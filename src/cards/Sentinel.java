package cards;

import java.util.List;

/**
 * <p>Sentinel placeholder Minion Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Sentinel extends Minion {

    /**
     * <p>Construct a Sentinel Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Sentinel(final int initMana, final int initHealth, final int initAttackDamage,
                    final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Sentinel", initColors);
    }

    /**
     * <p>Build a Sentinel Card from a fallen Minion.</p>
     * @param anotherSentinel a valid instance of a Minion card.
     */
    public Sentinel(final Sentinel anotherSentinel) {
        super(anotherSentinel);
    }

    /**
     * <p>A Sentinel Minion should
     * never be placed in the first row.
     * It gets scared very fast.</p>
     * @return false every time.
     */
    @Override
    public boolean inFrontRow() {
        return false;
    }

    /**
     * <p>Sentinel is not a Tank.</p>
     * @return false every time.
     */
    @Override
    public boolean isTank() {
        return false;
    }
}
