package cards;

import java.util.List;

public final class Firestorm extends Environment {
    public Firestorm(final int iniMana, final String initDescription,
                     final List<String> initColors) {
        super(iniMana, initDescription, "Firestorm", initColors);
    }

    public Firestorm(final Firestorm anotherFirestorm) {
        super(anotherFirestorm);
    }
}
