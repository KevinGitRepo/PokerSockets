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
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // eliminate hands from potential hands
        /*
        Royal Flush:
            If not enough royal cards (10, Jack, Queen, King, Ace) all same suit
        Can eliminate all hands besides Royal Flush and Straight Flush:
            If player has a quad
        Eliminate hands below current hand
        Possible Hands that could have been found:
            Quad, Triple, Two Pair, One Pair
         */

        boolean returnValue = false;
        boolean playerPokerHandBool = player.getPokerHand() != null;

        //Eliminate hands before starting
        //Then eliminate hands after looping

        if(playerPokerHandBool && player.getPokerHand().getHandName().equals("Quad")) {
            // Check for only Royal Flush and Straight Flush
            // Will eliminate those potions if not possible
            returnValue = checkRoyalAndStraightFlush(player, dealersHand);
        }

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

    private boolean checkRoyalAndStraightFlush(Player player, List<Card> dealersHand) {
        return false;
    }

    private void eliminateHands(Player player, List<Card> dealersHand) {
        //Eliminate Royal Flush

    }
}
