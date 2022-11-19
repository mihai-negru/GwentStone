package commands;

import cards.Card;
import cards.Environment;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

/**
 * <p>Class collection of static method to resolve the
 * functionality fot using environment abilities.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class EnvironmentCommand {

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private EnvironmentCommand() { }

    /**
     * <p>Main function to solve the usage of environment
     * abilities.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param action command containing all the information
     *               about the solving problem.
     */
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int activePlayerIndex = GwentStone.getGame().getActivePlayerIndex();
        final PlayingPlayer activePlayer = GwentStone.getGame().getPlayer(activePlayerIndex);

        final ObjectNode output = debugOutput.objectNode();
        output.put("command", "useEnvironmentCard");
        output.put("handIdx", action.getHandIdx());
        output.put("affectedRow", action.getAffectedRow());

        final Card actionCard = activePlayer.getHand().getCard(action.getHandIdx());

        if (actionCard == null) {
            return;
        }

        if (actionCard.isMinion()) {
            output.put("error", "Chosen card is not of type environment.");
            debugOutput.add(output);
        } else {
            final Environment envAction = (Environment) actionCard;

            if (envAction.getMana() > activePlayer.getMana()) {
                output.put("error", "Not enough mana to use environment card.");
                debugOutput.add(output);
            } else {
                boolean checkRow = true;

                if ((activePlayerIndex == 1) && (action.getAffectedRow() >= 2)) {
                    checkRow = false;
                } else if ((activePlayerIndex == 2) && (action.getAffectedRow() < 2)) {
                    checkRow = false;
                }

                if (checkRow) {
                    final String errorMessage = envAction.attackNow(action.getAffectedRow(), -1);
                    if (!errorMessage.equals("Ok")) {
                        output.put("error",
                                "Cannot steal enemy card since the player's row is full.");
                        debugOutput.add(output);
                    } else {
                        activePlayer.loseMana(envAction.getMana());
                        activePlayer.getHand().removeCard(action.getHandIdx());
                    }
                } else {
                    output.put("error", "Chosen row does not belong to the enemy.");
                    debugOutput.add(output);
                }
            }
        }
    }
}
