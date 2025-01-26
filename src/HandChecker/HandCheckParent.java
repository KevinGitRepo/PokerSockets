package HandChecker;

import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandCheckParent {

    /**
     * Checks if the card list provided contains a straight
     * @param player only is sent to make sure the hand contains a card in the straight
     * @param cardsList the list which will be tested for a straight
     * @return the list of cards that form a straight
     */
    public List<Card> checkStraightHelper( Player player, List<Card> cardsList ) {
        // Sorts the cards to make the determination easier
        Collections.sort( cardsList );

        List<Card> cardsListCopy = new ArrayList<>( cardsList );

        int straightCount = 1;
        int previousCardValue = 0;

        // Instead of removing the cards from the list which is O(n), keep track of indexes
        int endIndex = 0;
        int counter = 0;

        // Makes sure the cards are
        for ( Card card : cardsList ) {
            if ( card.getCardValue() == previousCardValue + 1 ) {
                straightCount++;
            }
            if ( previousCardValue != 0 && straightCount < 5 ) {
                straightCount = 1;
            }
            else {
                endIndex = counter;
            }

            counter++;
            previousCardValue = card.getCardValue();
        }

        int beginningIndex = endIndex - 5;

        // Only if there contains more than a 5 card straight and checks for the highest straight possible
        while ( straightCount > 5 && checkCardInPlayerHand( player, cardsListCopy.subList( beginningIndex, endIndex ) ) ) {
            straightCount--;
            endIndex--;
            beginningIndex--;
        }

        // Returns the sub list for the largest possible straight
        return checkCardInPlayerHand( player, cardsListCopy.subList( beginningIndex, endIndex ) ) ? cardsListCopy.subList( beginningIndex, endIndex ) : null;
    }

    /**
     * Checks to make sure the player's hand contains a card which exists in the list provided
     * @param player the player which will have their hand tested for a certain card
     * @param cardsList list of cards
     * @return true if the player's hand contains a card inside the cardsList, false otherwise
     */
    public boolean checkCardInPlayerHand( Player player, List<Card> cardsList ) {
        boolean firstCardFound = false;
        boolean secondCardFound = false;

        for ( Card card : cardsList ) {
            if ( card.equals( player.getHand().getFirst() ) ) {
                firstCardFound = true;
            }
            else if ( card.equals( player.getHand().getLast() ) ) {
                secondCardFound = true;
            }
        }

        return firstCardFound || secondCardFound;
    }

    /**
     * Combines two lists together
     * @param cardsList1 first list to be combined
     * @param cardsList2 second list to be combined
     * @return a combined list of the two lists provided
     */
    public List<Card> mergeLists( List<Card> cardsList1, List<Card> cardsList2 ) {
        List<Card> cardsList3 = new ArrayList<>( cardsList1 );
        cardsList3.addAll( cardsList2 );
        return cardsList3;
    }

}
