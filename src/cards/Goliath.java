package cards;

import java.util.List;

public final class Goliath extends Minion implements TankCard {
    public Goliath(final int initMana, final int initHealth, final int initAttackDamage,
                   final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Goliath", initColors);
    }
}
