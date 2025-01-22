package HandConnector;

import Hands.PokerHand;
import main.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandConnectorManager {
    private final HashMap<String, HandConnector> handConnectorHashMap;

    public HandConnectorManager() {
        this.handConnectorHashMap = new HashMap<>();
        addMapValues();
    }

    private void addMapValues() {
        this.handConnectorHashMap.put("One Pair", new OnePairConnector());
        this.handConnectorHashMap.put("Two Pair", new TwoPairConnector());
        this.handConnectorHashMap.put("Flush", new FlushConnector());
        this.handConnectorHashMap.put("Full House", new FullHouseConnector());
        this.handConnectorHashMap.put("Quad", new QuadConnector());
        this.handConnectorHashMap.put("Royal Flush", new RoyalFlushConnector());
        this.handConnectorHashMap.put("Straight Flush", new StraightFlushConnector());
        this.handConnectorHashMap.put("Straight", new StraightConnector());
        this.handConnectorHashMap.put("Triple", new TripleConnector());
    }

    public PokerHand sendForHand(String handIdentifier, List<Card> handList){
        return this.handConnectorHashMap.get(handIdentifier).sendForHand(handList);
    }

    public List<String> getHandTypes() {
        return new ArrayList<>(this.handConnectorHashMap.keySet());
    }
}
