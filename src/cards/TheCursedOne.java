package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>The Cursed One placeholder Minion Card with special abilities.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class TheCursedOne extends Minion implements SpecialCard {

    /**
     * <p>Construct a Cursed Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public TheCursedOne(final int initMana, final int initHealth, final int initAttackDamage,
                        final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage,
                initDescription, "The Cursed One", initColors);
    }

    /**
     * <p>Build a Cursed Card from a fallen Minion.</p>
     * @param anotherCursed a valid instance of a Minion card.
     */
    public TheCursedOne(final TheCursedOne anotherCursed) {
        super(anotherCursed);
    }

    /**
     * <p>A CursedOne Minion should
     * never be placed in the first row.
     * Its anger scary anyone.</p>
     * @return always false.
     */
    @Override
    public boolean inFrontRow() {
        return false;
    }

    /**
     * <p>CursedOne is not a Tank.</p>
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
     * <p>For The Cursed One, it uses the Shapeshift
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

        final int minionAttackDamage = minion.getAttackDamage();

        if (minionAttackDamage <= 0) {
            if (GwentStone.getGame().getBattleField().removeCard(cardX, cardY)) {
                return "Ok";
            }

            return "Could not remove card.";
        }

        unleashTheBeast();
        minion.setAttackDamage(minion.getHealth());
        minion.setHealth(minionAttackDamage);

        return "Ok";
    }
}
