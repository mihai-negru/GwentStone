package cards;

import java.util.List;

public final class Miraj extends Minion implements SpecialCard {
    public Miraj(final int initMana, final int initHealth, final int initAttackDamage,
                 final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Miraj", initColors);
    }

    public Miraj(final Miraj anotherMiraj) {
        super(anotherMiraj);
    }

    @Override
    public boolean inFrontRow() {
        return true;
    }

    @Override
    public boolean isTank() {
        return false;
    }
}
