package actions;

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

    public ActionsInput getNextAction() {
        return actions.remove(0);
    }
}
