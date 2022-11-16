package cards;

import java.util.List;

public class LordRoyce extends Hero {
    public LordRoyce(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "Lord Royce", initColors);
    }

    public LordRoyce(final LordRoyce anotherLord) {
        super(anotherLord);    }
}
