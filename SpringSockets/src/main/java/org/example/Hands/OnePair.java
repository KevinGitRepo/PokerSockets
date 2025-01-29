package org.example.Hands;


import org.example.General.Card;
import org.example.General.PokerHandTypes;

import java.util.List;

public class OnePair implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_ONEPAIR_AMOUNT = 14;

    private final List<Card> onePair;
    private int totalOnePairAmount;

    public OnePair( List<Card> onePair ) {
        this.onePair = onePair;
        for ( Card card : onePair ) {
            this.totalOnePairAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.onePair;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.ONE_PAIR;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public int amountWorth() {
        return STANDARD_ONEPAIR_AMOUNT + this.totalOnePairAmount;
    }

    /**
     * Prints the pair in a readable format
     * @return the list of cards that created the pair
     */
    @Override
    public String toString() {
        return "Pair: " + String.join( ", ", onePair.toString() );
    }

}
