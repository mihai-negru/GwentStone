package cards;

import java.util.List;

public final class Berserker extends Minion {
    public Berserker(final int initMana, final int initHealth, final int initAttackDamage,
                     final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Berserker", initColors);
    }

    public Berserker(final Berserker anotherBerserker) {
        super(anotherBerserker);
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
