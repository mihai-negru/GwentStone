package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.*;
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

    public String solve(final ArrayNode gameOutput) {
        var action = actions.remove(0);

        if (action.getCommand().startsWith("get")) {
            solveNextDebugCommand(gameOutput, action);
        } else {
            solveNextAction(gameOutput, action);
        }

        return action.getCommand();
    }

    private void solveNextAction(final ArrayNode gameOutput, final ActionsInput action) {
        switch (action.getCommand()) {
            case "placeCard" -> BattleTableCommand.solveCommand(gameOutput, action);
            case "useEnvironmentCard" -> EnvironmentCommand.solveCommand(gameOutput, action);
            case "cardUsesAttack" -> AttackCommand.solveCommand(gameOutput, action);
            case "cardUsesAbility" -> AbilityCommand.solveCommand(gameOutput, action);
            case "useAttackHero" -> AttackHeroCommand.solveCommand(gameOutput, action);
        }
    }

    private void solveNextDebugCommand(final ArrayNode gameOutput, final ActionsInput action) {
        DebugCommand.solveCommand(gameOutput, action);
    }


    public List<ActionsInput> ads() {
        return actions;
    }
}
