package game;

import actions.Action;
import battlefield.BattleTable;
import cards.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import fileio.GameInput;
import fileio.Input;
import players.PlayingPlayer;

import java.util.Collections;
import java.util.Random;

public final class GwentStone {
    private final PlayingPlayer[] playingPlayers;
    private final BattleTable playingTable;
    private Action playingActions;

    private int manaIncrementor;
    private int playingPlayerIdx;

    private boolean gameIsAlive;

    private int gamesPlayed = 1;

    private GwentStone() {
        playingPlayers = new PlayingPlayer[2];
        playingTable = new BattleTable();
    }

    private static class GwentStoneHelper {
        private static final GwentStone GWENT_STONE_GAME = new GwentStone();
    }

    public static GwentStone getGame() {
        return GwentStoneHelper.GWENT_STONE_GAME;
    }

    public void play(final Input gameInput, final ArrayNode gameOutput) {
        preprocess(gameInput);

        for (var game : gameInput.getGames()) {
            if (gamesPlayed > 11) {
                break;
            }
            start(game);

            while (!playingActions.noMoreActions()) {
                playerTurn(gameOutput);
                playerTurn(gameOutput);
                update();
            }

            gamesPlayed++;

            exit();
        }
    }

    private void preprocess(final Input gameInput) {
        manaIncrementor = 1;
        playingPlayerIdx = -1;
        playingPlayers[0] = new PlayingPlayer(gameInput.getPlayerOneDecks());
        playingPlayers[1] = new PlayingPlayer(gameInput.getPlayerTwoDecks());
    }

    private void start(final GameInput startInput) {
        playingPlayers[0].setPlayerPlayingDeck(startInput.getStartGame().getPlayerOneDeckIdx());
        playingPlayers[1].setPlayerPlayingDeck(startInput.getStartGame().getPlayerTwoDeckIdx());

        playingPlayers[0].setPlayerHero(aNewHeroIsBorn(startInput.getStartGame().getPlayerOneHero()));
        playingPlayers[1].setPlayerHero(aNewHeroIsBorn(startInput.getStartGame().getPlayerTwoHero()));

        Collections.shuffle(playingPlayers[0].getPlayingDeck().getCards(), new Random(startInput.getStartGame().getShuffleSeed()));
        Collections.shuffle(playingPlayers[1].getPlayingDeck().getCards(), new Random(startInput.getStartGame().getShuffleSeed()));

        playingPlayerIdx = startInput.getStartGame().getStartingPlayer() - 1;

        playingActions = new Action(startInput.getActions());

        playingPlayers[0].addCardToPlayerHand();
        playingPlayers[1].addCardToPlayerHand();

        playingPlayers[0].addMana(manaIncrementor);
        playingPlayers[1].addMana(manaIncrementor);

        playingPlayers[playingPlayerIdx].setPlayerTurn();

        gameIsAlive = true;
    }

    private void playerTurn(final ArrayNode gameOutput) {
        while (!playingActions.noMoreActions() && !playingActions.solve(gameOutput).equals(
                "endPlayerTurn")) {}

        playingPlayers[playingPlayerIdx].setPlayerTurn();
        playingTable.resetPlayerCards(playingPlayerIdx);
        playingPlayerIdx = (playingPlayerIdx + 1) % 2;
        playingPlayers[playingPlayerIdx].setPlayerTurn();
    }

    private void update() {
        if (manaIncrementor < 10) {
            ++manaIncrementor;
        }

        playingPlayers[0].addCardToPlayerHand();
        playingPlayers[1].addCardToPlayerHand();

        playingPlayers[0].addMana(manaIncrementor);
        playingPlayers[1].addMana(manaIncrementor);
    }

    private void exit() {
        playingTable.clearTable();
        System.out.println("--------------------------");
    }

    private Hero aNewHeroIsBorn(final CardInput heroCard) {
        Hero hero = null;

        String heroName = heroCard.getName();

        if (heroName.equals("Empress Thorina")) {
            hero = new EmpressThorina(heroCard.getMana(), heroCard.getDescription(), heroCard.getColors());
        } else if (heroName.equals("General Kocioraw")) {
            hero = new GeneralKocioraw(heroCard.getMana(), heroCard.getDescription(), heroCard.getColors());
        } else if (heroName.equals("King Mudface")) {
            hero = new KingMudface(heroCard.getMana(), heroCard.getDescription(), heroCard.getColors());
        } else {
            hero = new LordRoyce(heroCard.getMana(), heroCard.getDescription(), heroCard.getColors());
        }

        return hero;
    }

    public PlayingPlayer getPlayer(final int playerIndex) {
        return playingPlayers[playerIndex - 1];
    }

    public BattleTable getPlayingTable() {
        return playingTable;
    }

    public int getPlayingPlayerIdx() {
        return playingPlayerIdx + 1;
    }

    public void stopGame() {
        gameIsAlive = false;
    }
}
