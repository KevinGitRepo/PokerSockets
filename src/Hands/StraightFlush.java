package Hands;

import main.Card;

import java.util.List;

public class StraightFlush implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_STRAIGHTFLUSH_AMOUNT = 381;

    private List<Card> straightFlush;
    private int totalStraightFlushAmount;

    public StraightFlush(List<Card> straightFlush) {
        this.straightFlush = straightFlush;
        for (Card card : straightFlush) {
            this.totalStraightFlushAmount += card.getCardValue();
        }
    }

    @Override
    public String getHandName() {
        return "Straight Flush";
    }

    /**
     * Adds cards in straight flush together and adds predetermined hand amount to the total.
     * @return total amount the straight flush is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_STRAIGHTFLUSH_AMOUNT + this.totalStraightFlushAmount;
    }

    /**
     * Prints the straight flush in a readable format
     * @return the list of cards that created the straight flush
     */
    @Override
    public String toString() {
        return "Straight Flush: " + String.join(", ", straightFlush.toString());
    }

}
