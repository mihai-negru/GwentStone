package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.AbilityCommand;
import commands.AttackCommand;
import commands.AttackHeroCommand;
import commands.BattleTableCommand;
import commands.DebugCommand;
import commands.EnvironmentCommand;
import commands.HeroCommand;
import fileio.ActionsInput;
import game.GwentStone;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Action Class that maintain the command solving.</p>
 *
 * <p>A command will be processed and a special function
 * will be called to solve the specified command.</p>
 *
 * <p>The command are divided in two groups. One for
 * debugging and one for effective actions between players.</p>
 *
 * @see List
 * @see ArrayList
 * @author Mihai Negru
 * @since 1.0.0
 */
public class Action {

    /**
     * <p>A List of actions will all game commands
     * that need to be processed.</p>
     */
    private final List<ActionsInput> actions;

    /**
     * <p>Take one list of actions and set the current game commands.</p>
     * @param initActions {@code List} of commands for one game session.
     */
    public Action(final List<ActionsInput> initActions) {
        actions = new ArrayList<>(initActions);
    }

    /**
     * <p>Checks if there are any left commands to process
     * for the current playing game session.</p>
     * @return true if there are some commands left.
     * or false otherwise.
     */
    public boolean leftActions() {
        return !actions.isEmpty();
    }

    /**
     * <p>Decides if the command is for
     * debugging or for game processing
     * and calls the specific function to
     * solve the specified command.</p>
     * @param gameOutput {@code ArrayNode} Object to
     *                                    print the output.
     * @return A string containing the name of the
     * current processed command.
     */
    public String solve(final ArrayNode gameOutput) {
        final var action = actions.remove(0);

        if (action.getCommand().startsWith("get")) {
            solveNextDebugCommand(gameOutput, action);
        } else {
            if (GwentStone.getGame().getStatus()) {
                solveNextAction(gameOutput, action);
            }
        }

        return action.getCommand();
    }

    /**
     * <p>Solves a processing game command.
     * According to the name of the function
     * a class method solver will be called.</p>
     * @param gameOutput {@code ArrayNode} to print the
     *                                    output.
     * @param action {@code ActionInput} action containing
     *                                  all the command
     *                                  information.
     */
    private void solveNextAction(final ArrayNode gameOutput, final ActionsInput action) {
        switch (action.getCommand()) {
            case "placeCard" -> BattleTableCommand.solveCommand(gameOutput, action);
            case "useEnvironmentCard" -> EnvironmentCommand.solveCommand(gameOutput, action);
            case "cardUsesAttack" -> AttackCommand.solveCommand(gameOutput, action);
            case "cardUsesAbility" -> AbilityCommand.solveCommand(gameOutput, action);
            case "useAttackHero" -> AttackHeroCommand.solveCommand(gameOutput, action);
            case "useHeroAbility" -> HeroCommand.solveCommand(gameOutput, action);
            default -> { }
        }
    }

    /**
     * <p>Solves a debugging command.</p>
     * @param gameOutput {@code ArrayNode} to print the output.
     * @param action {@code ActionInput} action containing
     *                                  all the command information.
     */
    private void solveNextDebugCommand(final ArrayNode gameOutput, final ActionsInput action) {
        DebugCommand.solveCommand(gameOutput, action);
    }
}
