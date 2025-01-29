package org.example.Hands;

import org.example.General.Card;
import org.example.General.PokerHandTypes;

import java.util.List;

public class TwoPair implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_TWOPAIR_AMOUNT = 42;

    private final List<Card> twoPair;
    private int totalTwoPairAmount;

    public TwoPair( List<Card> twoPair ) {
        this.twoPair = twoPair;
        for ( Card card : twoPair ) {
            this.totalTwoPairAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.twoPair;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.TWO_PAIR;
    }

    /**
     * { @inheritDoc }
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
        return "Two Pair: " + String.join( ", ", twoPair.toString() );
    }

}
