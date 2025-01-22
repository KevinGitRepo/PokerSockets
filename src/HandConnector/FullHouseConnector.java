package HandConnector;

import Hands.FullHouse;
import Hands.PokerHand;
import main.Card;

import java.util.List;

public class FullHouseConnector implements HandConnector {

    /**
     * Creates a FullHouse object
     * @param handList list of cards to create a Full House
     * @return the FullHouse object
     */
    @Override
    public PokerHand sendForHand(List<Card> handList) {
        return new FullHouse(handList);
    }
}
