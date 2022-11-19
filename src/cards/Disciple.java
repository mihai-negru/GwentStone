package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>Disciple placeholder Minion Card with special abilities.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Disciple extends Minion implements SpecialCard {

    /**
     * <p>Construct a Disciple Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Disciple(final int initMana, final int initHealth, final int initAttackDamage,
                      final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Disciple", initColors);
    }

    /**
     * <p>Build a Disciple Card from a fallen Minion.</p>
     * @param anotherDisciple a valid instance of a Minion card.
     */
    public Disciple(final Disciple anotherDisciple) {
        super(anotherDisciple);
    }

    /**
     * <p>A Disciple Minion should
     * never be placed in the first row.
     * It gets scared very fast.</p>
     * @return always false.
     */
    @Override
    public boolean inFrontRow() {
        return false;
    }

    /**
     * <p>Disciple is not a Tank.</p>
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
     * <p>For Disciple, it makes your Minion
     * Card Harder by 2 health points</p>
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

        if (GwentStone.getGame().getBattleField().isEnemy(activePlayer, cardX)) {
            return "Attacked card does not belong to the current player.";
        }

        final Minion minion = GwentStone.getGame().getBattleField().getCard(cardX, cardY);

        if (minion == null) {
            return "Null card.";
        }

        unleashTheBeast();
        minion.makeHarder(2);

        return "Ok";
    }
}
