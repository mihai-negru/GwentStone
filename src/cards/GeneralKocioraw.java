package cards;

import java.util.List;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "General Kocioraw", initColors);
    }

    public GeneralKocioraw(final GeneralKocioraw anotherGeneral) {
        super(anotherGeneral);
    }
}
