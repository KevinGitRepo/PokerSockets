package Hands;

import main.Card;

import java.util.List;

public class Straight implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_STRAIGHT_AMOUNT = 138;

    private List<Card> straight;
    private int totalStraightAmount;

    public Straight(List<Card> straight) {
        this.straight = straight;
        for (Card card : straight) {
            this.totalStraightAmount += card.getCardValue();
        }
    }

    @Override
    public List<Card> getHandCards() {
        return this.straight;
    }

    @Override
    public String getHandName() {
        return "Straight";
    }

    /**
     * Adds cards in straight together and adds predetermined hand amount to the total.
     * @return total amount the straight is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_STRAIGHT_AMOUNT + this.totalStraightAmount;
    }

    /**
     * Prints the straight in a readable format
     * @return the list of cards that created the straight
     */
    @Override
    public String toString() {
        return "Straight: " + String.join(", ", straight.toString());
    }

}
