package cards;

import game.GwentStone;

import java.util.List;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "General Kocioraw", initColors);
    }

    public GeneralKocioraw(final GeneralKocioraw anotherGeneral) {
        super(anotherGeneral);
    }

    @Override
    public String useAbility(int posX, int posY) {
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
                .forEach(minion -> minion.makeStronger(1));

        performedAnAction();

        return "Ok";
    }
}
