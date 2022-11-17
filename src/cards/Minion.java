package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.GwentStone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Minion implements Card {
    protected final int mana;
    protected int health;
    protected int attackDamage;
    protected boolean isFrozen;
    protected boolean hasAttacked;
    protected final String description;
    protected final String name;
    protected final List<String> colors;

    public Minion(final int initMana, final int initHealth, final int initAttackDamage,
                  final String initDescription, final String initName,
                  final List<String> initColors) {
        mana = initMana;
        health = initHealth;
        attackDamage = initAttackDamage;
        isFrozen = false;
        hasAttacked = false;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        colors = new ArrayList<>(initColors);
    }

    public Minion(final Minion anotherMinion) {
        this(anotherMinion.mana, anotherMinion.health, anotherMinion.attackDamage,
                anotherMinion.description, anotherMinion.name, anotherMinion.colors);
    }

    @Override
    public String getCardName() {
        return name;
    }

    @Override
    public void printJson(final ObjectNode node) {
        node.put("mana", mana);
        node.put("attackDamage", attackDamage);
        node.put("health", health);
        node.put("description", description);

        ArrayNode colorsNode = node.putArray("colors");

        colors.forEach(colorsNode::add);

        node.put("name", name);
    }

    @Override
    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public boolean isNormal() {
        return true;
    }

    public int getMana() {
        return mana;
    }

    public void gotAttacked(final int attackDamage) {
        health -= attackDamage;
    }

    public void gotFrozen() {
        isFrozen = true;
    }
    public void unFroze() {isFrozen = false;}

    public int getHealth() {
        return health;
    }

    abstract public boolean inFrontRow();
    abstract public boolean isTank();

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void resetCard() {
        hasAttacked = false;
    }

    public void subAttack(final int points) {
        if (attackDamage <= points) {
            attackDamage = 0;
        }

        attackDamage -= points;
    }

    @Override
    public String attack(int posX, int posY) {
        Minion attackedCard = GwentStone.getGame().getPlayingTable().getCard(posX, posY);

        if (attackedCard == null) {
            return "Null card";
        }

        if (GwentStone.getGame().getPlayingTable().notAttackedATank(attackedCard, posX)) {
            return "Attacked card is not of type 'Tank'.";
        }

        attackedCard.gotAttacked(attackDamage);
        hasAttacked = true;

        if (attackedCard.getHealth() <= 0) {
            if (GwentStone.getGame().getPlayingTable().removeCard(posX, posY)) {
                return "Ok";
            }

            return "Could not remove card";
        }

        return "Ok";
    }

    //    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//
//        if (obj instanceof Minion aMinion) {
//            boolean firstCheck =  (mana == aMinion.mana) &&
//                    (health == aMinion.health) &&
//                    (attackDamage == aMinion.attackDamage) &&
//                    (isFrozen == aMinion.isFrozen) &&
//                    (description.equals(aMinion.description)) &&
//                    (name.equals(aMinion.name));
//
//            if (firstCheck) {
//                if (colors.size() != aMinion.colors.size()) {
//                    return false;
//                }
//
//
//            }
//
//            return firstCheck;
//        }
//
//        return false;
//    }
}
