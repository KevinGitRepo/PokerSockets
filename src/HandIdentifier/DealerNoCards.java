package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerNoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;

    public DealerNoCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    @Override
    public void checkHand(Player player, List<Card> dealersHand) {
        // Add all possible hands to start out
        player.addPossibleHands(this.connectorManager.getHandTypes());

        List<Card> playerHand = player.getHand();

        if(playerHand.get(0).equals(playerHand.get(1))){

        }
        /*
        Hands to check:
        OnePair
         */
    }
}
