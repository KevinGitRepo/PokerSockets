package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckFlush extends HandCheckParent implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckFlush(HandConnectorManager connectorManager) {
        this.handConnectorManager = connectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {

        Map<String, List<Card>> cardsMap = new HashMap<>();
        cardsMap.put("Clubs", new ArrayList<>());
        cardsMap.put("Spades", new ArrayList<>());
        cardsMap.put("Hearts", new ArrayList<>());
        cardsMap.put("Diamonds", new ArrayList<>());

        List<Card> mergedList = super.mergeLists(player.getHand(), dealersHand);
        String keyLongestList = "";
        int countLongestList = 0;

        for (Card card : mergedList) {
            cardsMap.get(card.getCardSuit()).add(card);

            if (card.getCardSuit().equals(keyLongestList)) {
                countLongestList++;
            }
            else {
                countLongestList--;
            }

            if (countLongestList == 0) {
                keyLongestList = card.getCardSuit();
            }
        }

        if (cardsMap.get(keyLongestList).size() < 5) {
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.FLUSH, cardsMap.get(keyLongestList)));
    }
}
