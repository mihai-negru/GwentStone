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
    public String attackNow(int posX, int posY) {
        if ((posX < 0) || (posX >= 4)) {
            return "Bad positions";
        }

        GwentStone.getGame()
                .getPlayingTable()
                .getCardsRow(posX)
                .forEach(Minion::freeze);

        return "Ok";
    }
}
