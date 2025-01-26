package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the different classes to identify hands
 * This manager class will separate the function call depending on the number of cards the dealer has
 */
public class HandIdentifierManager {

    private final Map<Integer, HandIdentifier> hands = new HashMap<>();
    private final HandConnectorManager connectorManager;
    private final HandIdentifierDistribute handIdentifierDistribute;

    public HandIdentifierManager(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
        this.connectorManager = connectorManager;
        this.handIdentifierDistribute = handIdentifierDistribute;
        populateHandsMap();
    }

    private void populateHandsMap(){
        DealerThreeFourFiveCards dealerThreeFourFiveCards = new DealerThreeFourFiveCards(this.handIdentifierDistribute);
        this.hands.put(0, new DealerNoCards(this.connectorManager));
        this.hands.put(1, new DealerOneCard(this.handIdentifierDistribute));
        this.hands.put(2, new DealerTwoCards(this.handIdentifierDistribute));
        this.hands.put(3, dealerThreeFourFiveCards);
        this.hands.put(4, dealerThreeFourFiveCards);
        this.hands.put(5, dealerThreeFourFiveCards);
    }

    public void checkHand(Player player, List<Card> dealerCards) {
        this.hands.get(dealerCards.size()).checkHand(player, dealerCards);
    }
}
