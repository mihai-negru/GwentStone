package commands;

import cards.Card;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

/**
 * <p>Class collection of static method to resolve the
 * functionality for using the battle table.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class BattleTableCommand {

    /**
     * <p>Max row allowed in the battle table.</p>
     */
    private static final int MAX_ROW = 4;

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private BattleTableCommand() { }

    /**
     * <p>Main function to solve the usage of battlefield.</p>
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
        output.put("command", "placeCard");
        output.put("handIdx", action.getHandIdx());

        final Card cardToPlace = activePlayer.getHand().getCard(action.getHandIdx());

        if (cardToPlace == null) {
            return;
        }

        if (!cardToPlace.isMinion()) {
            output.put("error", "Cannot place environment card on table.");
            debugOutput.add(output);
        } else {
            final Minion minionToPlace = (Minion) cardToPlace;
            if (minionToPlace.getMana() > activePlayer.getMana()) {
                output.put("error", "Not enough mana to place card on table.");
                debugOutput.add(output);
            } else {
                boolean minionIsTough = minionToPlace.inFrontRow();

                int cardRow;
                if ((activePlayerIndex == 1) && minionIsTough) {
                    cardRow = 2;
                } else if (activePlayerIndex == 1) {
                    cardRow = MAX_ROW - 1;
                } else if ((activePlayerIndex == 2) && minionIsTough) {
                    cardRow = 1;
                } else {
                    cardRow = 0;
                }

                if (!GwentStone.getGame().getBattleField().addCard(minionToPlace, cardRow)) {
                    output.put("error", "Cannot place card on table since row is full.");
                    debugOutput.add(output);
                } else {
                    activePlayer.getHand().removeCard(action.getHandIdx());
                    activePlayer.loseMana(minionToPlace.getMana());
                }
            }
        }
    }
}
