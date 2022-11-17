package cards;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Card {
    String getCardName();
    void printJson(ObjectNode node);
    boolean isFrozen();
    boolean isNormal();

    boolean attack(int posX, int posY);
}
