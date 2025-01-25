package HandChecker;

import main.Card;
import main.Player;

import java.util.List;

public interface CheckHand {

    public boolean check (Player player, List<Card> dealersHand);
}
