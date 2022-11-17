package cards;

import java.util.List;

public final class Warden extends Minion {
    public Warden(final int initMana, final int initHealth, final int initAttackDamage,
                  final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Warden", initColors);
    }

    public Warden(final Warden anotherWarden) {
        super(anotherWarden);
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
