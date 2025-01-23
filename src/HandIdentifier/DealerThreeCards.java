package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerThreeCards extends HandIdentifierAbstract implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private Player player;
    private List<Card> dealersHand;

    public DealerThreeCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        /*
        Potential hands would have been eliminated from the players' list by this point
        Dealer with Three card would only need to check for specific cases
        Only will eliminate flushes if not possible (Royal Flush, Straight Flush, Flush)
        Will always recheck for Quad, Triple, Two Pair, and One Pair
        Possible Hands that could have been found so far:
            Quad, Triple, Two Pair, One Pair
         */

        this.player = player;
        this.dealersHand = dealersHand;

        boolean returnValue = false;
        boolean playerPokerHandBool = player.getPokerHand() != null;

        // Method is specific for this class
        checkForFlush();

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

        return returnValue;
    }

    private boolean checkForFlush(){
        return false;
    }
}
