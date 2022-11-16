package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.BattleTableCommand;
import commands.DebugCommand;
import fileio.ActionsInput;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private final List<ActionsInput> actions;

    public Action(final List<ActionsInput> initActions) {
        actions = new ArrayList<>(initActions);
    }

    public boolean noMoreActions() {
        return actions.isEmpty();
    }

    public String solveNextAction(final ArrayNode gameOutput) {
        ActionsInput action = actions.remove(0);

        switch (action.getCommand()) {
            case "placeCard" -> BattleTableCommand.solveCommand(gameOutput, action);
        }

        return action.getCommand();
    }

    public void solveNextDebugCommand(final ArrayNode gameOutput) {
        DebugCommand.solveCommand(gameOutput, actions.remove(0));
    }

    public boolean isNextDebugCommand() {
        if (actions.size() > 0) {
            ActionsInput action = actions.get(0);

            return action.getCommand().startsWith("get");
        }

        return false;
    }
}
