package commands;

import cards.Minion;
import cards.SpecialCard;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

/**
 * <p>Class collection of static method to resolve the
 * functionality for using special minion ability.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class AbilityCommand {

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private AbilityCommand() { }

    /**
     * <p>Main function to solve the usage of special
     * minion ability.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param action command containing all the information
     *               about the solving problem.
     */
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int attackedCardX = action.getCardAttacked().getX();
        final int attackedCardY = action.getCardAttacked().getY();

        final ObjectNode output = debugOutput.objectNode();
        output.put("command", "cardUsesAbility");
        output.putObject("cardAttacker").put("x", action.getCardAttacker().getX()).put("y",
                action.getCardAttacker().getY());
        output.putObject("cardAttacked").put("x", attackedCardX).put("y", attackedCardY);

       final Minion attackerCard = GwentStone.getGame()
               .getBattleField()
               .getCard(action.getCardAttacker().getX(), action.getCardAttacker().getY());

        if (attackerCard == null) {
            return;
        }

        if (attackerCard.isFreezing()) {
            output.put("error", "Attacker card is frozen.");
            debugOutput.add(output);
        } else if (attackerCard.isSleeping()) {
            output.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(output);
        } else {
            final String errorMessage = ((SpecialCard) attackerCard)
                    .unleashTheHell(attackedCardX, attackedCardY);

            if (!errorMessage.equals("Ok")) {
                output.put("error", errorMessage);
                debugOutput.add(output);
            }
        }
    }
}
