package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Hero implements Card, SpecialCard {
    private final int mana;
    private int health;
    private final String description;
    private final String name;
    private final List<String> colors;

    public Hero(final int initMana, final String initDescription, final String initName,
                final List<String> initColors) {
        mana = initMana;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        health = 30;
        colors = new ArrayList<>(initColors);
    }

    public Hero(final Hero anotherHero) {
        this(anotherHero.mana, anotherHero.description,
                anotherHero.description, anotherHero.colors);
    }

    @Override
    public String getCardName() {
        return name;
    }

    @Override
    public void printJson(final ObjectNode node) {
        node.put("mana", mana);
        node.put("description", description);

        ArrayNode colorsNode = node.putArray("colors");
        for (var color : colors) {
            colorsNode.add(color);
        }

        node.put("name", name);
        node.put("health", health);
    }

    @Override
    public boolean isFrozen() {
        return false;
    }

    @Override
    public boolean isNormal() {
        return false;
    }

    @Override
    public String attack(int posX, int posY) {
        return useAbility(posX, posY);
    }

    public void gotAttacked(final int points) {
        if (points > 0) {
            health -= points;
        }
    }

    public int getHealth() {
        return health;
    }
}
