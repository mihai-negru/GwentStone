package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.GwentStone;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Abstract Class to encapsulate all the cards
 * that can attack and can be attacked.</p>
 *
 * <p>The Minion class has some basic functionalities
 * and also come with new abstract methods left
 * to implement to its children.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public abstract class Minion implements Card {

    /**
     * <p>The cost of the card to
     * be placed on the table or to
     * use an special ability.</p>
     */
    @Getter
    private final int mana;

    /**
     * <p>The most important fields.
     * If the Minion has 0 health it
     * is good as a dead body.</p>
     */
    @Getter
    @Setter
    private int health;

    /**
     * <p>The power of this
     * War Machine.</p>
     */
    @Getter
    @Setter
    private int attackDamage;

    /**
     * <p>If set to true the card
     * cannot attack, until it
     * is unfrozen.</p>
     */
    private boolean isFrozen;

    /**
     * <p>Field to keep track
     * if the Minion has attacked or not.</p>
     */
    private boolean hasAttacked;

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
     * <p>Construct a Minion Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initName name of the card.
     * @param initColors have no clue why are these needed.
     */
    public Minion(final int initMana, final int initHealth, final int initAttackDamage,
                  final String initDescription, final String initName,
                  final List<String> initColors) {
        mana = initMana;
        health = initHealth;
        attackDamage = initAttackDamage;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        colors = new ArrayList<>(initColors);

        isFrozen = false;
        hasAttacked = false;
    }

    /**
     * <p>Build a Minion Card from a fallen Minion.</p>
     * @param anotherMinion a valid instance of a Minion card.
     */
    public Minion(final Minion anotherMinion) {
        this(anotherMinion.mana, anotherMinion.health, anotherMinion.attackDamage,
                anotherMinion.description, anotherMinion.name, anotherMinion.colors);
    }

    /**
     * <p>Check if the Minion Card has to be placed
     * on the first row or on the last row of the
     * playing table.</p>
     * @return true if the card needs to be put in the
     * first row, false otherwise.
     */
    public abstract boolean inFrontRow();

    /**
     * <p>Special function to check if the card
     * is of {@code Tank} type.</p>
     * @return true if the Minion is a tank card,
     * or false otherwise.
     */
    public abstract boolean isTank();

    /**
     * <p>Parse the {@code Card} data
     * to a fancy JSON format.</p>
     * @param output {@code ObjectNode} instance
     *               to insert all the card's data.
     */
    @Override
    public void toJson(final ObjectNode output) {
        output.put("mana", mana);
        output.put("attackDamage", attackDamage);
        output.put("health", health);
        output.put("description", description);

        final ArrayNode colorsNode = output.putArray("colors");
        colors.forEach(colorsNode::add);

        output.put("name", name);
    }

    /**
     * <p>Checks if the Card is frozen or
     * not.</p>
     * @return true if card is frozen, false
     * otherwise.
     */
    @Override
    public boolean isFreezing() {
        return isFrozen;
    }

    /**
     * <p>Checks if the Card is a Minion card.
     * Functionality needed when inserting cards
     * on the playing table.</p>
     * @return true if card is {@code Minion} or
     * false otherwise.
     */
    @Override
    public boolean isMinion() {
        return true;
    }

    /**
     * <p>The Minion is attacked
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
     * <p>Freeze the Minion so
     * it could not attack in
     * current player turn.</p>
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * <p>Warm the Minion Card
     * and prepare for the next attack.</p>
     */
    public void heatUp() {
        isFrozen = false;
    }

    /**
     * <p>Check if the Minion Card
     * sleeps or is ready for another
     * attack.</p>
     * @return true if Minion has attacked in
     * this round or false otherwise.
     */
    public boolean isSleeping() {
        return hasAttacked;
    }

    /**
     * <p>Prepare the Minion
     * for the next attack, but
     * firstly let's wake it.</p>
     */
    public void wakeUp() {
        hasAttacked = false;
    }

    /**
     * <p>Reduce the Minion attack
     * damage by some points.</p>
     * @param points points to reduce the attack
     *               damage.
     */
    public void makeWeaker(final int points) {
        if (attackDamage <= points) {
            attackDamage = 0;
        } else {
            attackDamage -= points;
        }
    }

    /**
     * <p>Add more power to your
     * War Machine by some points.</p>
     * @param points points to add to the
     *               minion's attack damage.
     */
    public void makeStronger(final int points) {
        if (points > 0) {
            attackDamage += points;
        }
    }

    /**
     * <p>Make your Minion,
     * harder to get killed by
     * adding some points to its
     * health points.</p>
     * @param points points to add to
     *               the minion's health.
     */
    public void makeHarder(final int points) {
        if (points >= 0) {
            health += points;
        }
    }

    /**
     * <p>Your Minion has attacked
     * and now has to take a rest
     * and to get ready for the next
     * attack.</p>
     */
    public void unleashTheBeast() {
        hasAttacked = true;
    }

    /**
     * <p>Performs now an attack on another Card
     * and locks the card for current turn.</p>
     * @param cardX row index of the card to attack.
     * @param cardY column index of the card to attack.
     * @return "Ok" if the attacking process went
     * successfully, or an error {@code String} to
     * catch later.
     */
    @Override
    public String attackNow(final int cardX, final int cardY) {
        final Minion minionToAttack = GwentStone.getGame().getBattleField().getCard(cardX, cardY);

        if (minionToAttack == null) {
            return "Null card.";
        }

        if (GwentStone.getGame().getBattleField().notAttackedATank(minionToAttack, cardX)) {
            return "Attacked card is not of type 'Tank'.";
        }

        unleashTheBeast();
        minionToAttack.underAttack(attackDamage);

        if (minionToAttack.getHealth() <= 0) {
            if (GwentStone.getGame().getBattleField().removeCard(cardX, cardY)) {
                return "Ok";
            }

            return "Could not remove card.";
        }

        return "Ok";
    }
}
