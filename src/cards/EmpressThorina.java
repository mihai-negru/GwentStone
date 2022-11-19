package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>Empress Thorina Hero, special Card with unlimited powers.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class EmpressThorina extends Hero {

    /**
     * <p>Build a new Hero from Ashes.</p>
     * @param initMana cost to use th ability of the hero.
     * @param initDescription description of the card.
     * @param initColors no clue why they are needed.
     */
    public EmpressThorina(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "Empress Thorina", initColors);
    }

    /**
     * <p>Build a new Hero from ashes of another Hero.</p>
     * @param anotherEmpress a valid Empress Thorina Object
     */
    public EmpressThorina(final EmpressThorina anotherEmpress) {
        super(anotherEmpress);
    }

    /**
     * <p>Special ability that just
     * some card can posses. When the
     * card is using the ability the
     * hell comes to the surface.</p>
     * @param cardX row index to attack the card.
     * @param cardY column index to attack the card.
     * @return "Ok" string if everything when successfully
     * or an error message to catch later and process it.
     */
    @Override
    public String unleashTheHell(final int cardX, final int cardY) {
        if ((cardX < 0) || (cardX > MAX_ROW)) {
            return "Bad positions.";
        }

        final int activePlayer = GwentStone.getGame().getActivePlayerIndex();

        if (((activePlayer == 1) && (cardX == 2 || cardX == MAX_ROW - 1))
                || ((activePlayer == 2) && (cardX == 0 || cardX == 1))) {
            return "Selected row does not belong to the enemy.";
        }

        final var row = GwentStone.getGame().getBattleField().getRow(cardX);

        int maxHealth = -1;
        int minionIndex = -1;
        int iterIndex = 0;

        for (var minion : row) {
            if (minion.getHealth() > maxHealth) {
                maxHealth = minion.getHealth();
                minionIndex = iterIndex;
            }

            ++iterIndex;
        }

        if (minionIndex < 0) {
            return "Empty row.";
        }

        unleashTheBeast();
        GwentStone.getGame().getBattleField().removeCard(cardX, minionIndex);

        return "Ok";
    }
}
