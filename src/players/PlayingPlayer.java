package players;

import cards.Hero;
import fileio.DecksInput;

public final class PlayingPlayer extends Player {
    private Hero gameHero;
    private int playingDeck;
    private boolean isActive;

    public PlayingPlayer(final DecksInput playerInfo, final Hero initGameHero,
                         final int initPlayingDeck) {
        super(playerInfo);
        gameHero = initGameHero;
        playingDeck = initPlayingDeck;
        isActive = false;
    }
}
