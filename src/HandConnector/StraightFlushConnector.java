package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.StraightFlush;

import java.util.List;

public class StraightFlushConnector implements HandConnector {

    /**
     * Creates a StraightFlush object
     * @param handList list of cards to create a Straight Flush
     * @return the StraightFlush object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList) {
        return new StraightFlush(handList);
    }
}
