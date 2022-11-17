package commands;

import cards.Hero;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

public final class AttackHeroCommand {
    private AttackHeroCommand() {

    }

    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int attackerCardX = action.getCardAttacker().getX();
        final int attackerCardY = action.getCardAttacker().getY();

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", "useAttackHero");
        commandNode.putObject("cardAttacker").put("x", attackerCardX).put("y", attackerCardY);

        final int passivePlayer = GwentStone.getGame().getPlayingPlayerIdx() % 2 + 1;

        Minion attackerCard = GwentStone.getGame().getPlayingTable().getCard(attackerCardX, attackerCardY);

        if (attackerCard == null) {
            return;
        }

        if (attackerCard.isFreezing()) {
            commandNode.put("error", "Attacker card is frozen.");
            debugOutput.add(commandNode);
        } else if (attackerCard.isSleeping()) {
            commandNode.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(commandNode);
        } else {
            final int activePlayer = passivePlayer % 2 + 1;

            if (GwentStone.getGame().getPlayingTable().rowHasTanks(activePlayer)) {
                commandNode.put("error", "Attacked card is not of type 'Tank'.");
                debugOutput.add(commandNode);
            } else {
                Hero affectedHero = GwentStone.getGame().getPlayer(passivePlayer).getHero();
                affectedHero.underAttack(attackerCard.getAttackDamage());
                attackerCard.unleashTheBeast();

                if (affectedHero.getHealth() <= 0) {
                    GwentStone.getGame().stopGame();

                    if (activePlayer == 1) {
                        debugOutput.addObject().put("gameEnded", "Player one killed the enemy hero.");
                    } else {
                        debugOutput.addObject().put("gameEnded", "Player two killed the enemy hero.");
                    }

                    GwentStone.getGame().getPlayer(activePlayer).addWinGames();
                }
            }
        }
    }
}
