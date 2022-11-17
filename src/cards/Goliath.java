package cards;

import java.util.List;


/**
 * <p>Goliath placeholder Minion Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Goliath extends Minion {

    /**
     * <p>Construct a Goliath Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Goliath(final int initMana, final int initHealth, final int initAttackDamage,
                   final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Goliath", initColors);
    }

    /**
     * <p>Build a Goliath Card from a fallen Minion.</p>
     * @param anotherGoliath a valid instance of a Minion card.
     */
    public Goliath(final Goliath anotherGoliath) {
        super(anotherGoliath);
    }

    /**
     * <p>Goliath is a very powerful card,
     * so it deserves to stay in the first row
     * to protect another Cards.</p>
     * @return true every time.
     */
    @Override
    public boolean inFrontRow() {
        return true;
    }

    /**
     * <p>As the name suggests the Goliath,
     * is one of the most rigid Tanks.</p>
     * @return always true
     */
    @Override
    public boolean isTank() {
        return true;
    }
}
