package battlefield;

import cards.Minion;

import java.util.ArrayList;
import java.util.List;

public final class BattleTable {
    private final List<List<Minion>> table;

    public BattleTable() {
        table = new ArrayList<>(4);

        for (int i = 0; i < 4; ++i) {
            table.add(new ArrayList<>(5));
        }
    }

    public List<List<Minion>> getCards() {
        return table;
    }

    public List<Minion> getCardsRow(final int rowIndex) {
        return table.get(rowIndex);
    }

    public boolean addCardToTable(final Minion cardToPlace, final int rowIndex) {
        var rowList = table.get(rowIndex);

        if (rowList.size() >= 5) {
            return false;
        }

        return rowList.add(cardToPlace);
    }

    public void checkTable() {
        for (var row : table) {
            row.removeIf(card -> card.getHealth() <= 0);
        }
    }

    public void clearTable() {
        for (var row : table) {
            row.clear();
        }
    }
}
