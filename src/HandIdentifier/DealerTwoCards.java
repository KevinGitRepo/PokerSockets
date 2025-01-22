package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class DealerTwoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private final List<Card> mergedList;

    public DealerTwoCards(HandConnectorManager connectorManager) {
        this.connectorManager = connectorManager;
        this.mergedList = new ArrayList<>();
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // Check Triple first for quad
        // Check Single Pair next for triple and two pair

        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
         */

        boolean returnValue = false;
        boolean playerPokerHandBool = player.getPokerHand() != null;

        if(playerPokerHandBool && player.getPokerHand().getHandName().equals("Triple")){
            // If the pokerhand is a triple then only possible hand would be quad
            returnValue = checkQuad(player, dealersHand);
        }
        else if(playerPokerHandBool && player.getPokerHand().getHandName().equals("One Pair")){
            // If the pokerhand is a single pair then only possible beatable hand would be two pair or triple
            returnValue = checkTripleAndTwoPair(player, dealersHand);
        }
        else{
            // No pokerhand then check for a single pair
            returnValue = checkSinglePair(player, dealersHand);
        }

        clearList();
        return returnValue;
    }

    private boolean checkSinglePair(Player player, List<Card> dealersHand) {
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

    private boolean checkTripleAndTwoPair(Player player, List<Card> dealersHand) {
        int sameCardsCount = 0;
        int indexDifferentCardOne = -1;
        int indexDifferentCardTwo = -1;
        this.mergedList.addAll(dealersHand);
        this.mergedList.addAll(player.getHand());
        Card tripleCard = player.getPokerHand().getHandCards().getFirst();
        boolean returnValue = false;

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
        }
        else if(this.mergedList.get(indexDifferentCardOne).equals(this.mergedList.get(indexDifferentCardTwo))){
            returnValue = player.setPokerHand(this.connectorManager.sendForHand("Two Pair", this.mergedList));
        }

        return returnValue;
    }

    private boolean checkQuad(Player player, List<Card> dealersHand) {
        if(dealersHand.getFirst().equals(dealersHand.getLast())){
            this.mergedList.addAll(dealersHand);
            this.mergedList.addAll(player.getHand());
            return player.setPokerHand(this.connectorManager.sendForHand("Quad", this.mergedList));
        }
        return false;
    }

    private void clearList(){
        this.mergedList.clear();
    }
}
