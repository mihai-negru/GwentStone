package cards;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <p>Interface encapsulating the {@code Minion,
 * Environment, Hero} Objects. {@code Card}
 * interface has the basic functionalities
 * of a playing card and also a special function
 * {@code toJson} to print the card to a fancy
 * JSON format in a file.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public interface Card {
    /**
     * <p>Max row to take a card from table.</p>
     */
    int MAX_ROW = 4;

    /**
     * <p>Max column to take a card from table.</p>
     */
    int MAX_COLUMN = 5;

    /**
     * <p>Extract the name from the
     * current {@code Card}.</p>
     * @return the name of the Card.
     */
    String getName();

    /**
     * <p>Parse the {@code Card} data
     * to a fancy JSON format.</p>
     * @param output {@code ObjectNode} instance
     *               to insert all the card's data.
     */
    void toJson(ObjectNode output);

    /**
     * <p>Checks if the Card is frozen or
     * not.</p>
     * @return true if card is frozen, false
     * otherwise.
     */
    boolean isFreezing();

    /**
     * <p>Checks if the Card is a Minion card.
     * Functionality needed when inserting cards
     * on the playing table.</p>
     * @return true if card is {@code Minion} or
     * false otherwise.
     */
    boolean isMinion();

    /**
     * <p>Performs now an attack on another Card
     * and locks the card for current turn.</p>
     * @param cardX row index of the card to attack.
     * @param cardY column index of the card to attack.
     * @return "Ok" if the attacking process went
     * successfully, or an error {@code String} to
     * catch later.
     */
    String attackNow(int cardX, int cardY);
}
