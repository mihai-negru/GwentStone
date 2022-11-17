package cards;

import java.util.List;

public final class Goliath extends Minion {
    public Goliath(final int initMana, final int initHealth, final int initAttackDamage,
                   final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Goliath", initColors);
    }

    public Goliath(final Goliath anotherGoliath) {
        super(anotherGoliath);
    }

    @Override
    public boolean inFrontRow() {
        return true;
    }

    @Override
    public boolean isTank() {
        return true;
    }
}
