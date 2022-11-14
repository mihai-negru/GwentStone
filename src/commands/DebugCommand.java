package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;

public final class DebugCommand {
    private static final String COMMAND_ONE = "getCardsInHand";
    private static final String COMMAND_TWO = "getPlayerDeck";
    private static final String COMMAND_THREE = "getCardsOnTable";

    private DebugCommand() { }

    public static void solveCommand(final ArrayNode debugOutput,
                                    final String command, final int playerIndex) {
        if ((debugOutput == null) || (command == null) || (playerIndex < 0) || (playerIndex > 2)) {
            return;
        }

        if (command.equals(COMMAND_ONE)) {
            getCardsInHand(debugOutput, playerIndex);
        } else if (command.equals(COMMAND_TWO)) {
            getPlayerDeck(debugOutput, playerIndex);
        }
    }

    public static void solveCommand(final ArrayNode debugOutput, final String command) {
        if ((debugOutput == null) || (command == null)) {
            return;
        }

        if (command.equals(COMMAND_THREE)) {
            getCardsOnTable(debugOutput);
        }
    }

    private static void getCardsInHand(final ArrayNode debugOutput, final int playerIndex) {

    }

    private static void getPlayerDeck(final ArrayNode debugOutput, final int playerIndex) {

    }

    private static void getCardsOnTable(final ArrayNode debugOutput) {

    }
}
