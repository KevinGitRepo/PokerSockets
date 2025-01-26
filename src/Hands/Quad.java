package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public class Quad implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_QUAD_AMOUNT = 325;

    private final List<Card> quad;
    private int totalQuadAmount;

    public Quad( List<Card> quad ) {
        this.quad = quad;
        for ( Card card : quad ) {
            this.totalQuadAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.quad;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.QUAD;
    }

    /**
     * { @inheritDoc }
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
        return "Quad: " + String.join( ", ", quad.toString() );
    }

}
