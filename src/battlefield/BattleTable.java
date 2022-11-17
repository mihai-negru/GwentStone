package battlefield;

import cards.Minion;
import game.GwentStone;

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

    public Minion getCard(final int posX, final int posY) {
        if ((posX >= 0) && (posX <= 4)) {
            if (table.get(posX).size() > posY) {
                return table.get(posX).get(posY);
            }
        }

        return null;
    }

    public boolean removeCard(final int posX, final int posY) {
        if ((posX >= 0) && (posX <= 4)) {
            if (table.get(posX).size() > posY) {
                table.get(posX).remove(posY);

                return true;
            }
        }

        return false;
    }

    public boolean addCardToTable(final Minion cardToPlace, final int rowIndex) {
        var rowList = table.get(rowIndex);

        if (rowList.size() >= 5) {
            return false;
        }

        return rowList.add(cardToPlace);
    }

    public void checkTable() {
        table.forEach(row -> row.removeIf(card -> card.getHealth() <= 0));
    }

    public void resetPlayerCards(final int playerIndex) {
        if (playerIndex == 0) {
            table.get(2).forEach(Minion::unFroze);
            table.get(3).forEach(Minion::unFroze);
            table.get(2).forEach(Minion::resetCard);
            table.get(3).forEach(Minion::resetCard);
        } else {
            table.get(0).forEach(Minion::unFroze);
            table.get(1).forEach(Minion::unFroze);
            table.get(0).forEach(Minion::resetCard);
            table.get(1).forEach(Minion::resetCard);
        }
    }

    public void clearTable() {
        table.forEach(List::clear);
    }

    public boolean cardBelongsToEnemy(final int playerIndex, final int posX, final int posY) {
        return ((playerIndex == 0) && ((posX == 0) || (posX == 1))) ||
                ((playerIndex == 1) && ((posX == 2) || (posX == 3)));
    }

    public boolean rowHasTanks(final int rowIndex) {
        if ((rowIndex < 0) || (rowIndex > 4)) {
            return false;
        }

        return table.get(rowIndex).stream()
                .anyMatch(Minion::isTank);
    }

    public boolean notAttackedATank(final Minion minion , final int posX) {
        if (minion == null) {
            return false;
        }

        return !minion.isTank()
                && ((((posX == 0) || (posX == 1)) && GwentStone.getGame().getPlayingTable().rowHasTanks(1))
                || (((posX == 2) || (posX == 3)) && GwentStone.getGame().getPlayingTable().rowHasTanks(2)));
    }
}
