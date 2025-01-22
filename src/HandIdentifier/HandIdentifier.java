package HandIdentifier;

import main.Card;
import main.Player;

import java.util.List;

public interface HandIdentifier {

    public boolean checkHand(Player player, List<Card> dealersHand);
}
