package Hands;

import main.Card;
import java.util.List;

public class Flush implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_FLUSH_AMOUNT = 198;

    private List<Card> flush;
    private int totalFlushAmount;

    public Flush(List<Card> flush) {
        this.flush = flush;
        for (Card card : flush) {
            this.totalFlushAmount += card.getCardValue();
        }
    }

    /**
     * Adds cards in flush together and adds predetermined hand amount to the total.
     * @return total amount the flush is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_FLUSH_AMOUNT + this.totalFlushAmount;
    }

    /**
     * Prints the flush in a readable format
     * @return the list of cards that created the flush
     */
    @Override
    public String toString() {
        return "Flush: " + String.join(", ", flush.toString());
    }
    
}
