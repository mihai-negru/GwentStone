package players;

import fileio.DecksInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private int playedGames;
    private int winGames;
    private int nrDecks;
    private int nrCardsInDeck;
    private final List<Deck> decks;

    public Player(final DecksInput playerInfo) {
        playedGames = 0;
        winGames = 0;
        nrDecks = playerInfo.getNrDecks();
        nrCardsInDeck = playerInfo.getNrCardsInDeck();
        decks = new ArrayList<>(nrDecks);

        for (var playerDeck : playerInfo.getDecks()) {
            decks.add(new Deck(playerDeck, nrCardsInDeck));
        }
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getWinGames() {
        return winGames;
    }

    public List<Deck> getDecks() {
        return Collections.unmodifiableList(decks);
    }
}
