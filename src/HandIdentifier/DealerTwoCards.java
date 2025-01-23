package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class DealerTwoCards extends HandIdentifierAbstract implements HandIdentifier {

//    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;
    private Player player;
    private List<Card> dealersHand;

    public DealerTwoCards(HandConnectorManager connectorManager) {
        super.setConnectorManager(connectorManager);
        this.mergedList = new ArrayList<>();
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        /*
        Check Triple first for quad
        Check Single Pair next for triple and two pair

        Removes as many possible hands as possible for later hands
        Try to decrease amount of calculating for the final two cards
        */

        this.player = player;
        this.dealersHand = dealersHand;

        super.setPlayer(player);
        super.setDealersHand(dealersHand);

        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
         */

        boolean returnValue = false;
        boolean playerPokerHandBool = this.player.getPokerHand() != null;

        if(!playerPokerHandBool) {
            // No poker hand then check for a single pair
            return super.checkSinglePair();
        }

        if(this.player.getPokerHand().getHandName().equals("Triple")){
            // If the poker hand is a triple then only possible hand would be quad
            returnValue = super.checkQuad();
        }
        else if(this.player.getPokerHand().getHandName().equals("One Pair")){
            // If the poker hand is a single pair then only possible beatable hand would be two pair or triple
            returnValue = super.checkTripleAndTwoPair();
        }

        super.clearList();
        return returnValue;
    }
}
