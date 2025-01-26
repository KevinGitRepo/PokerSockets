package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.Triple;

import java.util.List;

public class TripleConnector implements HandConnector {

    /**
     * Creates a Triple object
     * @param handList list of cards to create a Triple
     * @return the Triple object
     */
    @Override
    public PokerHand sendForHand( List<Card> handList ) {
        return new Triple( handList );
    }
}
