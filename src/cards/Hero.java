package cards;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Hero implements Card, SpecialCard {
    protected int mana;
    protected int health;
    protected final String description;
    protected final String name;
    protected final List<String> colors;

    public Hero(final int initMana, final String initDescription, final String initName,
                final List<String> initColors) {
        mana = initMana;
        description = Objects.requireNonNullElse(initDescription, "No description");
        name = Objects.requireNonNullElse(initName, "No name");
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
}
