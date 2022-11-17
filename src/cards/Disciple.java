package cards;

import java.util.List;

public final class Disciple extends Minion implements SpecialCard {
    public Disciple(final int initMana, final int initHealth, final int initAttackDamage,
                      final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Disciple", initColors);
    }

    public Disciple(final Disciple anotherDisciple) {
        super(anotherDisciple);
    }

    @Override
    public boolean inFrontRow() {
        return false;
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
