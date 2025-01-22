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
        checkSinglePair(player, dealersHand);
    }

    private void checkSinglePair(Player player, List<Card> dealersHand) {
        List<Card> playerHand = player.getHand();

        // Checks for a single pair in the player's current hand
        // Only needs to send the whole player hand to add to the PokerHand object
        // Normally will need to send only the card list that produced the hand itself
        if((playerHand.getFirst() != null && playerHand.getLast() != null) && playerHand.get(0).equals(playerHand.get(1))){
            player.setPokerHand(this.connectorManager.sendForHand("One Pair", playerHand));
        }
    }
}
