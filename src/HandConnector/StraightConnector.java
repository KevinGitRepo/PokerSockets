package HandConnector;

import main.Card;
import Hands.PokerHand;
import Hands.Straight;

import java.util.List;

public class StraightConnector implements HandConnector {

    /**
     * Creates a Straight object
     * @param handList list of cards to create a Straight
     * @return the Straight object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList) {
        return new Straight(handList);
    }
}
