package commands;

import cards.Hero;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;

/**
 * <p>Class collection of static method to resolve the
 * functionality for attacking a player hero.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class AttackHeroCommand {

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private AttackHeroCommand() { }

    /**
     * <p>Main function to solve the usage of attacking a hero.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param action command containing all the information
     *               about the solving problem.
     */
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        final int attackerCardX = action.getCardAttacker().getX();
        final int attackerCardY = action.getCardAttacker().getY();

        final ObjectNode output = debugOutput.objectNode();
        output.put("command", "useAttackHero");
        output.putObject("cardAttacker").put("x", attackerCardX).put("y", attackerCardY);

        final int passivePlayer = GwentStone.getGame().getActivePlayerIndex() % 2 + 1;

        final Minion attackerMinion = GwentStone.getGame()
                .getBattleField().getCard(attackerCardX, attackerCardY);

        if (attackerMinion == null) {
            return;
        }

        if (attackerMinion.isFreezing()) {
            output.put("error", "Attacker card is frozen.");
            debugOutput.add(output);
        } else if (attackerMinion.isSleeping()) {
            output.put("error", "Attacker card has already attacked this turn.");
            debugOutput.add(output);
        } else {
            final int activePlayer = passivePlayer % 2 + 1;

            if (GwentStone.getGame().getBattleField().rowHasTanks(activePlayer)) {
                output.put("error", "Attacked card is not of type 'Tank'.");
                debugOutput.add(output);
            } else {
                final Hero affectedHero = GwentStone.getGame().getPlayer(passivePlayer).getHero();

                affectedHero.underAttack(attackerMinion.getAttackDamage());
                attackerMinion.unleashTheBeast();

                if (affectedHero.getHealth() <= 0) {
                    GwentStone.getGame().stopGame();

                    if (activePlayer == 1) {
                        debugOutput.addObject().put("gameEnded",
                                "Player one killed the enemy hero.");
                    } else {
                        debugOutput.addObject().put("gameEnded",
                                "Player two killed the enemy hero.");
                    }

                    GwentStone.getGame().getPlayer(activePlayer).addWinGames();
                }
            }
        }
    }
}
