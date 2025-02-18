package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.FullHouse;
import org.example.Hands.PokerHand;

import java.util.List;

public class FullHouseConnector implements HandConnector {

    /**
     * Creates a FullHouse object
     * @param handList list of cards to create a Full House
     * @return the FullHouse object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList ) {
        return new FullHouse( handList );
    }
}
