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
    public void checkHand(Player player, List<Card> dealersHand) {
        // Have to check larger hands first
        // Check single pair
        // Check triple pair iff single pair was confirmed

        if(player.getPokerHand() != null && player.getPokerHand().getHandName().equals("One Pair")){
            checkTriple(player, dealersHand);
        }

        checkSinglePair(player, dealersHand);

        clearList();
    }

    private void checkSinglePair(Player player, List<Card> dealersHand) {
        for(Card card : player.getHand()){
            if(card.equals(dealersHand.getFirst())){
                this.mergedList.add(card);
                this.mergedList.add(dealersHand.getFirst());
                player.setPokerHand(this.connectorManager.sendForHand("One Pair", this.mergedList));
                break;
            }
        }
    }

    private void checkTriple(Player player, List<Card> dealersHand) {
        if(player.getHand().getFirst().equals(dealersHand.getFirst())){
            this.mergedList.addAll(dealersHand);
            this.mergedList.addAll(player.getHand());
            player.setPokerHand(this.connectorManager.sendForHand("Triple", this.mergedList));
        }
    }

    private void clearList(){
        this.mergedList.clear();
    }
}
