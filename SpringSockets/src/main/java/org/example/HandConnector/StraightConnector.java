package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;
import org.example.Hands.Straight;

import java.util.List;

public class StraightConnector implements HandConnector {

    /**
     * Creates a Straight object
     * @param handList list of cards to create a Straight
     * @return the Straight object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new Straight( handList );
    }
}
