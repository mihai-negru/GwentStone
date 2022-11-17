package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Environment Card to perform an action over
 * a row from the playing table. Very powerful cards
 * if you know how to use them.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public abstract class Environment implements Card {

    /**
     * <p>The cost of the card to
     * be used over a row.</p>
     */
    @Getter
    private final int mana;

    /**
     * <p>Some good words about the card.</p>
     */
    private final String description;

    /**
     * <p>The name of the card to identify.</p>
     */
    @Getter
    private final String name;

    /**
     * <p>A unuseful thing to
     * check our majesty in working
     * with {@code Lists}.</p>
     */
    private final List<String> colors;

    /**
     * <p>Construct an Environment Card from ashes.</p>
     * @param initMana cost to use the card.
     * @param initDescription description of the card.
     * @param initName name of the card.
     * @param initColors have no clue why are these needed.
     */
    public Environment(final int initMana, final String initDescription, final String initName,
                       final List<String> initColors) {
        mana = initMana;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        colors = new ArrayList<>(initColors);
    }

    /**
     * <p>Build an Environment Card from a fallen Card.</p>
     * @param anotherEnvironment a valid instance of an Environment card.
     */
    public Environment(final Environment anotherEnvironment) {
        this(anotherEnvironment.mana, anotherEnvironment.description, anotherEnvironment.name,
                anotherEnvironment.colors);
    }

    /**
     * <p>Parse the {@code Card} data
     * to a fancy JSON format.</p>
     * @param output {@code ObjectNode} instance
     *               to insert all the card's data.
     */
    @Override
    public void toJson(final ObjectNode output) {
        output.put("mana", mana);
        output.put("description", description);

        final ArrayNode colorsNode = output.putArray("colors");
        colors.forEach(colorsNode::add);

        output.put("name", name);
    }

    /**
     * <p>Checks if the Card is frozen or
     * not. However an Environment cannot
     * be frozen.</p>
     * @return false every time.
     */
    @Override
    public boolean isFreezing() {
        return false;
    }

    /**
     * <p>An Environment Card.
     * cannot be a Minion Card.</p>
     * @return false every time.
     */
    @Override
    public boolean isMinion() {
        return false;
    }
}
