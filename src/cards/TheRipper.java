package cards;

import game.GwentStone;

import java.util.List;

/**
 * <p>The Ripper placeholder Minion Card with special abilities.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public final class TheRipper extends Minion implements SpecialCard {

    /**
     * <p>Construct a Ripper Card from ashes.</p>
     * @param initMana cost to place the card.
     * @param initHealth initial health of the card.
     * @param initAttackDamage initial power.
     * @param initDescription description of the card.
     * @param initColors have no clue why are these needed.
     */
    public TheRipper(final int initMana, final int initHealth, final int initAttackDamage,
                     final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "The Ripper", initColors);
    }

    /**
     * <p>Build a Ripper Card from a fallen Minion.</p>
     * @param anotherRipper a valid instance of a Minion card.
     */
    public TheRipper(final TheRipper anotherRipper) {
        super(anotherRipper);
    }

    /**
     * <p>A Ripper Minion should
     * be placed in the first row.
     * Its anger scary anyone.</p>
     * @return always true.
     */
    @Override
    public boolean inFrontRow() {
        return true;
    }

    /**
     * <p>The Ripper is not a Tank.</p>
     * @return false every time.
     */
    @Override
    public boolean isTank() {
        return false;
    }

    /**
     * <p>Special ability that just
     * some card can posses. When the
     * card is using the ability the
     * hell comes to the surface.</p>
     *
     * <p>For The Riper, it uses the Weak Knees
     * ability.</p>
     *
     * @param cardX row index to attack the card.
     * @param cardY column index to attack the card.
     * @return "Ok" string if everything when successfully
     * or an error message to catch later and process it.
     */
    @Override
    public String unleashTheHell(final int cardX, final int cardY) {
        if ((cardX < 0) || (cardX > MAX_ROW) || (cardY < 0) || (cardY > MAX_COLUMN)) {
            return "Bad positions.";
        }

        final int activePlayer = GwentStone.getGame().getActivePlayerIndex() - 1;

        if (!GwentStone.getGame().getBattleField()
                .isEnemy(activePlayer, cardX)) {
            return "Attacked card does not belong to the enemy.";
        }

        final Minion minion = GwentStone.getGame().getBattleField().getCard(cardX, cardY);

        if (minion == null) {
            return "Null card.";
        }

        if (GwentStone.getGame().getBattleField().notAttackedATank(minion, cardX)) {
            return "Attacked card is not of type 'Tank'.";
        }

        unleashTheBeast();
        minion.makeWeaker(2);

        return "Ok";
    }
}
