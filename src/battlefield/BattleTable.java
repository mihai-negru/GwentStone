package battlefield;

import cards.Minion;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BattleTable {
    private final List<List<Minion>> table;

    public BattleTable() {
        table = new ArrayList<>(4);

        for (int i = 0; i < 4; ++i) {
            table.add(new ArrayList<>(5));
        }
    }

    public List<List<Minion>> getCards() {
        return table;
    }
}
