package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public class Triple implements PokerHand {

    // Standard value is the best possible amount for lower hand
    private final int STANDARD_TRIPLE_AMOUNT = 96;

    private final List<Card> triple;
    private int totalTripleAmount;

    public Triple( List<Card> triple ) {
        this.triple = triple;
        for ( Card card : triple ) {
            this.totalTripleAmount += card.getCardValue();
        }
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public List<Card> getHandCards() {
        return this.triple;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public PokerHandTypes getHandName() {
        return PokerHandTypes.TRIPLE;
    }

    /**
     * { @inheritDoc }
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
        return "Triple: " + String.join( ", ", triple.toString() );
    }

}
