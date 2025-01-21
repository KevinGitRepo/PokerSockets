package Hands;

import main.Card;

import java.util.List;

public class TwoPair implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_TWOPAIR_AMOUNT = 42;

    private List<Card> twoPair;
    private int totalTwoPairAmount;

    public TwoPair(List<Card> twoPair) {
        this.twoPair = twoPair;
        for (Card card : twoPair) {
            this.totalTwoPairAmount += card.getCardValue();
        }
    }

    /**
     * Adds cards in two pair together and adds predetermined hand amount to the total.
     * @return total amount the two pair is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_TWOPAIR_AMOUNT + this.totalTwoPairAmount;
    }

    /**
     * Prints the two pair in a readable format
     * @return the list of cards that created the two pair
     */
    @Override
    public String toString() {
        return "Two Pair: " + String.join(", ", twoPair.toString());
    }

}
