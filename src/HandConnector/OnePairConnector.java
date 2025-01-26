package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.OnePair;

import java.util.List;

public class OnePairConnector implements HandConnector {

    /**
     * Creates a OnePair object
     * @param handList list of cards to create a single pair
     * @return the OnePair object
     */
    @Override
    public PokerHand sendForHand( List<Card> handList ) {
        return new OnePair( handList );
    }
}
