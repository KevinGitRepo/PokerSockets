package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public class StraightFlush implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_STRAIGHTFLUSH_AMOUNT = 381;

    private final List<Card> straightFlush;
    private int totalStraightFlushAmount;

    public StraightFlush( List<Card> straightFlush ) {
        this.straightFlush = straightFlush;
        for ( Card card : straightFlush ) {
            this.totalStraightFlushAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.straightFlush;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.STRAIGHT_FLUSH;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public int amountWorth() {
        return STANDARD_STRAIGHTFLUSH_AMOUNT + this.totalStraightFlushAmount;
    }

    /**
     * Prints the straight flush in a readable format
     * @return the list of cards that created the straight flush
     */
    @Override
    public String toString() {
        return "Straight Flush: " + String.join( ", ", straightFlush.toString() );
    }

}
