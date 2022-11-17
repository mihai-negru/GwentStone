package commands;

import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

public final class AttackCommand {
    private AttackCommand() { }
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int attackedCardX = action.getCardAttacked().getX();
        final int attackedCardY = action.getCardAttacked().getY();

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "cardUsesAttack");
        commandNode.putObject("cardAttacker").put("x", action.getCardAttacker().getX()).put("y",
                action.getCardAttacker().getY());
        commandNode.putObject("cardAttacked").put("x", attackedCardX).put("y", attackedCardY);

        Minion attackerCard = GwentStone.getGame().getPlayingTable().getCard(
                action.getCardAttacker().getX(), action.getCardAttacker().getY());

        if (attackerCard == null) {
            return;
        }

        int playerIndex = GwentStone.getGame().getPlayingPlayerIdx() - 1;
        if (!GwentStone.getGame().getPlayingTable().cardBelongsToEnemy(playerIndex,
                attackedCardX, attackedCardY)) {
            commandNode.put("error", "Attacked card does not belong to the enemy.");
            debugOutput.add(commandNode);
        } else if (attackerCard.isFreezing()) {
            commandNode.put("error", "Attacker card is frozen.");
            debugOutput.add(commandNode);
        } else if (attackerCard.isSleeping()) {
            commandNode.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(commandNode);
        } else {
            String errorMessage = attackerCard.attackNow(attackedCardX, attackedCardY);
            if (!errorMessage.equals("Ok")) {
                commandNode.put("error", errorMessage);
                debugOutput.add(commandNode);
            }
        }
    }
}
