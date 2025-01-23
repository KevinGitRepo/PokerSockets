package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class HandIdentifierAbstract {
    private Player player;
    private List<Card> dealersHand;
    private List<Card> mergedList = new ArrayList<>();
    private HandConnectorManager connectorManager;

    public void setConnectorManager(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDealersHand(List<Card> dealersHand) {
        this.dealersHand = dealersHand;
    }

    public boolean checkQuad() {
        if(this.dealersHand.getFirst().equals(this.dealersHand.getLast())){
            mergeLists(this.dealersHand, this.player.getHand());
            // Removes every potential hand except ones that can beat a quad
            for(String potentialHandString: this.player.getPossibleHands()){
                if(!potentialHandString.equals("Royal Flush") && !potentialHandString.equals("Straight Flush")){
                    player.removePossibleHand(potentialHandString);
                }
            }
            return this.player.setPokerHand(this.connectorManager.sendForHand("Quad", this.mergedList));
        }
        return false;
    }

    public boolean checkTripleAndTwoPair() {
        int sameCardsCount = 0;
        int indexDifferentCardOne = -1;
        int indexDifferentCardTwo = -1;
        boolean returnValue = false;

        mergeLists(this.dealersHand, this.player.getHand());
        Card tripleCard = player.getPokerHand().getHandCards().getFirst();

        for(int i = 0; i < this.mergedList.size(); i++){
            if(this.mergedList.get(i).equals(tripleCard)){
                sameCardsCount++;
            }
            else{
                if(indexDifferentCardOne != -1){
                    indexDifferentCardTwo = i;
                }
                else {
                    indexDifferentCardOne = i;
                }
            }
        }
        if(sameCardsCount == 3){
            this.mergedList.remove(indexDifferentCardOne);
            returnValue = player.setPokerHand(this.connectorManager.sendForHand("Triple", this.mergedList));
            // Removes possible hands that are less than the triple
            this.player.removePossibleHand("Two Pair");
            this.player.removePossibleHand("One Pair");
        }
        else if(this.mergedList.get(indexDifferentCardOne).equals(this.mergedList.get(indexDifferentCardTwo))){
            returnValue = this.player.setPokerHand(this.connectorManager.sendForHand("Two Pair", this.mergedList));
            // Removes possible hand of One Pair
            this.player.removePossibleHand("One Pair");
        }

        return returnValue;
    }

    public boolean checkSinglePair() {
        for(Card card : player.getHand()){
            int index = dealersHand.indexOf(card);
            if(index != -1){
                this.mergedList.add(dealersHand.get(index));
                this.mergedList.add(card);
                player.setPokerHand(this.connectorManager.sendForHand("One Pair", this.mergedList));
                return true;
            }
        }
        return false;
    }

    public void clearList(){
        this.mergedList.clear();
    }

    private void mergeLists(List<Card> list1, List<Card> list2){
        this.mergedList.addAll(list1);
        this.mergedList.addAll(list2);
    }
}
