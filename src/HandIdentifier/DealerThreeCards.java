package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerThreeCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;

    public DealerThreeCards(HandConnectorManager connectorManager) {
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
        Flush
        FullHouse
        RoyalFlush
        Straight
        StraightFlush
         */
    }
}
