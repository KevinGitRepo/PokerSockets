package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class DealerOneCard implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;

    public DealerOneCard(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
        this.mergedList = new ArrayList<>();
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // Have to check larger hands first
        // Check single pair
        // Check triple pair iff single pair was confirmed

        boolean returnValue = false;

        if(player.getPokerHand() == null){
            return returnValue;
        }

        if(player.getPokerHand().getHandName().equals("One Pair")){
            returnValue = checkTriple(player, dealersHand);
        }else{
            returnValue = checkSinglePair(player, dealersHand);
        }

        clearList();
        return returnValue;
    }

    private boolean checkSinglePair(Player player, List<Card> dealersHand) {
        int index = player.getHand().indexOf(dealersHand.getFirst());
        if(index != -1){
            this.mergedList.add(player.getHand().get(index));
            this.mergedList.add(dealersHand.getFirst());
            player.setPokerHand(this.connectorManager.sendForHand("One Pair", this.mergedList));
            return true;
        }
        return false;
    }

    private boolean checkTriple(Player player, List<Card> dealersHand) {
        if(player.getHand().getFirst().equals(dealersHand.getFirst())){
            this.mergedList.addAll(dealersHand);
            this.mergedList.addAll(player.getHand());
            player.setPokerHand(this.connectorManager.sendForHand("Triple", this.mergedList));
            return true;
        }
        return false;
    }

    private void clearList(){
        this.mergedList.clear();
    }
}
