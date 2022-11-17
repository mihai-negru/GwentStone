package commands;

import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.GwentStone;
import players.PlayingPlayer;

public final class DebugCommand {
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

    private static final int MAX_X = 4;
    private static final int MAX_Y = 5;

    private DebugCommand() { }

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
        }
    }

    private static void getCardsInHand(final ArrayNode debugOutput, final int playerIndex) {
        PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_ONE);
        commandNode.put("playerIdx", playerIndex);

        ArrayNode outputNode = commandNode.putArray("output");

        player.getHand()
                .getCards()
                .forEach(card -> card.toJson(outputNode.addObject()));

        debugOutput.add(commandNode);
    }

    private static void getPlayerDeck(final ArrayNode debugOutput, final int playerIndex) {
        PlayingPlayer player = GwentStone.getGame().getPlayer(playerIndex);

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_TWO);
        commandNode.put("playerIdx", playerIndex);

        ArrayNode outputNode = commandNode.putArray("output");

        player.getDeck()
                .getCards()
                .forEach(card -> card.toJson(outputNode.addObject()));

        debugOutput.add(commandNode);
    }

    private static void getCardsOnTable(final ArrayNode debugOutput) {
        var table = GwentStone.getGame().getPlayingTable().getCards();

        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_THREE);

        ArrayNode outputNode = commandNode.putArray("output");

        table.forEach(minions -> {
            ArrayNode minionsRow = outputNode.addArray();
            minions.forEach(card -> card.toJson(minionsRow.addObject()));
        });

        debugOutput.add(commandNode);
    }

    private static void getPlayerTurn(final ArrayNode debugOutput) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_FOUR);

        int playerIndex = 2;

        if (GwentStone.getGame().getPlayer(1).getStatus()) {
            playerIndex = 1;
        }

        commandNode.put("output", playerIndex);

        debugOutput.add(commandNode);
    }

    private static void getPlayerHero(final ArrayNode debugOutput, final int playerIndex) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_FIVE);
        commandNode.put("playerIdx", playerIndex);

        GwentStone.getGame()
                .getPlayer(playerIndex)
                .getHero()
                .toJson(commandNode.putObject("output"));

        debugOutput.add(commandNode);
    }

    private static void getCardAtPosition(final ArrayNode debugOutput,
                                          final int posX, final int posY) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_SIX);
        commandNode.put("x", posX);
        commandNode.put("y", posY);

        var tableRow = GwentStone.getGame().getPlayingTable().getCards().get(posX);

        if (tableRow.size() > posY) {;
            tableRow.get(posY).toJson(commandNode.putObject("output"));
        } else {
            commandNode.put("output", "No card available at that position.");
        }

        debugOutput.add(commandNode);
    }

    private static void getPlayerMana(final ArrayNode debugOutput, final int playerIndex) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_SEVEN);
        commandNode.put("playerIdx", playerIndex);
        commandNode.put("output", GwentStone.getGame().getPlayer(playerIndex).getMana());

        debugOutput.add(commandNode);
    }

    public static void getEnvironmentCardsInHand(final ArrayNode debugOutput, final int playerIndex) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_EIGHT);
        commandNode.put("playerIdx", playerIndex);

        ArrayNode cardsOutput = commandNode.putArray("output");

        GwentStone.getGame()
                .getPlayer(playerIndex)
                .getHand()
                .getCards()
                .stream()
                .filter(card -> !card.isMinion())
                .forEach(card -> card.toJson(cardsOutput.addObject()));

        debugOutput.add(commandNode);
    }

    public static void getFrozenCardsOnTable(final ArrayNode debugOutput) {
        ObjectNode commandNode = debugOutput.objectNode();
        commandNode.put("command", COMMAND_NINE);

        ArrayNode cardsOutput = commandNode.putArray("output");

        GwentStone.getGame()
                .getPlayingTable()
                .getCards()
                .forEach(minions -> minions.stream()
                        .filter(Minion::isFreezing)
                        .forEach(minion -> minion.toJson(cardsOutput.addObject())));

        debugOutput.add(commandNode);
    }

    private static void getTotalGamesPlayed(final ArrayNode statOutput) {
        ObjectNode commandNode = statOutput.objectNode();
        commandNode.put("command", COMMAND_TEN);
        commandNode.put("output", GwentStone.getGame().getPlayedGames());
        statOutput.add(commandNode);
    }

    private static void getPlayerWins(final ArrayNode statOutput, final int playerIndex, final String command) {
        ObjectNode commandNode = statOutput.objectNode();
        commandNode.put("command", command);
        commandNode.put("output", GwentStone.getGame().getPlayer(playerIndex).getWinGames());
        statOutput.add(commandNode);
    }
}
