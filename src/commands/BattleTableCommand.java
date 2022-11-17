package commands;

import cards.Card;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

public final class BattleTableCommand {
    private BattleTableCommand() {
    }

    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        int playerIndex = GwentStone.getGame().getPlayingPlayerIdx();
        PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "placeCard");
        commandNode.put("handIdx", action.getHandIdx());

        Card cardToPlace = player.getHand().getCard(action.getHandIdx());

        if (cardToPlace == null) {
            return;
        }

        if (!cardToPlace.isMinion()) {
            commandNode.put("error", "Cannot place environment card on table.");
            debugOutput.add(commandNode);
        } else {
            Minion minionToPlace = (Minion) cardToPlace;
            if (minionToPlace.getMana() > player.getMana()) {
                commandNode.put("error", "Not enough mana to place card on table.");
                debugOutput.add(commandNode);
            } else {
                boolean cardInFrontRow = minionToPlace.inFrontRow();

                int calculateRowToPlaceCard;
                if ((playerIndex == 1) && cardInFrontRow) {
                    calculateRowToPlaceCard = 2;
                } else if (playerIndex == 1) {
                    calculateRowToPlaceCard = 3;
                } else if ((playerIndex == 2) && cardInFrontRow) {
                    calculateRowToPlaceCard = 1;
                } else {
                    calculateRowToPlaceCard = 0;
                }


                if (!GwentStone.getGame().getPlayingTable().addCardToTable(minionToPlace,
                        calculateRowToPlaceCard)) {
                    commandNode.put("error", "Cannot place card on table since row is full.");
                    debugOutput.add(commandNode);
                } else {
                    player.getHand().removeCard(action.getHandIdx());
                    player.loseMana(minionToPlace.getMana());
                }
            }
        }
    }
}
