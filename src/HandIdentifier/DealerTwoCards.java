package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerTwoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;

    public DealerTwoCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    @Override
    public void checkHand(Player player, List<Card> dealersHand) {
        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
         */
    }
}
