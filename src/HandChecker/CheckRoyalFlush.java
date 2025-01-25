package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckRoyalFlush extends HandCheckParent implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckRoyalFlush(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {

        // Has to be flush in order to check for royal flush
        List<Card> flushCards = player.getPokerHand().getHandCards();

        Collections.sort(flushCards);

        if (flushCards.getFirst().getCardValue() != 10 || flushCards.getLast().getCardValue() != 14){
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.ROYAL_FLUSH, flushCards));
    }
}
