package HandConnector;

import Hands.Flush;
import Hands.PokerHand;
import main.Card;

import java.util.List;

public class FlushConnector implements HandConnector {
    /**
     * Creates the Flush
     * @param handList list of cards to create a Flush
     * @return the Flush object
     */
    @Override
    public PokerHand sendForHand( List<Card> handList ) {
        return new Flush( handList );
    }
}
