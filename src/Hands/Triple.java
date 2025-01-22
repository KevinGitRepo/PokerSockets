package Hands;

import main.Card;

import java.util.List;

public class Triple implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_TRIPLE_AMOUNT = 96;

    private List<Card> triple;
    private int totalTripleAmount;

    public Triple(List<Card> triple) {
        this.triple = triple;
        for (Card card : triple) {
            this.totalTripleAmount += card.getCardValue();
        }
    }

    @Override
    public List<Card> getHandCards() {
        return this.triple;
    }

    @Override
    public String getHandName() {
        return "Triple";
    }

    /**
     * Adds cards in triple together and adds predetermined hand amount to the total.
     * @return total amount the triple is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_TRIPLE_AMOUNT + this.totalTripleAmount;
    }

    /**
     * Prints the triple in a readable format
     * @return the list of cards that created the triple
     */
    @Override
    public String toString() {
        return "Triple: " + String.join(", ", triple.toString());
    }

}
