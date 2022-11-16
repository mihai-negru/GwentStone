package cards;

import java.util.List;

public final class TheCursedOne extends Minion implements SpecialCard {
    public TheCursedOne(final int initMana, final int initHealth, final int initAttackDamage,
                        final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage,
                initDescription, "The Cursed One", initColors);
    }

    public TheCursedOne(final TheCursedOne anotherCursedOne) {
        super(anotherCursedOne);
    }

    @Override
    public boolean inFrontRow() {
        return false;
    }
}
