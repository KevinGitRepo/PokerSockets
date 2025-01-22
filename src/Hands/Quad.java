package Hands;

import main.Card;

import java.util.List;

public class Quad implements PokerHand{

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_QUAD_AMOUNT = 325;

    private List<Card> quad;
    private int totalQuadAmount;

    public Quad(List<Card> quad) {
        this.quad = quad;
        for (Card card : quad) {
            this.totalQuadAmount += card.getCardValue();
        }
    }

    @Override
    public String getHandName() {
        return "Quad";
    }

    /**
     * Adds cards in quad together and adds predetermined hand amount to the total.
     * @return total amount the quad is worth
     */
    @Override
    public int amountWorth() {
        return STANDARD_QUAD_AMOUNT + this.totalQuadAmount;
    }

    /**
     * Prints the quad in a readable format
     * @return the list of cards that created the quad
     */
    @Override
    public String toString() {
        return "Quad: " + String.join(", ", quad.toString());
    }

}
