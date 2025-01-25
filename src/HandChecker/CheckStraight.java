package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.List;

public class CheckStraight extends HandCheckParent implements CheckHand{

    private final HandConnectorManager handConnectorManager;

    public CheckStraight(HandConnectorManager handConnectorManager) {
        this.handConnectorManager = handConnectorManager;
    }

    @Override
    public boolean check(Player player, List<Card> dealersHand) {

        List<Card> mergedList = super.mergeLists(player.getHand(), dealersHand);

        List<Card> returnedList = super.checkStraightHelper(player, mergedList);

        if (returnedList == null){
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.STRAIGHT, returnedList));
    }
}
