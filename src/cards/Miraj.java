package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>Miraj placeholder Minion Card with special abilities.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Miraj extends Minion implements SpecialCard {

    /**
     * <p>Construct a Miraj Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Miraj(final int initMana, final int initHealth, final int initAttackDamage,
                 final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Miraj", initColors);
    }

    /**
     * <p>Build a Miraj Card from a fallen Minion.</p>
     * @param anotherMiraj a valid instance of a Minion card.
     */
    public Miraj(final Miraj anotherMiraj) {
        super(anotherMiraj);
    }

    /**
     * <p>A Miraj Minion should
     * be placed in the first row.
     * Its anger scary anyone.</p>
     * @return always true.
     */
    @Override
    public boolean inFrontRow() {
        return true;
    }

    /**
     * <p>Miraj is not a Tank.</p>
     * @return false every time.
     */
    @Override
    public boolean isTank() {
        return false;
    }

    /**
     * <p>Special ability that just
     * some card can posses. When the
     * card is using the ability the
     * hell comes to the surface.</p>
     *
     * <p>For Miraj, it uses the Skyjack
     * ability.</p>
     *
     * @param cardX row index to attack the card.
     * @param cardY column index to attack the card.
     * @return "Ok" string if everything when successfully
     * or an error message to catch later and process it.
     */
    @Override
    public String unleashTheHell(final int cardX, final int cardY) {
        if ((cardX < 0) || (cardX > MAX_ROW) || (cardY < 0) || (cardY > MAX_COLUMN)) {
            return "Bad positions.";
        }

        final int activePlayer = GwentStone.getGame().getActivePlayerIndex() - 1;

        if (!GwentStone.getGame().getBattleField()
                .isEnemy(activePlayer, cardX)) {
            return "Attacked card does not belong to the enemy.";
        }

        final Minion minion = GwentStone.getGame().getBattleField().getCard(cardX, cardY);

        if (minion == null) {
            return "Null card.";
        }

        if (GwentStone.getGame().getBattleField().notAttackedATank(minion, cardX)) {
            return "Attacked card is not of type 'Tank'.";
        }

        unleashTheBeast();
        final int minionHealth = minion.getHealth();
        minion.setHealth(getHealth());
        setHealth(minionHealth);

        return "Ok";
    }
}
