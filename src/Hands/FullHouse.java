package Hands;

import main.Card;

import java.util.List;

public class FullHouse implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_FULLHOUSE_AMOUNT = 257;

    private List<Card> fullHouse;
    private int totalFullHouseAmount;

    public FullHouse(List<Card> fullHouse) {
        this.fullHouse = fullHouse;
        for (Card card : fullHouse) {
            this.totalFullHouseAmount += card.getCardValue();
        }
    }

    /**
     * Adds cards in full house together and adds predetermined hand amount to the total
     * @return total amount the full house is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_FULLHOUSE_AMOUNT + this.totalFullHouseAmount;
    }

    /**
     * Prints the full house in a readable format
     * @return the list of cards that created the full house
     */
    @Override
    public String toString() {
        return "Full House: " + String.join(", ", fullHouse.toString());
    }

}
