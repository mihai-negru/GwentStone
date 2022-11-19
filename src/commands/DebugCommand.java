package commands;

import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

/**
 * <p>Class collection of static methods to resolve the
 * functionality for debugging process.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class DebugCommand {

    /**
     * <p>List of all available commands.</p>
     */
    private static final String COMMAND_ONE = "getCardsInHand";
    private static final String COMMAND_TWO = "getPlayerDeck";
    private static final String COMMAND_THREE = "getCardsOnTable";
    private static final String COMMAND_FOUR = "getPlayerTurn";
    private static final String COMMAND_FIVE = "getPlayerHero";
    private static final String COMMAND_SIX = "getCardAtPosition";
    private static final String COMMAND_SEVEN = "getPlayerMana";
    private static final String COMMAND_EIGHT = "getEnvironmentCardsInHand";
    private static final String COMMAND_NINE = "getFrozenCardsOnTable";
    private static final String COMMAND_TEN = "getTotalGamesPlayed";
    private static final String COMMAND_ELEVEN = "getPlayerOneWins";
    private static final String COMMAND_TWELVE = "getPlayerTwoWins";

    /**
     * <p>Don't let anyone instantiate this class.</p>
     */
    private DebugCommand() { }

    /**
     * <p>Main function to solve the debugging command.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param action command containing all the information
     *               about the solving problem.
     */
    public static void solveCommand(final ArrayNode debugOutput, final ActionsInput action) {
        if ((debugOutput == null) || (action == null)) {
            return;
        }

        switch (action.getCommand()) {
            case COMMAND_ONE -> getCardsInHand(debugOutput, action.getPlayerIdx());
            case COMMAND_TWO -> getPlayerDeck(debugOutput, action.getPlayerIdx());
            case COMMAND_THREE -> getCardsOnTable(debugOutput);
            case COMMAND_FOUR -> getPlayerTurn(debugOutput);
            case COMMAND_FIVE -> getPlayerHero(debugOutput, action.getPlayerIdx());
            case COMMAND_SIX -> getCardAtPosition(debugOutput, action.getX(), action.getY());
            case COMMAND_SEVEN -> getPlayerMana(debugOutput, action.getPlayerIdx());
            case COMMAND_EIGHT -> getEnvironmentCardsInHand(debugOutput, action.getPlayerIdx());
            case COMMAND_NINE -> getFrozenCardsOnTable(debugOutput);
            case COMMAND_TEN -> getTotalGamesPlayed(debugOutput);
            case COMMAND_ELEVEN -> getPlayerWins(debugOutput, 1, COMMAND_ELEVEN);
            case COMMAND_TWELVE -> getPlayerWins(debugOutput, 2, COMMAND_TWELVE);
            default -> { }
        }
    }

    /**
     * <p>Prints the cards in the player's play hand.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param playerIndex player index to print the cards.
     */
    private static void getCardsInHand(final ArrayNode debugOutput, final int playerIndex) {
        final PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_ONE);
        commandNode.put("playerIdx", playerIndex);

        final ArrayNode outputNode = commandNode.putArray("output");

        player.getHand()
                .getCards()
                .forEach(card -> card.toJson(outputNode.addObject()));
    }

    /**
     * <p>Prints the cards in the player's play deck.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param playerIndex player index to print the cards.
     */
    private static void getPlayerDeck(final ArrayNode debugOutput, final int playerIndex) {
        final PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_TWO);
        commandNode.put("playerIdx", playerIndex);

        final ArrayNode outputNode = commandNode.putArray("output");

        player.getDeck()
                .getCards()
                .forEach(card -> card.toJson(outputNode.addObject()));
    }

    /**
     * <p>Prints all the cards from the
     * battle table from left to right.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     */
    private static void getCardsOnTable(final ArrayNode debugOutput) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_THREE);

        final ArrayNode outputNode = commandNode.putArray("output");

        GwentStone.getGame()
                .getBattleField()
                .getTable()
                .forEach(minions -> {
                    ArrayNode minionsRow = outputNode.addArray();
                    minions.forEach(card -> card.toJson(minionsRow.addObject()));
                });
    }

    /**
     * <p>Prints the player that is active right now.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     */
    private static void getPlayerTurn(final ArrayNode debugOutput) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_FOUR);
        commandNode.put("output", GwentStone.getGame().getActivePlayerIndex());
    }

    /**
     * <p>Prints the player's hero information.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param playerIndex player index to print the hero.
     */
    private static void getPlayerHero(final ArrayNode debugOutput, final int playerIndex) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_FIVE);
        commandNode.put("playerIdx", playerIndex);

        GwentStone.getGame()
                .getPlayer(playerIndex)
                .getHero()
                .toJson(commandNode.putObject("output"));
    }

    /**
     * <p>Prints the card information from a given
     * possition in the battlefield.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output messages.
     * @param cardX row index of the card to print.
     * @param cardY column index of the card to print.
     */
    private static void getCardAtPosition(final ArrayNode debugOutput,
                                          final int cardX, final int cardY) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_SIX);
        commandNode.put("x", cardX);
        commandNode.put("y", cardY);

        final var tableRow = GwentStone.getGame().getBattleField().getTable().get(cardX);

        if (tableRow.size() > cardY) {
            tableRow.get(cardY).toJson(commandNode.putObject("output"));
        } else {
            commandNode.put("output", "No card available at that position.");
        }
    }

    /**
     * <p>Prints the player's mana points.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param playerIndex player index to print the mana points.
     */
    private static void getPlayerMana(final ArrayNode debugOutput, final int playerIndex) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_SEVEN);
        commandNode.put("playerIdx", playerIndex);
        commandNode.put("output", GwentStone.getGame().getPlayer(playerIndex).getMana());
    }

    /**
     * <p>Prints the player's environment cards
     * from playing hand.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output.
     * @param playerIndex player index to print the cards
     */
    private static void getEnvironmentCardsInHand(final ArrayNode debugOutput,
                                                 final int playerIndex) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_EIGHT);
        commandNode.put("playerIdx", playerIndex);

        final ArrayNode outputNode = commandNode.putArray("output");

        GwentStone.getGame()
                .getPlayer(playerIndex)
                .getHand()
                .getCards()
                .stream()
                .filter(card -> !card.isMinion())
                .forEach(card -> card.toJson(outputNode.addObject()));
    }

    /**
     * <p>Prints all the freezing cards from the
     * battlefield.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output messages.
     */
    private static void getFrozenCardsOnTable(final ArrayNode debugOutput) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_NINE);

        final ArrayNode cardsOutput = commandNode.putArray("output");

        GwentStone.getGame()
                .getBattleField()
                .getTable()
                .forEach(minions -> minions.stream()
                        .filter(Minion::isFreezing)
                        .forEach(minion -> minion.toJson(cardsOutput.addObject())));
    }

    /**
     * <p>Prints all the played games.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output messages.
     */
    private static void getTotalGamesPlayed(final ArrayNode debugOutput) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", COMMAND_TEN);
        commandNode.put("output", GwentStone.getGame().getPlayedGames());
    }

    /**
     * <p>Prints how many games a player has win.</p>
     * @param debugOutput {@code ArrayNode} Object to print
     *                                     the output messages.
     * @param playerIndex player index to get the win matches.
     * @param command the command to process either the first
     *                or the second player.
     */
    private static void getPlayerWins(final ArrayNode debugOutput,
                                      final int playerIndex, final String command) {
        final ObjectNode commandNode = debugOutput.addObject();
        commandNode.put("command", command);
        commandNode.put("output", GwentStone.getGame().getPlayer(playerIndex).getWinGames());
    }
}
