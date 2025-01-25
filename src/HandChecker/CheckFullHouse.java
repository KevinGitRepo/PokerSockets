package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckFullHouse extends HandCheckParent implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckFullHouse(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {

        List<Card> mergedList = super.mergeLists(player.getHand(), dealersHand);

        Map<Integer, List<Card>> fullHouseMap = new HashMap<>();

        for (Card card : mergedList) {
            fullHouseMap.computeIfAbsent(card.getCardValue(), k -> new ArrayList<>());
            fullHouseMap.get(card.getCardValue()).add(card);
        }

        // Check if at least 1 list has 3 and one has 2
        int keyForListOfThree = -1;
        int keyForListOfTwo = -1;

        for (Integer integerKey : fullHouseMap.keySet()) {
            if (fullHouseMap.get(integerKey).size() == 3) {
                if (keyForListOfThree > integerKey) {
                    keyForListOfTwo = Integer.max(keyForListOfTwo, integerKey);
                }
                else {
                    keyForListOfTwo = Integer.max(keyForListOfThree, keyForListOfTwo);
                    keyForListOfThree = integerKey;
                }
            }
            else if (fullHouseMap.get(integerKey).size() == 2) {
                keyForListOfTwo = Integer.max(keyForListOfTwo, integerKey);
            }
        }

        if (fullHouseMap.get(keyForListOfTwo).size() > 2) {
            fullHouseMap.get(keyForListOfTwo).removeLast();
        }

        mergedList = super.mergeLists(fullHouseMap.get(keyForListOfThree), fullHouseMap.get(keyForListOfTwo));

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.FULL_HOUSE, mergedList));
    }
}
