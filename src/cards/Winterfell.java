package cards;

import game.GwentStone;

import java.util.List;

public final class Winterfell extends Environment {
    public Winterfell(final int iniMana, final String initDescription,
                      final List<String> initColors) {
        super(iniMana, initDescription, "Winterfell", initColors);
    }

    public Winterfell(final Winterfell anotherWinterfell) {
        super(anotherWinterfell);
    }

    @Override
    public boolean useAbility(int posX, int posY) {
        if ((posX < 0) || (posX >= 4)) {
            return false;
        }

        var rowTable = GwentStone.getGame().getPlayingTable().getCardsRow(posX);

        for (var minion : rowTable) {
            minion.gotFrozen();
        }

        return true;
    }
}
