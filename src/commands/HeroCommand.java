package commands;

import cards.Hero;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

/**
 * <p>Class collection of static method to resolve the
 * functionality for using here ability.</p>
 *
 * @author Mihai Negry
 * @since 1.0.0
 */
public final class HeroCommand {

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private HeroCommand() { }

    /**
     * <p>Main function to solve the use Hero Ability.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param action command containing all the information
     *               about the solving problem.
     */
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final ObjectNode output = debugOutput.objectNode();
        output.put("command", "useHeroAbility");
        output.put("affectedRow", action.getAffectedRow());

        final int activePlayerIndex = GwentStone.getGame().getActivePlayerIndex();
        final PlayingPlayer activePlayer = GwentStone.getGame().getPlayer(activePlayerIndex);
        final Hero attackingHero = activePlayer.getHero();

        if (attackingHero == null) {
            return;
        }

        if (attackingHero.getMana() > activePlayer.getMana()) {
            output.put("error", "Not enough mana to use hero's ability.");
            debugOutput.add(output);
        } else if (attackingHero.isSleeping()) {
            output.put("error", "Hero has already attacked this turn.");
            debugOutput.add(output);
        } else {
            final String errorMessage = attackingHero.unleashTheHell(action.getAffectedRow(), -1);

            if (!errorMessage.equals("Ok")) {
                output.put("error", errorMessage);
                debugOutput.add(output);
            } else {
                activePlayer.loseMana(attackingHero.getMana());
            }
        }
    }
}
