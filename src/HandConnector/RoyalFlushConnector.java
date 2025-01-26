package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.RoyalFlush;

import java.util.List;

public class RoyalFlushConnector implements HandConnector {

    /**
     * Creates a RoyalFlush object
     * @param handList list of cards to create a Royal Flush
     * @return the RoyalFlush object
     */
    @Override
    public PokerHand sendForHand( List<Card> handList ) {
        return new RoyalFlush( handList );
    }
}
