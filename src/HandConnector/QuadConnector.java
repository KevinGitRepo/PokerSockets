package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.Quad;

import java.util.List;

public class QuadConnector implements HandConnector {

    /**
     * Creates a Quad object
     * @param handList list of cards to create a Quad
     * @return the Quad object
     */
    @Override
    public PokerHand sendForHand( List<Card> handList ) {
        return new Quad( handList );
    }
}
