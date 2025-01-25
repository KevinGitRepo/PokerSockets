package HandIdentifier;

import HandConnector.HandConnectorManager;
import HandChecker.*;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandIdentifierDistribute {

    private final Map<PokerHandTypes, CheckHand> checkHandMap;
    private final HandConnectorManager handConnectorManager;

    public HandIdentifierDistribute(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
        this.checkHandMap = new HashMap<>();
        addMapValues();
    }

    private void addMapValues() {
        this.checkHandMap.put(PokerHandTypes.ONE_PAIR, new CheckOnePair(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.TWO_PAIR, new CheckTwoPair(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.TRIPLE, new CheckTriple(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.STRAIGHT, new CheckStraight(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.FLUSH, new CheckFlush(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.FULL_HOUSE, new CheckFullHouse(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.QUAD, new CheckQuad(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.STRAIGHT_FLUSH, new CheckStraightFlush(this.handConnectorManager));
        this.checkHandMap.put(PokerHandTypes.ROYAL_FLUSH, new CheckRoyalFlush(this.handConnectorManager));
    }

    public boolean checkHand(PokerHandTypes handType, Player player, List<Card> dealersHand) {
        return this.checkHandMap.get(handType).check(player, dealersHand);
    }
}
