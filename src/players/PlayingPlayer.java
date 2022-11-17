package players;

import cards.Card;
import cards.Hero;
import fileio.DecksInput;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Class to store the information of a player
 * playing a game or a session of games.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class PlayingPlayer extends Player {

    /**
     * <p>Instance of the {@code Hero} Object
     * that the system has chosen for the
     * player to play in the current game
     * session.</p>
     */
    @Getter
    @Setter
    private Hero hero;

    /**
     * <p>Chosen deck by the player
     * to play the game session.</p>
     */
    @Getter
    private Deck deck;

    /**
     * <p>{@code Deck} Object which maintains
     * the hand of the player.</p>
     */
    @Getter
    private Deck hand;

    /**
     * <p>Boolean value to show if
     * the player is active or not. If
     * player is active that all the actions
     * are made by it, until the turn is changed.</p>
     */
    private boolean isActive;

    /**
     * <p>Current player mana, needed to place
     * cards on the game table or to use
     * special abilities.</p>
     */
    @Getter
    private int mana;

    /**
     * <p>Player constructor to initialize a playing
     * player for the game session, from a JSON file.</p>
     * @param playerInfo {@code DecksInput} instance containing
     *                                     all the information.
     */
    public PlayingPlayer(final DecksInput playerInfo) {
        super(playerInfo);
        isActive = false;
        mana = 0;
    }

    /**
     * <p>Set a new {@code Deck} for the playing
     * player for the current game or for another
     * session. The new deck index has to be possitiive
     * and valid.</p>
     * @param playingDeckIndex index of the deck to select
     *                         for the game.
     */
    public void setDeck(final int playingDeckIndex) {
        deck = new Deck(super.getDecks().get(playingDeckIndex));
        hand = new Deck();
    }

    /**
     * <p>Changes the turn of the playing player.</p>
     */
    public void changeTurn() {
        isActive = !isActive;
    }

    /**
     * <p>Get the status of the player.</p>
     * @return true if the playing players has
     * the turn or false otherwise.
     */
    public boolean getStatus() {
        return isActive;
    }

    /**
     * <p>Take one card from the player
     * selected {@code Deck} and puts it
     * in the hand of the player.</p>
     */
    public void takeCardFromDeck() {
        if (deck.isEmpty()) {
            return;
        }

        final Card removeCard = deck.removeFirstCard();
        if (removeCard != null) {
            hand.addCard(removeCard);
        }
    }

    /**
     * <p>Gain <b>more, more</b> and get ready
     * to beat your opponent in the next round.</p>
     * @param gain mana to add to the current mana.
     */
    public void gainMana(final int gain) {
        if (gain > 0) {
            mana += gain;
        }
    }

    /**
     * <p>Lost some mana, but it's alright,
     * you will get more mana in the next round.
     * Relax, take it easy.</p>
     * @param lost mana to subtract from the current mana.
     */
    public void loseMana(final int lost) {
        if (lost > 0) {
            mana -= lost;
        }
    }

    /**
     * <p>Wake up your {@code Hero} and
     * get him ready to attack in the next
     * rounds. If the hero is awaken the function
     * does nothing.</p>
     */
    public void wakeHeroUp() {
       hero.wakeUp();
    }

    /**
     * <p>Resets the mana to zero, because
     * after a win or lost game you can have
     * remaining mana, to make the game
     * fair, I reset your mana.</p>
     */
    public void resetMana() {
        mana = 0;
    }
}
