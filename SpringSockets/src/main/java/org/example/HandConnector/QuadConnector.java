package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.Quad;

import java.util.List;

public class QuadConnector implements HandConnector {

    /**
     * Creates a Quad object
     * @param handList list of cards to create a Quad
     * @return the Quad object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new Quad( handList );
    }
}
