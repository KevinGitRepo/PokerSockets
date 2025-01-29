package org.example.Hands;

import org.example.General.Card;
import org.example.General.PokerHandTypes;

import java.util.List;

public class Flush implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_FLUSH_AMOUNT = 198;

    private final List<Card> flush;
    private int totalFlushAmount;

    public Flush( List<Card> flush ) {
        this.flush = flush;
        for ( Card card : flush ) {
            this.totalFlushAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.flush;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.FLUSH;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public int amountWorth() {
        return STANDARD_FLUSH_AMOUNT + this.totalFlushAmount;
    }

    /**
     * Prints the flush in a readable format
     * @return the list of cards that created the flush
     */
    @Override
    public String toString() {
        return "Flush: " + String.join( ", ", flush.toString() );
    }
    
}
