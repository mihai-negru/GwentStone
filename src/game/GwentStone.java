package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Input;
import players.PlayingPlayer;

import java.util.ArrayList;

public final class GwentStone {
    private PlayingPlayer firstPlayer;
    private PlayingPlayer secondPlayer;

    private GwentStone() {
    }

    private static class GwentStoneHelper {
        private static final GwentStone GWENT_STONE_GAME = new GwentStone();
    }

    public static GwentStone getGame() {
        return GwentStoneHelper.GWENT_STONE_GAME;
    }

    /**
     *
     * @param gameInput
     * @param gameOutput
     */
    public void start(final Input gameInput, final ArrayNode gameOutput) {
        game(gameOutput);
        stop(gameOutput);
    }

    private void game(final ArrayNode gameOutput) {
    }

    private void stop(final ArrayNode gameOutput) {
    }

    public PlayingPlayer getPlayer(final int playerIndex) {
        if (playerIndex == 1) {
            return firstPlayer;
        } else if (playerIndex == 2) {
            return secondPlayer;
        }

        return null;
    }
}
