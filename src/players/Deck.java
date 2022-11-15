package players;

import cards.Berserker;
import cards.Card;
import cards.Discipline;
import cards.Firestorm;
import cards.Goliath;
import cards.HeartHound;
import cards.Miraj;
import cards.Sentinel;
import cards.TheCursedOne;
import cards.TheRipper;
import cards.Warden;
import cards.Winterfell;
import fileio.CardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Deck {
    private final List<Card> cards;

    public Deck(final ArrayList<CardInput> playerCards, final int nrCardsInDeck) {
        cards = new ArrayList<>(nrCardsInDeck);

        for (var playerCard : playerCards) {
            cards.add(createCard(playerCard));
        }
    }

    public Card createCard(final CardInput playerCard) {
        Card card = null;

        String cardName = playerCard.getName();

        if (cardName.equals("Berserker")) {
            card = new Berserker(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Discipline")) {
            card = new Discipline(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Goliath")) {
            card = new Goliath(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Miraj")) {
            card = new Miraj(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Sentinel")) {
            card = new Sentinel(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("The Cursed One")) {
            card = new TheCursedOne(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("The Ripper")) {
            card = new TheRipper(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Warden")) {
            card = new Warden(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Firestorm")) {
            card = new Firestorm(playerCard.getMana(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Heart Hound")) {
            card = new HeartHound(playerCard.getMana(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Winterfell")) {
            card = new Winterfell(playerCard.getMana(), playerCard.getDescription(),
                    playerCard.getColors());
        }

        return card;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
