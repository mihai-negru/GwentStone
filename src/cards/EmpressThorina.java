package cards;

import game.GwentStone;

import java.util.List;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "Empress Thorina", initColors);
    }

    public EmpressThorina(final EmpressThorina anotherEmpress) {
        super(anotherEmpress);
    }

    @Override
    public String unleashTheHell(int posX, int posY) {
        if ((posX < 0) || (posX > 4)) {
            return "Bad positions";
        }

        final int activePlayer = GwentStone.getGame().getPlayingPlayerIdx();

        if (((activePlayer == 1) && (posX == 2 || posX == 3)) ||
                ((activePlayer == 2) && (posX == 0 || posX == 1))) {
            return "Selected row does not belong to the enemy.";
        }

        var affectedRow = GwentStone.getGame().getPlayingTable().getCardsRow(posX);

        int maxHealth = -1;
        int minionIndex = -1;
        int iterIndex = 0;

        for (var minion : affectedRow) {
            if (minion.getHealth() > maxHealth) {
                maxHealth = minion.getHealth();
                minionIndex = iterIndex;
            }

            ++iterIndex;
        }

        if (minionIndex < 0) {
            return "Empty row";
        }

        GwentStone.getGame().getPlayingTable().removeCard(posX, minionIndex);
        unleashTheBeast();

        return "Ok";
    }
}
