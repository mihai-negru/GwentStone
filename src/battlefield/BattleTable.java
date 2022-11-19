package battlefield;

import cards.Minion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class maintaining the battle table.</p>
 *
 * <p>The main functions of the game are found here
 * and every card is processed in this class.</p>
 *
 * @see List
 * @see Minion
 * @see ArrayList
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class BattleTable {

    /**
     * <p>Maximum row allowed in the table.</p>
     */
    private static final int MAX_ROW = 4;

    /**
     * <p>Maximum column allowed in the table.</p>
     */
    private static final int MAX_COL = 5;

    /**
     * <p>Matrix composed from {@code Lists}.
     * Four rows and five columns.</p>
     */
    @Getter
    private final List<List<Minion>> table;

    /**
     * <p>Construct a basic table and initialize
     * the table matrix.</p>
     */
    public BattleTable() {
        table = new ArrayList<>(MAX_ROW);

        for (int i = 0; i < MAX_ROW; ++i) {
            table.add(new ArrayList<>(MAX_COL));
        }
    }

    /**
     * <p>Gets a row from the table.</p>
     * @param rowIndex index between 0 and 3.
     * @return {@code List} containing all the
     * cards from a specified row.
     */
    public List<Minion> getRow(final int rowIndex) {
        return new ArrayList<>(table.get(rowIndex));
    }

    /**
     * <p>Gets a card from the table, the same instance
     * will be returned and no copy will be made
     * over the {@code Minion} Object.</p>
     * @param cardX row index of the card to extract.
     * @param cardY column index of the card to extract.
     * @return an instance of the {@code Minion} Class.
     */
    public Minion getCard(final int cardX, final int cardY) {
        if ((cardX >= 0) && (cardX <= MAX_ROW)) {
            if (table.get(cardX).size() > cardY) {
                return table.get(cardX).get(cardY);
            }
        }

        return null;
    }

    /**
     * <p>Removes one card from the table.</p>
     * @param cardX row index to remove the card.
     * @param cardY column index to remove the card.
     * @return true if card was removed successfully
     * or false otherwise.
     */
    public boolean removeCard(final int cardX, final int cardY) {
        if ((cardX >= 0) && (cardX <= MAX_ROW)) {
            if (table.get(cardX).size() > cardY) {
                table.get(cardX).remove(cardY);

                return true;
            }
        }

        return false;
    }

    /**
     * <p>Adds one card to the battle fields table.</p>
     * @param minion instance of a {@code Minion} class
     *               to add in the table.
     * @param rowIndex row index to add the minion to the table.
     * @return true if the card was added successfully or false
     * otherwise.
     */
    public boolean addCard(final Minion minion, final int rowIndex) {
        final var rowList = table.get(rowIndex);

        if (rowList.size() >= MAX_COL) {
            return false;
        }

        return rowList.add(minion);
    }

    /**
     * <p>Checks if there exists minions that
     * are dead on the table and removes them
     * if any is found.</p>
     */
    public void checkTable() {
        table.forEach(row -> row.removeIf(card -> card.getHealth() <= 0));
    }

    /**
     * <p>Prepare the player for another round
     * by heating his minions up and waking
     * them up.</p>
     * @param playerIndex player index to prepare
     *                    for another round.
     */
    public void anotherRound(final int playerIndex) {
        if (playerIndex == 0) {
            table.get(MAX_ROW - 2).forEach(Minion::heatUp);
            table.get(MAX_ROW - 2).forEach(Minion::wakeUp);
            table.get(MAX_ROW - 1).forEach(Minion::heatUp);
            table.get(MAX_ROW - 1).forEach(Minion::wakeUp);
        } else {
            table.get(0).forEach(Minion::heatUp);
            table.get(0).forEach(Minion::wakeUp);
            table.get(1).forEach(Minion::heatUp);
            table.get(1).forEach(Minion::wakeUp);
        }
    }

    /**
     * <p>Deletes all the minions from
     * the table, and prepare the battlefield
     * for another game session.</p>
     */
    public void clear() {
        table.forEach(List::clear);
    }

    /**
     * <p>Checks if card belong to the enemy.</p>
     * @param playerIndex current active player that calls
     *                    the method.
     * @param cardX row index of the card to check.
     * @return true if card belong to the enemy or
     * false otherwise.
     */
    public boolean isEnemy(final int playerIndex, final int cardX) {
        return ((playerIndex == 0) && ((cardX == 0) || (cardX == 1)))
                || ((playerIndex == 1) && ((cardX == 2) || (cardX == MAX_ROW - 1)));
    }

    /**
     * <p>Check if the row from the table contains any
     * tank cards.</p>
     * @param rowIndex row index to check for tanks.
     * @return true if row has tanks or false otherwise.
     */
    public boolean rowHasTanks(final int rowIndex) {
        if ((rowIndex < 0) || (rowIndex > MAX_ROW)) {
            return false;
        }

        return table.get(rowIndex).stream()
                .anyMatch(Minion::isTank);
    }

    /**
     * <p>Checks if the attacked card is a
     * tank or not and gives permissions
     * to attack the desired card if it is
     * not a tank type.</p>
     * @param minion card to attack.
     * @param cardX row index of the minion to attack.
     * @return true if the minion has permissions to
     * attack the desired minion or false otherwise.
     */
    public boolean notAttackedATank(final Minion minion, final int cardX) {
        if (minion == null) {
            return false;
        }

        return !minion.isTank()
                && ((((cardX == 0) || (cardX == 1)) && rowHasTanks(1))
                || (((cardX == 2) || (cardX == MAX_ROW - 1)) && rowHasTanks(2)));
    }
}
