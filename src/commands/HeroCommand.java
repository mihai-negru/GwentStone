package commands;

import cards.Hero;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

public final class HeroCommand {
    private HeroCommand() {
    }

    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "useHeroAbility");
        commandNode.put("affectedRow", action.getAffectedRow());

        final int activePlayerIndex = GwentStone.getGame().getPlayingPlayerIdx();
        final PlayingPlayer activePlayer = GwentStone.getGame().getPlayer(activePlayerIndex);
        final Hero attackingHero = activePlayer.getHero();

        if (attackingHero == null) {
            return;
        }

        if (attackingHero.getMana() > activePlayer.getMana()) {
            commandNode.put("error", "Not enough mana to use hero's ability.");
            debugOutput.add(commandNode);
        } else if (attackingHero.hasAttacked()) {
            commandNode.put("error", "Hero has already attacked this turn.");
            debugOutput.add(commandNode);
        } else {
            String errorMessage = attackingHero.useAbility(action.getAffectedRow(), -1);

            if (!errorMessage.equals("Ok")) {
                commandNode.put("error", errorMessage);
                debugOutput.add(commandNode);
            } else {
                activePlayer.loseMana(attackingHero.getMana());
            }
        }
    }
}
