package cards;

import game.GwentStone;

import java.util.List;

public final class TheCursedOne extends Minion implements SpecialCard {
    public TheCursedOne(final int initMana, final int initHealth, final int initAttackDamage,
                        final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage,
                initDescription, "The Cursed One", initColors);
    }

    public TheCursedOne(final TheCursedOne anotherCursedOne) {
        super(anotherCursedOne);
    }

    @Override
    public boolean inFrontRow() {
        return false;
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

        final int affectedCardAttackDamage = affectedCard.getAttackDamage();

        if (affectedCardAttackDamage <= 0) {
            if (GwentStone.getGame().getPlayingTable().removeCard(posX, posY)) {
                return "Ok";
            }

            return "Could not remove card";
        }

        affectedCard.setAttackDamage(affectedCard.getHealth());
        affectedCard.setHealth(affectedCardAttackDamage);
        performedAnAction();

        return "Ok";
    }
}
