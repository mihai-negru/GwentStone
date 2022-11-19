package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>Winterfell placeholder for the Environment Card.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class Winterfell extends Environment {

    /**
     * <p>Build A Winterfell Card from Magic.</p>
     * @param iniMana cost to use the card.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public Winterfell(final int iniMana, final String initDescription,
                      final List<String> initColors) {
        super(iniMana, initDescription, "Winterfell", initColors);
    }

    /**
     * <p>Build a Winterfell card from another Firestorm.</p>
     * @param anotherWinterfell a valid Winterfell instance.
     */
    public Winterfell(final Winterfell anotherWinterfell) {
        super(anotherWinterfell);
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
            return "Bad positions";
        }

        GwentStone.getGame()
                .getBattleField()
                .getRow(cardX)
                .forEach(Minion::freeze);

        return "Ok";
    }
}
