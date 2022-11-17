package cards;

import game.GwentStone;

import java.util.List;

public class LordRoyce extends Hero {
    public LordRoyce(final int initMana, final String initDescription,
                          final List<String> initColors) {
        super(initMana, initDescription, "Lord Royce", initColors);
    }

    public LordRoyce(final LordRoyce anotherLord) {
        super(anotherLord);
    }

    @Override
    public String useAbility(int posX, int posY) {
        if ((posX < 0) || (posX > 4)) {
            return "Bad positions";
        }

        final int activePlayer = GwentStone.getGame().getPlayingPlayerIdx();

        if (((activePlayer == 1) && (posX == 2 || posX == 3)) ||
                ((activePlayer == 2) && (posX == 0 || posX == 1))) {
            return "Selected row does not belong to the enemy.";
        }

        var affectedRow = GwentStone.getGame().getPlayingTable().getCardsRow(posX);

        int maxAttackDamage = -1;
        Minion maxAttackDamageMinion = null;

        for (var minion : affectedRow) {
            if (minion.getAttackDamage() > maxAttackDamage) {
                maxAttackDamage = minion.getAttackDamage();
                maxAttackDamageMinion = minion;
            }
        }

        if (maxAttackDamageMinion == null) {
            return "Empty row";
        }

        maxAttackDamageMinion.freeze();
        performedAnAction();

        return "Ok";
    }
}
