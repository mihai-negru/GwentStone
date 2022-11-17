package players;

import fileio.CardInput;
import fileio.DecksInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class to save the structure of a {@code player}.
 * The {@code} Player acts as a database for the Player
 * containing all the gained decks and all the win games
 * from the current game.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public class Player {

    /**
     * <p>Total win games from the
     * current season.</p>
     */
    @Getter
    private int winGames;

    /**
     * <p>Information of all available decks.</p>
     */
    @Getter
    private final List<List<CardInput>> decks;

    /**
     * <p>Constructor of the Player class
     * that will create a {@code Player database}
     * for a <i>player</i>.</p>
     * <p>The information is gained from an input
     * JSON file.</p>
     * @param playerInfo instance of {@code DecksInput}
     *                   containing information about
     *                   all the decks of the player.
     */
    public Player(final DecksInput playerInfo) {
        winGames = 0;
        decks = new ArrayList<>();

        for (var deck : playerInfo.getDecks()) {
            decks.add(new ArrayList<>(deck));

        }
    }

    /**
     * <p>Add one win game to the
     * total win games of the player.</p>
     */
    public void addWinGames() {
        ++winGames;
    }
}
