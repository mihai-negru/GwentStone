package cards;

import java.util.List;

public final class KingMudface extends Hero {
    public KingMudface(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "King Mudface", initColors);
    }

    public KingMudface(final KingMudface anotherKing) {
        super(anotherKing);
    }
}
