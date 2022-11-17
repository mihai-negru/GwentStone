package cards;

import java.util.List;

/**
 * <p>Berserker placeholder Minion Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Berserker extends Minion {

    /**
     * <p>Construct a Berserker Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Berserker(final int initMana, final int initHealth, final int initAttackDamage,
                     final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Berserker", initColors);
    }

    /**
     * <p>Build a Berserker Card from a fallen Minion.</p>
     * @param anotherBerserker a valid instance of a Minion card.
     */
    public Berserker(final Berserker anotherBerserker) {
        super(anotherBerserker);
    }

    /**
     * <p>A Berserker Minion should
     * never be placed in the first row.
     * It gets scared very fast.</p>
     * @return false every time.
     */
    @Override
    public boolean inFrontRow() {
        return false;
    }

    /**
     * <p>Berserker is not a Tank.</p>
     * @return false every time.
     */
    @Override
    public boolean isTank() {
        return false;
    }
}
