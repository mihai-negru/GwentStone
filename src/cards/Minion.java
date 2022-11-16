package cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Minion implements Card {
    protected final int mana;
    protected int health;
    protected int attackDamage;
    protected final String description;
    protected final String name;
    protected final List<String> colors;

    public Minion(final int initMana, final int initHealth, final int initAttackDamage,
                  final String initDescription, final String initName,
                  final List<String> initColors) {
        mana = initMana;
        health = initHealth;
        attackDamage = initAttackDamage;
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
}
