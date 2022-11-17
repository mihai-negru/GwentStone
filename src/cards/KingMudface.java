package cards;

import game.GwentStone;

import java.util.List;

public final class KingMudface extends Hero {
    public KingMudface(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "King Mudface", initColors);
    }

    public KingMudface(final KingMudface anotherKing) {
        super(anotherKing);
    }

    @Override
    public String unleashTheHell(int posX, int posY) {
        if ((posX < 0) || (posX > 4)) {
            return "Bad positions";
        }

        final int activePlayer = GwentStone.getGame().getPlayingPlayerIdx();

        if (((activePlayer == 1) && (posX == 0 || posX == 1)) ||
                ((activePlayer == 2) && (posX == 2 || posX == 3))) {
            return "Selected row does not belong to the current player.";
        }

        GwentStone.getGame()
                .getPlayingTable()
                .getCardsRow(posX)
                .forEach(minion -> minion.makeHarder(1));

        unleashTheBeast();

        return "Ok";
    }
}
