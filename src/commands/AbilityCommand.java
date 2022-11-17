package commands;

import cards.Minion;
import cards.SpecialCard;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

public final class AbilityCommand {
    private AbilityCommand() {
    }

    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int attackedCardX = action.getCardAttacked().getX();
        final int attackedCardY = action.getCardAttacked().getY();

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "cardUsesAbility");
        commandNode.putObject("cardAttacker").put("x", action.getCardAttacker().getX()).put("y",
                action.getCardAttacker().getY());
        commandNode.putObject("cardAttacked").put("x", attackedCardX).put("y", attackedCardY);

        Minion attackerCard = GwentStone.getGame().getPlayingTable().getCard(
                action.getCardAttacker().getX(), action.getCardAttacker().getY());

        if (attackerCard == null) {
            return;
        }

        if (attackerCard.isFrozen()) {
            commandNode.put("error", "Attacker card is frozen.");
            debugOutput.add(commandNode);
        } else if (attackerCard.hasAttacked()) {
            commandNode.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(commandNode);
        } else {
            String errorMessage = ((SpecialCard) attackerCard).useAbility(attackedCardX, attackedCardY);
            if (!errorMessage.equals("Ok")) {
                commandNode.put("error", errorMessage);
                debugOutput.add(commandNode);
            }
        }
    }
}
