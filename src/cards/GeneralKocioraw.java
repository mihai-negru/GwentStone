package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>General Kocioraw Hero, special Card with unlimited powers.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class GeneralKocioraw extends Hero {

    /**
     * <p>Build a new Hero from Ashes.</p>
     * @param initMana cost to use th ability of the hero.
     * @param initDescription description of the card.
     * @param initColors no clue why they are needed.
     */
    public GeneralKocioraw(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "General Kocioraw", initColors);
    }

    /**
     * <p>Build a new Hero from ashes of another Hero.</p>
     * @param anotherGeneral a valid General Object
     */
    public GeneralKocioraw(final GeneralKocioraw anotherGeneral) {
        super(anotherGeneral);
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

        if (((activePlayer == 1) && (cardX == 0 || cardX == 1))
                || ((activePlayer == 2) && (cardX == 2 || cardX == MAX_ROW - 1))) {
            return "Selected row does not belong to the current player.";
        }

        unleashTheBeast();
        GwentStone.getGame()
                .getBattleField()
                .getRow(cardX)
                .forEach(minion -> minion.makeStronger(1));

        return "Ok";
    }
}
