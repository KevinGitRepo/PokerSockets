package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.Arrays;
import java.util.List;

public class CheckOnePair implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckOnePair(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {
        // Only check the last card in dealersHand for a single pair instead of every card
        Card dealerLastCard = dealersHand.getLast();
        Card pairedCard;

        if (player.getHand().getFirst().equals(dealerLastCard)) {
            pairedCard = player.getHand().getFirst();
        }
        else if (player.getHand().getLast().equals(dealerLastCard)) {
            pairedCard = player.getHand().getLast();
        }
        else {
            return false;
        }

        player.removePossibleHand(PokerHandTypes.ONE_PAIR);

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.ONE_PAIR, Arrays.asList(dealerLastCard, pairedCard)));
    }
}
