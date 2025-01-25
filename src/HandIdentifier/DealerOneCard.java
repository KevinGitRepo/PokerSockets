package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealerOneCard implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;
    private Player player;
    private List<Card> dealersHand;
    private HandIdentifierDistribute handIdentifierDistribute;

    public DealerOneCard(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
        this.connectorManager = connectorManager;
        this.handIdentifierDistribute = handIdentifierDistribute;
        this.mergedList = new ArrayList<>();
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // Have to check larger hands first
        // Check single pair
        // Check triple pair iff single pair was confirmed

        if(player.getPokerHand() == null){
            return this.handIdentifierDistribute.checkHand(PokerHandTypes.ONE_PAIR, player, dealersHand);
        }

        return this.handIdentifierDistribute.checkHand(PokerHandTypes.TRIPLE, player, dealersHand);
    }

    private boolean checkSinglePair() {
        int index = this.player.getHand().indexOf(this.dealersHand.getFirst());
        if(index != -1){
            this.mergedList.add(this.player.getHand().get(index));
            this.mergedList.add(this.dealersHand.getFirst());
            this.player.setPokerHand(this.connectorManager.sendForHand(PokerHandTypes.ONE_PAIR, this.mergedList));

            // Removes One Pair from potential hand list so it won't be checked in the future
            this.player.removePossibleHand(PokerHandTypes.ONE_PAIR);
            return true;
        }
        return false;
    }

    private boolean checkTriple() {
        if(this.player.getHand().getFirst().equals(dealersHand.getFirst())){
            mergeLists(this.dealersHand, this.player.getHand());
            this.player.setPokerHand(this.connectorManager.sendForHand(PokerHandTypes.TRIPLE, this.mergedList));

            // Removes potential hands
            this.player.removePossibleHands(Arrays.asList(PokerHandTypes.ONE_PAIR, PokerHandTypes.TWO_PAIR));
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
