package HandConnector;

import Hands.PokerHand;
import main.Card;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandConnectorManager {
    private final HashMap<PokerHandTypes, HandConnector> handConnectorHashMap;

    public HandConnectorManager() {
        this.handConnectorHashMap = new HashMap<>();
        addMapValues();
    }

    private void addMapValues() {
        this.handConnectorHashMap.put(PokerHandTypes.ONE_PAIR, new OnePairConnector());
        this.handConnectorHashMap.put(PokerHandTypes.TWO_PAIR, new TwoPairConnector());
        this.handConnectorHashMap.put(PokerHandTypes.FLUSH, new FlushConnector());
        this.handConnectorHashMap.put(PokerHandTypes.FULL_HOUSE, new FullHouseConnector());
        this.handConnectorHashMap.put(PokerHandTypes.QUAD, new QuadConnector());
        this.handConnectorHashMap.put(PokerHandTypes.ROYAL_FLUSH, new RoyalFlushConnector());
        this.handConnectorHashMap.put(PokerHandTypes.STRAIGHT_FLUSH, new StraightFlushConnector());
        this.handConnectorHashMap.put(PokerHandTypes.STRAIGHT, new StraightConnector());
        this.handConnectorHashMap.put(PokerHandTypes.TRIPLE, new TripleConnector());
    }

    public PokerHand sendForHand(PokerHandTypes handIdentifier, List<Card> handList){
        return this.handConnectorHashMap.get(handIdentifier).sendForHand(handList);
    }
}
