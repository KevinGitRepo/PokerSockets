package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.List;

public class DealerNoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private Player player;

    public DealerNoCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        /*
        Use player as a global variable to decrease passing object through multiple methods
        Mostly will be useful in other types of HandIdentifier's with more than one helper method
        DealersHand isn't used here since it would have no cards and be null
         */

        // Add all possible hands to start out
        this.player = player;
        player.addPossibleHands(this.connectorManager.getHandTypes());
        return checkSinglePair();
    }

    private boolean checkSinglePair() {
        List<Card> playerHand = this.player.getHand();

        if(playerHand.getFirst() != null && playerHand.getLast() != null){
            return false;
        }

        // Checks for a single pair in the player's current hand
        // Only needs to send the whole player hand to add to the PokerHand object
        // Normally will need to send only the card list that produced the hand itself
        if(playerHand.get(0).equals(playerHand.get(1))){
            this.player.setPokerHand(this.connectorManager.sendForHand("One Pair", playerHand));

            // Removes One Pair from a potential hand since another pair would be considered two pair
            this.player.removePossibleHand("One Pair");
            return true;
        }

        return false;
    }
}
