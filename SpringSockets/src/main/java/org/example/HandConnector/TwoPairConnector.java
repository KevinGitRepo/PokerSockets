package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.TwoPair;

import java.util.List;

public class TwoPairConnector implements HandConnector {

    /**
     * Creates a TwoPair object
     * @param handList list of cards to create two pairs
     * @return the TwoPair object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new TwoPair( handList );
    }
}
