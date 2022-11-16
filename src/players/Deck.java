package players;

import cards.Berserker;
import cards.Card;
import cards.Disciple;
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

    public Deck(final List<CardInput> playerCards) {
        cards = new ArrayList<>();

        for (var playerCard : playerCards) {
            cards.add(createCardFromInput(playerCard));
        }
    }

    public Deck(final Deck anotherDeck) {
        cards = new ArrayList<>();

        for (Card copyCard : anotherDeck.cards) {
            cards.add(copyCardInfo(copyCard));
        }
    }

    public Deck() {
        cards = new ArrayList<>();
    }

    private Card createCardFromInput(final CardInput playerCard) {
        Card card = null;

        String cardName = playerCard.getName();

        if (cardName.equals("Berserker")) {
            card = new Berserker(playerCard.getMana(), playerCard.getHealth(),
                    playerCard.getAttackDamage(), playerCard.getDescription(),
                    playerCard.getColors());
        } else if (cardName.equals("Disciple")) {
            card = new Disciple(playerCard.getMana(), playerCard.getHealth(),
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

    private Card copyCardInfo(final Card copyCard) {
        Card card = null;

        String cardName = copyCard.getCardName();

        if (cardName.equals("Berserker")) {
            card = new Berserker((Berserker) copyCard);
        } else if (cardName.equals("Disciple")) {
            card = new Disciple((Disciple) copyCard);
        } else if (cardName.equals("Goliath")) {
            card = new Goliath((Goliath) copyCard);
        } else if (cardName.equals("Miraj")) {
            card = new Miraj((Miraj) copyCard);
        } else if (cardName.equals("Sentinel")) {
            card = new Sentinel((Sentinel) copyCard);
        } else if (cardName.equals("The Cursed One")) {
            card = new TheCursedOne((TheCursedOne) copyCard);
        } else if (cardName.equals("The Ripper")) {
            card = new TheRipper((TheRipper) copyCard);
        } else if (cardName.equals("Warden")) {
            card = new Warden((Warden) copyCard);
        } else if (cardName.equals("Firestorm")) {
            card = new Firestorm((Firestorm) copyCard);
        } else if (cardName.equals("Heart Hound")) {
            card = new HeartHound((HeartHound) copyCard);
        } else if (cardName.equals("Winterfell")) {
            card = new Winterfell((Winterfell) copyCard);
        }

        return card;
    }

    public boolean addCard(final Card card) {
        if (card != null) {
            return cards.add(card);
        }

        return false;
    }

    public boolean removeCard(final int index) {
        if ((index >= 0) && (index < cards.size())) {
            cards.remove(index);

            return true;
        }

        return false;
    }

    public Card getCard(final int index) {
        if ((index >= 0) && (index < cards.size())) {
            return cards.get(index);
        }

        return null;
    }

    public Card removeFirstCard() {
        if (cards.size() > 0) {
            return cards.remove(0);
        }

        return null;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
