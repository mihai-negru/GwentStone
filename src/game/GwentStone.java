package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;

public final class GwentStone {

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
}
