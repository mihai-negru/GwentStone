package commands;

import cards.Card;
import cards.Environment;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

public final class EnvironmentCommand {

    private EnvironmentCommand() { }
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        int playerIndex = GwentStone.getGame().getActivePlayerIndex();
        PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "useEnvironmentCard");
        commandNode.put("handIdx", action.getHandIdx());
        commandNode.put("affectedRow", action.getAffectedRow());

        Card actionCard = player.getHand().getCard(action.getHandIdx());

        if (actionCard == null) {
            return;
        }

        if (actionCard.isMinion()) {
            commandNode.put("error", "Chosen card is not of type environment.");
            debugOutput.add(commandNode);
        } else {
            Environment envAction = (Environment) actionCard;

            if (envAction.getMana() > player.getMana()) {
                commandNode.put("error", "Not enough mana to use environment card.");
                debugOutput.add(commandNode);
            } else {
                boolean isRowValid = true;

                if ((playerIndex == 1) && (action.getAffectedRow() >= 2)) {
                    isRowValid = false;
                } else if ((playerIndex == 2) && (action.getAffectedRow() < 2)) {
                    isRowValid = false;
                }

                if (isRowValid) {
                    String errorMessage = envAction.attackNow(action.getAffectedRow(), -1);
                    if (!errorMessage.equals("Ok")) {
                        commandNode.put("error", "Cannot steal enemy card since the player's row is full.");
                        debugOutput.add(commandNode);
                    } else {
                        player.loseMana(envAction.getMana());
                        player.getHand().removeCard(action.getHandIdx());
                    }
                } else {
                    commandNode.put("error", "Chosen row does not belong to the enemy.");
                    debugOutput.add(commandNode);
                }
            }
        }
    }
}
