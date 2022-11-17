package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>{@code Hero Card} the <b>the most powerful</b> card
 * in the entire game, be ready to get doomed.</p>
 */
public abstract class Hero implements Card, SpecialCard {

    /**
     * <p>The initial health points that are assigned
     * to every hero when the game starts.</p>
     */
    private static final int INIT_HERO_HEALTH = 30;

    /**
     * <p>The cost of the Hero card to use
     * its ability over another card.</p>
     */
    @Getter
    private final int mana;

    /**
     * <p>The health points of the Hero.</p>
     */
    @Getter
    private int health;

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
     * <p>Field to keep track
     * if the Hero has attacked or not.</p>
     */
    private boolean hasAttacked;

    /**
     * <p>Born a new Hero from the Ashes.</p>
     * @param initMana cost to use the card.
     * @param initDescription description of the card.
     * @param initName name of the card.
     * @param initColors have no clue why are these needed.
     */
    public Hero(final int initMana, final String initDescription, final String initName,
                final List<String> initColors) {
        mana = initMana;
        health = INIT_HERO_HEALTH;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        colors = new ArrayList<>(initColors);

        hasAttacked = false;
    }

    /**
     * <p>Born a new Hero from the ashes of a fallen Hero.</p>
     * @param anotherHero a valid fallen Hero
     */
    public Hero(final Hero anotherHero) {
        this(anotherHero.mana, anotherHero.description,
                anotherHero.description, anotherHero.colors);
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
        output.put("health", health);
    }

    /**
     * <p>Checks if the Card is frozen or
     * not. However the Hero cannot be frozen,
     * because if a Hero sleeps the game
     * is as lost.</p>
     * @return false all the time.
     */
    @Override
    public boolean isFreezing() {
        return false;
    }

    /**
     * <p>Wtf, a Hero is not a Minion.</p>
     * @return false, even in other dimensions.
     */
    @Override
    public boolean isMinion() {
        return false;
    }

    /**
     * <p>Dummy function to use the special
     * ability of the Hero.</p>
     * @param cardX row index of the card to attack.
     * @param cardY dummy value.
     * @return "Ok" if attacking went successfully,
     * error message otherwise.
     */
    @Override
    public String attackNow(final int cardX, final int cardY) {
        return unleashTheHell(cardX, cardY);
    }

    /**
     * <p>The Hero is attacked
     * and its health is reduced.</p>
     * @param damage points to reduce from the
     *               minion health.
     */
    public void underAttack(final int damage) {
        if (damage > 0) {
            health -= damage;
        }
    }

    /**
     * <p>Check if the Hero Card
     * sleeps or is ready for another
     * attack.</p>
     * @return true if Hero has attacked in
     * this round or false otherwise.
     */
    public boolean isSleeping() {
        return hasAttacked;
    }

    /**
     * <p>Your Hero has attacked
     * and now has to take a rest
     * and to get ready for the next
     * attack.</p>
     */
    public void unleashTheBeast() {
        hasAttacked = true;
    }

    /**
     * <p>Prepare the Hero
     * for the next attack, but
     * firstly let's wake it.</p>
     */
    public void wakeUp() {
        hasAttacked = false;
    }
}
