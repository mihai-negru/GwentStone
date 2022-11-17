package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Minion implements Card {
    protected final int mana;
    protected int health;
    protected int attackDamage;
    protected boolean isFrozen;
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
        for (var color : colors) {
            colorsNode.add(color);
        }

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

    public int getHealth() {
        return health;
    }

    abstract public boolean inFrontRow();

    @Override
    public boolean useAbility(int posX, int posY) {
        return false;
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
