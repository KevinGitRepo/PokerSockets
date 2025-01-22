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
    private final HandConnectorManager connectorManager = new HandConnectorManager();

    public HandIdentifierManager() {
        populateHandsMap();
    }

    private void populateHandsMap(){
        this.hands.put(0, new DealerNoCards(this.connectorManager));
        this.hands.put(1, new DealerOneCard(this.connectorManager));
        this.hands.put(2, new DealerTwoCards(this.connectorManager));
        this.hands.put(3, new DealerThreeCards(this.connectorManager));
        this.hands.put(4, new DealerFourCards(this.connectorManager));
        this.hands.put(5, new DealerFiveCards(this.connectorManager));
    }

    public void checkHand(Player player, List<Card> dealerCards) {
        this.hands.get(dealerCards.size()).checkHand(player, dealerCards);
    }
}
