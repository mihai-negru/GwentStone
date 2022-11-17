package cards;

import java.util.List;

public final class TheRipper extends Minion implements SpecialCard {
    public TheRipper(final int initMana, final int initHealth, final int initAttackDamage,
                     final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "The Ripper", initColors);
    }

    public TheRipper(final TheRipper anotherRipper) {
        super(anotherRipper);
    }

    @Override
    public boolean inFrontRow() {
        return true;
    }

    @Override
    public boolean isTank() {
        return false;
    }

    @Override
    public String useAbility(int posX, int posY) {
        return "Ok";
    }
}
