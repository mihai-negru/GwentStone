package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>HeartHound placeholder for the Environment Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class HeartHound extends Environment {

    /**
     * <p>Build A HeartHound Card from Magic.</p>
     * @param iniMana cost to use the card.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public HeartHound(final int iniMana, final String initDescription,
                      final List<String> initColors) {
        super(iniMana, initDescription, "Heart Hound", initColors);
    }

    /**
     * <p>Build a HeartHound card from another Firestorm.</p>
     * @param anotherHound a valid HeartHound instance.
     */
    public HeartHound(final HeartHound anotherHound) {
        super(anotherHound);
    }

    /**
     * <p>Performs now an attack on another Card
     * and locks the card for current turn.</p>
     * @param cardX row index of the card to attack.
     * @param cardY column index of the card to attack.
     * @return "Ok" if the attacking process went
     * successfully, or an error {@code String} to
     * catch later.
     */
    @Override
    public String attackNow(final int cardX, final int cardY) {
        if ((cardX < 0) || (cardX >= MAX_ROW)) {
            return "Bad positions.";
        }

        int rowIndex;
        if (cardX == 0) {
            rowIndex = MAX_ROW - 1;
        } else if (cardX == 1) {
            rowIndex = 2;
        } else if (cardX == 2) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        final var selectedRow = GwentStone.getGame().getBattleField().getRow(rowIndex);

        if (selectedRow.size() >= MAX_ROW) {
            return "Bad positions.";
        }

        final var row = GwentStone.getGame().getBattleField().getRow(cardX);

        Minion maxHealthMinion = row.get(0);
        int maxHealthMinionIndex = -1;

        int iterIndex = 0;
        for (var minion : row) {
            if (minion.getHealth() > maxHealthMinion.getHealth()) {
                maxHealthMinion = minion;
                maxHealthMinionIndex = iterIndex;
            }

            ++iterIndex;
        }

        selectedRow.add(row.remove(maxHealthMinionIndex));

        return "Ok";
    }
}
