package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.OnePair;
import org.example.Hands.PokerHand;

import java.util.List;

public class OnePairConnector implements HandConnector {

    /**
     * Creates a OnePair object
     * @param handList list of cards to create a single pair
     * @return the OnePair object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new OnePair( handList );
    }
}
