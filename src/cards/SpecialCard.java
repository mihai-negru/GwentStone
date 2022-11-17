package cards;

/**
 * <p>Special Interface to mark that
 * the Card can perform a special
 * attack under some circumstances.</p>
 *
 * @author Mihai Negru
 * @since 1.0.0
 */
public interface SpecialCard {

    /**
     * <p>Special ability that just
     * some card can posses. When the
     * card is using the ability the
     * hell comes to the surface.</p>
     * @param cardX row index to attack the card.
     * @param cardY column index to attack the card.
     * @return "Ok" string if everything when successfully
     * or an error message to catch later and process it.
     */
    String unleashTheHell(int cardX, int cardY);
}
