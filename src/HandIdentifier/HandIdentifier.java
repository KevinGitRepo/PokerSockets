package HandIdentifier;

import main.Card;
import main.Player;

import java.util.List;

public interface HandIdentifier {

    /**
     * Checks the types of hands that are possible with the dealers hand and the player's hand
     * @param player current player's cards to check
     * @param dealersHand current dealer's cards to check
     * @return true if the player's hand created a poker hand type using the dealer's cards; false otherwise
     */
    boolean checkHand(Player player, List<Card> dealersHand);
}
