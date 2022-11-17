package cards;

import game.GwentStone;

import java.util.List;

public final class TheRipper extends Minion implements SpecialCard {
    public TheRipper(final int initMana, final int initHealth, final int initAttackDamage,
                     final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "The Ripper", initColors);
    }

    public TheRipper(final TheRipper anotherRipper) {
        super(anotherRipper);
    }

    @Override
    public boolean inFrontRow() {
        return true;
    }

    @Override
    public boolean isTank() {
        return false;
    }

    @Override
    public String useAbility(int posX, int posY) {
        if ((posX < 0) || (posX > 4) || (posY < 0) || (posY > 5)) {
            return "Bad positions";
        }

        final int activePlayer = GwentStone.getGame().getPlayingPlayerIdx() - 1;

        if (!GwentStone.getGame().getPlayingTable().cardBelongsToEnemy(activePlayer, posX, posY)) {
            return "Attacked card does not belong to the enemy.";
        }

        Minion affectedCard = GwentStone.getGame().getPlayingTable().getCard(posX, posY);

        if (affectedCard == null) {
            return "Null card.";
        }

        if (GwentStone.getGame().getPlayingTable().notAttackedATank(affectedCard, posX)) {
            return "Attacked card is not of type 'Tank'.";
        }

        affectedCard.makeWeaker(2);

        unleashTheBeast();

        return "Ok";
    }
}
