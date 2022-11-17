package players;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private int winGames;
    private int nrDecks;
    private int nrCardsInDeck;
    private final List<List<CardInput>> decks;

    public Player(final DecksInput playerInfo) {
        winGames = 0;
        nrDecks = playerInfo.getNrDecks();
        nrCardsInDeck = playerInfo.getNrCardsInDeck();
        decks = new ArrayList<>();

        for (var deck : playerInfo.getDecks()) {
            decks.add(new ArrayList<>(deck));

        }
    }

    public int getWinGames() {
        return winGames;
    }

    public List<List<CardInput>> getDecks() {
        return decks;
    }

    public void addWinGames() {
        ++winGames;
    }
}
