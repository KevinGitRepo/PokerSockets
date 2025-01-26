package HandConnector;

import Hands.PokerHand;
import main.Card;

import java.util.List;

public interface HandConnector {

    PokerHand sendForHand( List<Card> handList );
}
