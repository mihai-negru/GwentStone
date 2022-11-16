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

    public PlayingPlayer(final DecksInput playerInfo) {
        super(playerInfo);
        isActive = false;
    }

    public void setPlayerHero(final Hero initGameHero) {
        gameHero = initGameHero;
    }

    public void setPlayerPlayingDeck(final int initPlayingDeckIndex) {
        playingDeckIndex = initPlayingDeckIndex;
        playingDeck = new Deck(getDecks().get(playingDeckIndex));
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
}
