package commands;

import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

/**
 * <p>Class collection of static method to resolve the
 * functionality for attacking a minion.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class AttackCommand {

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private AttackCommand() { }

    /**
     * <p>Main function to solve the usage of attacking a minion.</p>
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
        output.put("command", "cardUsesAttack");
        output.putObject("cardAttacker").put("x", action.getCardAttacker().getX()).put("y",
                action.getCardAttacker().getY());
        output.putObject("cardAttacked").put("x", attackedCardX).put("y", attackedCardY);

        final Minion attackerCard = GwentStone.getGame()
                .getBattleField()
                .getCard(action.getCardAttacker().getX(), action.getCardAttacker().getY());

        if (attackerCard == null) {
            return;
        }

        final int playerIndex = GwentStone.getGame().getActivePlayerIndex() - 1;
        if (!GwentStone.getGame().getBattleField().isEnemy(playerIndex,
                attackedCardX)) {
            output.put("error", "Attacked card does not belong to the enemy.");
            debugOutput.add(output);
        } else if (attackerCard.isFreezing()) {
            output.put("error", "Attacker card is frozen.");
            debugOutput.add(output);
        } else if (attackerCard.isSleeping()) {
            output.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(output);
        } else {
            final String errorMessage = attackerCard.attackNow(attackedCardX, attackedCardY);
            if (!errorMessage.equals("Ok")) {
                output.put("error", errorMessage);
                debugOutput.add(output);
            }
        }
    }
}
