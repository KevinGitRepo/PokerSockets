package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerOneCard implements HandIdentifier {

    private final HandConnectorManager connectorManager;

    public DealerOneCard(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    @Override
    public void checkHand(Player player, List<Card> dealersHand) {
        /*
        Hands to check:
        OnePair
        Triple
         */
    }
}
