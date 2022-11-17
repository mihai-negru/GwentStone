package cards;

import game.GwentStone;

import java.util.List;

public final class Disciple extends Minion implements SpecialCard {
    public Disciple(final int initMana, final int initHealth, final int initAttackDamage,
                      final String initDescription, final List<String> initColors) {
        super(initMana, initHealth, initAttackDamage, initDescription, "Disciple", initColors);
    }

    public Disciple(final Disciple anotherDisciple) {
        super(anotherDisciple);
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

        if (GwentStone.getGame().getPlayingTable().cardBelongsToEnemy(activePlayer, posX, posY)) {
            return "Attacked card does not belong to the current player.";
        }

        Minion affectedCard = GwentStone.getGame().getPlayingTable().getCard(posX, posY);

        if (affectedCard == null) {
            return "Null card.";
        }

        affectedCard.makeHarder(2);
        unleashTheBeast();

        return "Ok";
    }
}
