package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public class RoyalFlush implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_ROYALFLUSH_AMOUNT = 436;

    private final List<Card> royalFlush;
    private int totalRoyalFlushAmount;

    public RoyalFlush( List<Card> royalFlush ) {
        this.royalFlush = royalFlush;
        for ( Card card : royalFlush ) {
            this.totalRoyalFlushAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.royalFlush;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.ROYAL_FLUSH;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public int amountWorth() {
        return STANDARD_ROYALFLUSH_AMOUNT + this.totalRoyalFlushAmount;
    }

    /**
     * Prints the royal flush in a readable format
     * @return the list of cards that created the royal flush
     */
    @Override
    public String toString() {
        return "Royal Flush: " + String.join( ", ", royalFlush.toString() );
    }

}
