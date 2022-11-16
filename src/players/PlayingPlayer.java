package players;

import cards.Card;
import cards.Hero;
import fileio.DecksInput;

public final class PlayingPlayer extends Player {
    private Hero gameHero;
    private int playingDeckIndex;
    private Deck playingDeck;
    private Deck playingHand;
    private boolean isActive;

    private int playerMana;

    public PlayingPlayer(final DecksInput playerInfo) {
        super(playerInfo);
        isActive = false;
        playerMana = 0;
    }

    public void setPlayerHero(final Hero initGameHero) {
        gameHero = initGameHero;
    }

    public void setPlayerPlayingDeck(final int initPlayingDeckIndex) {
        playingDeckIndex = initPlayingDeckIndex;
        playingDeck = new Deck(super.getDecks().get(playingDeckIndex));

        playingHand = new Deck();
    }

    public void setPlayerTurn() {
        isActive = !isActive;
    }

    public Deck getPlayingDeck() {
        return playingDeck;
    }

    public Deck getPlayingHand() {
        return playingHand;
    }

    public boolean getTurn() {
        return isActive;
    }

    public Hero getGameHero() {
        return gameHero;
    }

    public int getPlayerMana() {
        return playerMana;
    }

    public boolean addCardToPlayerHand() {
        if (playingDeck.isEmpty()) {
            return false;
        }

        return playingHand.addCard(playingDeck.removeFirstCard());
    }

    public void addMana(final int mana) {
        playerMana += mana;
    }

    public void unfrozeAllCards() {

    }
}
