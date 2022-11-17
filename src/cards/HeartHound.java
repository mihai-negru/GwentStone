package cards;

import game.GwentStone;

import java.util.List;

public final class HeartHound extends Environment {
    public HeartHound(final int iniMana, final String initDescription,
                      final List<String> initColors) {
        super(iniMana, initDescription, "Heart Hound", initColors);
    }

    public HeartHound(final HeartHound anotherHound) {
        super(anotherHound);
    }

    @Override
    public boolean useAbility(int posX, int posY) {
        if ((posX < 0) || (posX >= 4)) {
            return false;
        }

        int newRow = -1;
        if (posX == 0) {
            newRow = 3;
        } else if (posX == 1) {
            newRow = 2;
        } else if (posX == 2) {
            newRow = 1;
        } else {
            newRow = 0;
        }

        var insertRowTable = GwentStone.getGame().getPlayingTable().getCardsRow(newRow);

        if (insertRowTable.size() >= 5) {
            return false;
        }

        var rowTable = GwentStone.getGame().getPlayingTable().getCardsRow(posX);

        Minion maxHealthMinion = rowTable.get(0);
        int maxHealthMinionIndex = -1;

        int iterIndex = 0;
        for (var minion : rowTable) {
            if (minion.getHealth() > maxHealthMinion.getHealth()) {
                maxHealthMinion = minion;
                maxHealthMinionIndex = iterIndex;
            }

            ++iterIndex;
        }

        return insertRowTable.add(rowTable.remove(maxHealthMinionIndex));
    }
}
