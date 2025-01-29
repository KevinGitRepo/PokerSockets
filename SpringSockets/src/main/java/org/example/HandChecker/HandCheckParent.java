package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;

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
    public List<Card> checkStraightHelper(Player player, List<Card> cardsList ) {
        // Sorts the cards to make the determination easier
        Collections.sort( cardsList );

        List<Card> cardsListCopy = new ArrayList<>( cardsList );

        int straightCount = 1;
        int previousCardValue = 0;

        // Instead of removing the cards from the list each time which is O(n), keep track of indexes
        int endIndex = 0;
        int counter = 0;

        // Finds if there is a sequence in the card list
        for ( Card card : cardsList ) {
            if ( card.getCardValue() == previousCardValue + 1 ) {
                straightCount++;
            }
            // Resets if the card is not in a sequence
            else if ( previousCardValue != 0 && straightCount < 5 ) {
                straightCount = 1;
            }

            // changes the final index only if there are at least 5 cards in a row
            if ( straightCount >= 5 ) {
                endIndex = counter;
            }

            counter++;
            previousCardValue = card.getCardValue();
        }

        // No straight if end index was never changed
        if ( endIndex == 0) {
            return null;
        }

        int beginningIndex = endIndex - 5;

        // Only if there contains more than a 5 card straight and checks for the highest straight possible
        while ( straightCount > 5 && checkCardInPlayerHand( player, cardsListCopy.subList( beginningIndex, endIndex ) ) ) {
            straightCount--;
            endIndex--;
            beginningIndex--;
        }

        // Will check the final 5 cards and return a list or null
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
            if ( card.equals( player.getHand().get(0) ) ) {
                firstCardFound = true;
            }
            else if ( card.equals( player.getHand().get( player.getHand().size() - 1 ) ) ) {
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
