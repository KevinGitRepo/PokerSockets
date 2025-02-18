package org.example.HandConnector;

import org.example.General.Card;
import org.example.General.PokerHandTypes;
import org.example.Hands.PokerHand;

import java.util.HashMap;
import java.util.List;

public class HandConnectorManager {
    private final HashMap<PokerHandTypes, HandConnector> handConnectorHashMap;

    public HandConnectorManager() {
        this.handConnectorHashMap = new HashMap<>();
        addMapValues();
    }

    /**
     * Initializes the map with Poker Hand Types and new connectors for each Poker Hand
     */
    private void addMapValues() {
        this.handConnectorHashMap.put( PokerHandTypes.ONE_PAIR, new OnePairConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.TWO_PAIR, new TwoPairConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.FLUSH, new FlushConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.FULL_HOUSE, new FullHouseConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.QUAD, new QuadConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.ROYAL_FLUSH, new RoyalFlushConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.STRAIGHT_FLUSH, new StraightFlushConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.STRAIGHT, new StraightConnector() );
        this.handConnectorHashMap.put( PokerHandTypes.TRIPLE, new TripleConnector() );
    }

    /**
     * Receives a poker hand type and uses the connector function to handle the request
     * @param handIdentifier the type of poker hand
     * @param handList list of cards
     * @return a new Poker hand object for the list of cards
     */
    public PokerHand sendForHand(PokerHandTypes handIdentifier, List<Card> handList ){
        return this.handConnectorHashMap.get( handIdentifier ).sendForHand( handList );
    }
}
