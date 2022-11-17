package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Environment implements Card {
    protected final int mana;
    protected final String description;
    protected final String name;
    protected final List<String> colors;

    public Environment(final int iniMana, final String initDescription, final String initName,
                       final List<String> initColors) {
        mana = iniMana;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
        colors = new ArrayList<>(initColors);
    }

    public Environment(final Environment anotherEnvironment) {
        this(anotherEnvironment.mana, anotherEnvironment.description, anotherEnvironment.name,
                anotherEnvironment.colors);
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

        colors.forEach(colorsNode::add);

        node.put("name", name);
    }

    @Override
    public boolean isFrozen() {
        return false;
    }

    @Override
    public boolean isNormal() {
        return false;
    }

    public int getMana() {
        return mana;
    }
}
