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

    public PlayingPlayer(final DecksInput playerInfo, final Hero initGameHero,
                         final int initPlayingDeckIndex) {
        super(playerInfo);
        gameHero = initGameHero;
        playingDeckIndex = initPlayingDeckIndex;
        playingDeck = new Deck(getDecks().get(playingDeckIndex));
        playingHand = new Deck();
        isActive = false;
    }

    public Deck getPlayingDeck() {
        return playingDeck;
    }

    public Deck getPlayingHand() {
        return playingHand;
    }
}
