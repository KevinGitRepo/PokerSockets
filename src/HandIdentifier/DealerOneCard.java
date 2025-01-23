package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealerOneCard implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;
    private Player player;
    private List<Card> dealersHand;

    public DealerOneCard(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
        this.mergedList = new ArrayList<>();
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // Have to check larger hands first
        // Check single pair
        // Check triple pair iff single pair was confirmed
        this.player = player;
        this.dealersHand = dealersHand;

        boolean returnValue = false;

        if(player.getPokerHand() == null){
            return returnValue;
        }

        if(player.getPokerHand().getHandName().equals("One Pair")){
            returnValue = checkTriple();
        }else{
            returnValue = checkSinglePair();
        }

        clearList();
        return returnValue;
    }

    private boolean checkSinglePair() {
        int index = this.player.getHand().indexOf(this.dealersHand.getFirst());
        if(index != -1){
            this.mergedList.add(this.player.getHand().get(index));
            this.mergedList.add(this.dealersHand.getFirst());
            this.player.setPokerHand(this.connectorManager.sendForHand("One Pair", this.mergedList));

            // Removes One Pair from potential hand list so it won't be checked in the future
            this.player.removePossibleHand("One Pair");
            return true;
        }
        return false;
    }

    private boolean checkTriple() {
        if(this.player.getHand().getFirst().equals(dealersHand.getFirst())){
            mergeLists(this.dealersHand, this.player.getHand());
            this.player.setPokerHand(this.connectorManager.sendForHand("Triple", this.mergedList));

            // Removes potential hands
            this.player.removePossibleHands(Arrays.asList("One Pair", "Two Pair"));
            return true;
        }
        return false;
    }

    private void clearList(){
        this.mergedList.clear();
    }

    private void mergeLists(List<Card> list1, List<Card> list2){
        this.mergedList.addAll(list1);
        this.mergedList.addAll(list2);
    }
}
