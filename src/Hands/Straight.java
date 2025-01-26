package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public class Straight implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_STRAIGHT_AMOUNT = 138;

    private final List<Card> straight;
    private int totalStraightAmount;

    public Straight( List<Card> straight ) {
        this.straight = straight;
        for ( Card card : straight ) {
            this.totalStraightAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.straight;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.STRAIGHT;
    }

    /**
     * { @inheritDoc }
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
        return "Straight: " + String.join( ", ", straight.toString() );
    }

}
