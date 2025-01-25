package HandChecker;

import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandCheckParent {

    public List<Card> checkStraightHelper(Player player, List<Card> cardsList) {
        Collections.sort(cardsList);

        List<Card> cardsListCopy = new ArrayList<>(cardsList);

        int straightCount = 1;
        int previousCardValue = 0;

        for (Card card : cardsList) {
            if (card.getCardValue() == previousCardValue + 1){
                straightCount++;
            }
            else {
                straightCount = 1;
                cardsListCopy.remove(card);
            }

            previousCardValue = card.getCardValue();
        }

        return straightCount == 5 && checkCardInPlayerHand(player, cardsListCopy) ? cardsListCopy : null;
    }

    private boolean checkCardInPlayerHand(Player player, List<Card> cardsList) {
        boolean firstCardFound = false;
        boolean secondCardFound = false;

        for (Card card : cardsList) {
            if (card.equals(player.getHand().getFirst())) {
                firstCardFound = true;
            }
            else if (card.equals(player.getHand().getLast())) {
                secondCardFound = true;
            }
        }

        return firstCardFound || secondCardFound;
    }

    public List<Card> mergeLists(List<Card> cardsList1, List<Card> cardsList2) {
        List<Card> cardsList3 = new ArrayList<>();
        cardsList3.addAll(cardsList1);
        cardsList3.addAll(cardsList2);
        return cardsList3;
    }

}
