package cards;

import game.GwentStone;

import java.util.List;

public final class Firestorm extends Environment {
    public Firestorm(final int iniMana, final String initDescription,
                     final List<String> initColors) {
        super(iniMana, initDescription, "Firestorm", initColors);
    }

    public Firestorm(final Firestorm anotherFirestorm) {
        super(anotherFirestorm);
    }

    @Override
    public boolean attack(int posX, int posY) {
        if ((posX < 0) || (posX >= 4)) {
            return false;
        }

        GwentStone.getGame()
                .getPlayingTable()
                .getCardsRow(posX)
                .forEach(minion -> minion.gotAttacked(1));

        GwentStone.getGame().getPlayingTable().checkTable();

        return true;
    }
}
