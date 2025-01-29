package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.StraightFlush;

import java.util.List;

public class StraightFlushConnector implements HandConnector {

    /**
     * Creates a StraightFlush object
     * @param handList list of cards to create a Straight Flush
     * @return the StraightFlush object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new StraightFlush( handList );
    }
}
