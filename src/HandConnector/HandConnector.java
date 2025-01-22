package HandConnector;

import Hands.PokerHand;
import main.Card;

import java.util.List;

public interface HandConnector {

    public PokerHand sendForHand(List<Card> handList);
}
