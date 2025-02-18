package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.RoyalFlush;

import java.util.List;

public class RoyalFlushConnector implements HandConnector {

    /**
     * Creates a RoyalFlush object
     * @param handList list of cards to create a Royal Flush
     * @return the RoyalFlush object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new RoyalFlush( handList );
    }
}
