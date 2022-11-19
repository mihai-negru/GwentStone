package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>Firestorm placeholder for the Environment Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Firestorm extends Environment {

    /**
     * <p>Build A Firestorm Card from Magic.</p>
     * @param iniMana cost to use the card.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Firestorm(final int iniMana, final String initDescription,
                     final List<String> initColors) {
        super(iniMana, initDescription, "Firestorm", initColors);
    }

    /**
     * <p>Build a Firestorm card from another Firestorm.</p>
     * @param anotherFirestorm a valid Firestorm instance.
     */
    public Firestorm(final Firestorm anotherFirestorm) {
        super(anotherFirestorm);
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
        if ((cardX < 0) || (cardX >= MAX_ROW)) {
            return "Bad positions.";
        }

        GwentStone.getGame()
                .getBattleField()
                .getRow(cardX)
                .forEach(minion -> minion.underAttack(1));

        GwentStone.getGame().getBattleField().checkTable();

        return "Ok";
    }
}
