package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class DealerTwoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;

    public DealerTwoCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
        this.mergedList = new ArrayList<>();
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

    private void checkSinglePair(Player player, List<Card> dealersHand) {
        for(Card card : player.getHand()){
            int index = dealersHand.indexOf(card);
            if(index != -1){
                this.mergedList.add(dealersHand.get(index));
                this.mergedList.add(card);
                player.setPokerHand(this.connectorManager.sendForHand("One Pair", this.mergedList));
                break;
            }
        }
    }

    private void checkTriple(Player player, List<Card> dealersHand) {

    }

    private void checkQuad(Player player, List<Card> dealersHand) {

    }

    private void checkTwoPair(Player player, List<Card> dealersHand) {

    }

    private void clearList(){
        this.mergedList.clear();
    }
}
