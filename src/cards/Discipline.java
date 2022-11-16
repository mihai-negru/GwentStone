package cards;

import java.util.List;

public final class Discipline extends Minion implements SpecialCard {
    public Discipline(final int initMana, final int initHealth, final int initAttackDamage,
                      final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Discipline", initColors);
    }

    public Discipline(final Discipline anotherDiscipline) {
        super(anotherDiscipline);
    }
}
