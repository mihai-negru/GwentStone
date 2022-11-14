package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;

public final class StatisticsCommand {
    private static final int MAX_X = 4;
    private static final int MAX_Y = 5;

    private StatisticsCommand() { }
    public static void solveCommand(final ArrayNode statOutput, final String command) {
        if ((statOutput == null) || (command == null)) {
            return;
        }

        getPlayerTurn(statOutput);
    }

    public static void solveCommand(final ArrayNode statOutput, final String command,
                                    final int playerIndex) {
        if ((statOutput == null) || (command == null) || (playerIndex < 0) || (playerIndex > 2)) {
            return;
        }

        getPlayerHero(statOutput, playerIndex);
    }

    public static void solveCommand(final ArrayNode statOutput, final String command,
                                    final int posX, final int posY) {
        if ((statOutput == null) || (command == null)) {
            return;
        }

        if ((posX < 0) || (posX > MAX_X) || (posY < 0) || (posY > MAX_Y)) {
            return;
        }

        getCardAtPosition(statOutput, posX, posY);
    }

    private static void getPlayerTurn(final ArrayNode statOutput) {

    }

    private static void getPlayerHero(final ArrayNode statOutput, final int playerIndex) {

    }

    private static void getCardAtPosition(final ArrayNode statOutput,
                                          final int posX, final int posY) {
    }
}
