package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckTriple implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckTriple(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {

        // Gets the card in a pair to check for triple
        Card pairCard = player.getPokerHand().getHandCards().getFirst();

        if (!pairCard.equals(dealersHand.getLast())) {
            return false;
        }

        List<Card> mergedList = new ArrayList<>(player.getHand());
        mergedList.add(dealersHand.getLast());

        player.removePossibleHands(Arrays.asList(PokerHandTypes.TRIPLE, PokerHandTypes.TWO_PAIR));

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.TRIPLE, mergedList));
    }
}
