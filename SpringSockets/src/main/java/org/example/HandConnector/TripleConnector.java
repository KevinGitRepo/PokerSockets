package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.Triple;

import java.util.List;

public class TripleConnector implements HandConnector {

    /**
     * Creates a Triple object
     * @param handList list of cards to create a Triple
     * @return the Triple object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new Triple( handList );
    }
}
