package game;

import actions.Action;
import battlefield.BattleTable;
import cards.EmpressThorina;
import cards.GeneralKocioraw;
import cards.Hero;
import cards.KingMudface;
import cards.LordRoyce;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import fileio.GameInput;
import fileio.Input;
import lombok.Getter;
import players.PlayingPlayer;

import java.util.Collections;
import java.util.Random;

/**
 * <p>Actual game class that follow the Singleton pattern.</p>
 *
 * <p>The class is implemented over the Bill Pugh pattern.</p>
 *
 * @see Random
 * @see Collections
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class GwentStone {

    /**
     * <p>Max mana incrementor for the
     * game session.</p>
     */
    private static final int MAX_MANA = 10;

    /**
     * <p>An array with two playing players
     * of the current game session.</p>
     */
    private final PlayingPlayer[] players;

    /**
     * <p>The game table where all the action
     * will take place.</p>
     */
    @Getter
    private final BattleTable battleField;

    /**
     * <p>All the game/games commands.</p>
     */
    private Action sessionActions;

    /**
     * <p>Points to add to the players mana
     * after one played round.</p>
     */
    private int manaIncrementor;

    /**
     * <p>Index of the current active player.</p>
     */
    private int activePlayerIndex;

    /**
     * <p>Boolean value to take care of the
     * game status, when true game is still running.
     * If the status is false then no game commands
     * will be processed.</p>
     */
    private boolean gameIsAlive;

    /**
     * <p>Count of played games in the current
     * game session.</p>
     */
    @Getter
    private int playedGames;

    /**
     * <p>Singleton Constructor will initialize the
     * players array and will create the table board
     * for the game sessions.</p>
     */
    private GwentStone() {
        players = new PlayingPlayer[2];
        battleField = new BattleTable();
    }

    /**
     * <p>Helper inner class to maintain the Singleton
     * pattern.</p>
     */
    private static class GwentStoneHelper {
        private static final GwentStone GWENT_STONE_GAME = new GwentStone();
    }

    /**
     * <p>Get the game instance.</p>
     * @return {@code GwentStone} game instance.
     */
    public static GwentStone getGame() {
        return GwentStoneHelper.GWENT_STONE_GAME;
    }

    /**
     * <p>Entering point in the game session.</p>
     * @param gameInput input to process for the game sessions.
     * @param gameOutput output object to print the results.
     */
    public void play(final Input gameInput, final ArrayNode gameOutput) {
        preprocess(gameInput);

        gameInput.getGames().forEach(game -> {
            start(game);

            while (sessionActions.leftActions()) {
                playerTurn(gameOutput);
                playerTurn(gameOutput);
                update();
            }

            exit();
        });
    }

    /**
     * <p>Initialize the players and set the
     * played games to one played game.</p>
     * @param gameInput input to process for the game sessions
     *                  and to extract player information.
     */
    private void preprocess(final Input gameInput) {
        players[0] = new PlayingPlayer(gameInput.getPlayerOneDecks());
        players[1] = new PlayingPlayer(gameInput.getPlayerTwoDecks());
        playedGames = 1;
    }

    /**
     * <p>Process the input data and starts
     * <b>ONE GAME SESSION</b>.</p>
     * @param startInput game input info to start
     *                   one game.
     */
    private void start(final GameInput startInput) {
        manaIncrementor = 1;

        players[0].setDeck(startInput.getStartGame().getPlayerOneDeckIdx());
        players[1].setDeck(startInput.getStartGame().getPlayerTwoDeckIdx());

        players[0].setHero(riseHeroFromAshes(startInput.getStartGame().getPlayerOneHero()));
        players[1].setHero(riseHeroFromAshes(startInput.getStartGame().getPlayerTwoHero()));

        Collections.shuffle(players[0].getDeck().getCards(),
                new Random(startInput.getStartGame().getShuffleSeed()));
        Collections.shuffle(players[1].getDeck().getCards(),
                new Random(startInput.getStartGame().getShuffleSeed()));

        activePlayerIndex = startInput.getStartGame().getStartingPlayer() - 1;

        sessionActions = new Action(startInput.getActions());

        players[0].takeCardFromDeck();
        players[1].takeCardFromDeck();

        players[0].gainMana(manaIncrementor);
        players[1].gainMana(manaIncrementor);

        players[activePlayerIndex].changeTurn();

        gameIsAlive = true;
    }

    /**
     * <p>Processes one active player
     * actions.</p>
     * @param gameOutput {@code ArrayNode} Object
     *                   to print the output messages.
     */
    private void playerTurn(final ArrayNode gameOutput) {
        while (sessionActions.leftActions()
                && !sessionActions.solve(gameOutput).equals("endPlayerTurn")) {
            continue;
        }

        if (gameIsAlive) {
            players[activePlayerIndex].changeTurn();
            players[activePlayerIndex].wakeHeroUp();
            battleField.anotherRound(activePlayerIndex);
            activePlayerIndex = (activePlayerIndex + 1) % 2;
            players[activePlayerIndex].changeTurn();
        }
    }

    /**
     * <p>After one round finished its time
     * to update the game and to add mana to
     * the players and take one card
     * from playing deck.</p>
     */
    private void update() {
        if (gameIsAlive) {
            if (manaIncrementor < MAX_MANA) {
                ++manaIncrementor;
            }

            players[0].takeCardFromDeck();
            players[1].takeCardFromDeck();

            players[0].gainMana(manaIncrementor);
            players[1].gainMana(manaIncrementor);
        }
    }

    /**
     * <p>Clears the table board and
     * resets the players mana for another
     * game session. (Needed when
     * players play more than one game).</p>
     */
    private void exit() {
        battleField.clear();
        players[0].resetMana();
        players[1].resetMana();
        ++playedGames;
    }

    /**
     * <p>Create a new Hero and set to the playing player.</p>
     * @param heroCard input info for creating a Hero Card.
     * @return A newborn Hero.
     */
    private Hero riseHeroFromAshes(final CardInput heroCard) {
        return switch (heroCard.getName()) {
            case "Empress Thorina" -> new EmpressThorina(heroCard.getMana(),
                    heroCard.getDescription(), heroCard.getColors());
            case "General Kocioraw" -> new GeneralKocioraw(heroCard.getMana(),
                    heroCard.getDescription(), heroCard.getColors());
            case "King Mudface" -> new KingMudface(heroCard.getMana(),
                    heroCard.getDescription(), heroCard.getColors());
            case "Lord Royce" -> new LordRoyce(heroCard.getMana(),
                    heroCard.getDescription(), heroCard.getColors());
            default -> null;
        };
    }

    /**
     * <p>Gets the player by index which has to be
     * 1 or 2.</p>
     * @param playerIndex index of the player
     * @return the instance of the player selected.
     */
    public PlayingPlayer getPlayer(final int playerIndex) {
        return players[playerIndex - 1];
    }

    /**
     * <p>Gets the active player index. It adds
     * one to the index to maintain the
     * functionality easy.</p>
     * @return 1 or 2.
     */
    public int getActivePlayerIndex() {
        return activePlayerIndex + 1;
    }

    /**
     * <p>Sets the game to stop status.</p>
     */
    public void stopGame() {
        gameIsAlive = false;
    }

    /**
     * <p>Gets the current status of the game.</p>
     * @return true if game is alive
     * or true if the game session has ended.
     */
    public boolean getStatus() {
        return gameIsAlive;
    }
}
