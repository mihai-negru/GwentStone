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
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of {@code Card} objects. The class
 * performs basic instruction to process a {@code List}
 * of objects. The Deck class is one of the most important
 * classes, because it will create the instances of the cards.<p>
 *
 * @see List
 * @see ArrayList
 * @author Negru Mihai
 * @since 1.0.0
 */
public final class Deck {

    /**
     * The actual {@code List} of cards, that will
     * take care of cards functionality
     */
    @Getter
    private final List<Card> cards;

    /**
     * <p>The Deck Object will be instanced over a
     * List of {@code CardInput} given from a
     * JSON.</p>
     * @param playerCards CardInput list constaining
     *                    information about cards
     */
    public Deck(final List<CardInput> playerCards) {
        cards = new ArrayList<>();

        for (var playerCard : playerCards) {
            cards.add(createCardFromInput(playerCard));
        }
    }

    /**
     * <p>The Deck Object will be created from another Deck object.
     * The {@code Copy Constructor} is needed for creation of a
     * deck Object from non-input data. Used when selecting
     * different decks for different games</p>
     * @param anotherDeck a valid instance of a Deck Object
     */
    public Deck(final Deck anotherDeck) {
        cards = new ArrayList<>();
        anotherDeck.cards
                .forEach(card -> cards.add(copyCardInfo(card)));
    }

    /**
     * <p>Default Constructor for the Deck class.
     * Used to create Deck instances, but fill the <b>cards</b>
     * {@code List} later</p>
     */
    public Deck() {
        cards = new ArrayList<>();
    }

    /**
     * <p>Method to create an {@code Card} instance from a
     * {@code CardInput} instance</p>
     * @param playerCard a valid {@code Card Input} instance
     * @return a new created {@code Card} containing all
     * functionalities needed for a gameplay.
     */
    private Card createCardFromInput(final CardInput playerCard) {
        return switch (playerCard.getName()) {
            case "Berserker" -> new Berserker(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Disciple" -> new Disciple(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Goliath" -> new Goliath(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Miraj" -> new Miraj(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Sentinel" -> new Sentinel(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "The Cursed One" -> new TheCursedOne(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "The Ripper" -> new TheRipper(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Warden" -> new Warden(playerCard.getMana(), playerCard.getHealth(),
                        playerCard.getAttackDamage(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Firestorm" -> new Firestorm(playerCard.getMana(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Heart Hound" -> new HeartHound(playerCard.getMana(), playerCard.getDescription(),
                        playerCard.getColors());
            case "Winterfell" -> new Winterfell(playerCard.getMana(), playerCard.getDescription(),
                        playerCard.getColors());
            default -> null;
        };
    }

    /**
     * <p>Method to create a new instance of the
     * {@code Card} Interface and to copy all the
     * information from another card of the same
     * {@code Card} instance</p>
     * @param copyCard a valid instance of a {@code Card}
     *                 instance.
     * @return a new created {@code Card} containing all
     * functionalities needed for a gameplay.
     */
    private Card copyCardInfo(final Card copyCard) {
        return switch (copyCard.getName()) {
            case "Berserker" -> new Berserker((Berserker) copyCard);
            case "Disciple" -> new Disciple((Disciple) copyCard);
            case "Goliath" -> new Goliath((Goliath) copyCard);
            case "Miraj" -> new Miraj((Miraj) copyCard);
            case "Sentinel" -> new Sentinel((Sentinel) copyCard);
            case "The Cursed One" -> new TheCursedOne((TheCursedOne) copyCard);
            case "The Ripper" -> new TheRipper((TheRipper) copyCard);
            case "Warden" -> new Warden((Warden) copyCard);
            case "Firestorm" -> new Firestorm((Firestorm) copyCard);
            case "Heart Hound" -> new HeartHound((HeartHound) copyCard);
            case "Winterfell" -> new Winterfell((Winterfell) copyCard);
            default -> null;
        };
    }

    /**
     * <p>Simple method to add a {@code Card} to the
     * end of a Deck. Multiple instances of the same
     * card can be added to the deck.</p>
     * @param card non-null instance of {@code Card} Interface.
     * @return true if card was added successfully to the end of
     * the Deck, or false if any error occured.
     */
    public boolean addCard(@NonNull final Card card) {
        return cards.add(card);
    }

    /**
     * <p>Simple method to remove a card from the
     * deck, at a specified index. All the cards
     * from the right of the removed card will
     * be shifted to the left. If the index is
     * invalid then no error will be prompted
     * and no card will be removed.</p>
     * @param index a valid index to remove the card,
     *              has to be positive and less than
     *              the size of the deck.
     */
    public void removeCard(final int index) {
        if ((index >= 0) && (index < cards.size())) {
            cards.remove(index);
        }
    }

    /**
     * <p>Method to extract an {@code Card} from the
     * Deck Object specified by an input index.
     * Index has to be possitive and less than the
     * size of the Deck, otherwise <b>null</b>
     * will be returned. The function will
     * not remove the card from the deck</p>
     * @param index a valid index to extract the card
     *              from the deck.
     * @return the exact instance (no copied) of the
     * {@code Card} Object from the specified index.
     */
    public Card getCard(final int index) {
        if ((index >= 0) && (index < cards.size())) {
            return cards.get(index);
        }

        return null;
    }

    /**
     * <p>Removes the first card from the Deck
     * and returns the card instance. If no
     * cards are left a null reference will be returned</p>
     * @return the first card from the deck or null.
     */
    public Card removeFirstCard() {
        if (cards.size() > 0) {
            return cards.remove(0);
        }

        return null;
    }

    /**
     * Checks either the deck has any
     * {@code Card} left in it.
     * @return true if deck is empty
     * or false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
