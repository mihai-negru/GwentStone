package cards;

import java.util.List;

public final class Sentinel extends Minion {
    public Sentinel(final int initMana, final int initHealth, final int initAttackDamage,
                    final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Sentinel", initColors);
    }

    public Sentinel(final Sentinel anotherSentinel) {
        super(anotherSentinel);
    }

    @Override
    public boolean inFrontRow() {
        return false;
    }

    @Override
    public boolean isTank() {
        return false;
    }
}
