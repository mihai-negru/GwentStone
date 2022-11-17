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
    public boolean attack(int posX, int posY) {
        if ((posX < 0) || (posX >= 4)) {
            return false;
        }

        GwentStone.getGame()
                .getPlayingTable()
                .getCardsRow(posX)
                .forEach(Minion::gotFrozen);

        return true;
    }
}
