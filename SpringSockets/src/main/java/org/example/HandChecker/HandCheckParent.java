package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;

import java.util.*;

public class HandCheckParent {

    private static final int SIZE_OF_POKER_HAND = 4;

    /**
     * Checks if the card list provided contains a straight
     * @param player only is sent to make sure the hand contains a card in the straight
     * @param cardsList the list which will be tested for a straight
     * @return the list of cards that form a straight
     */
    public List<Card> checkStraightHelper(Player player, List<Card> cardsList ) {
        // Sorts the cards to make the determination easier
        Collections.sort( cardsList );

        boolean found = false;

        // Instead of removing the cards from the list each time which is O(n), keep track of indexes
        int endIndex = 0;
        int index = 0;
        int beginningIndex = 0;

        // Only checks first and last card in a 5 card sequence for a straight since the hand is already in order
        while ( index <= cardsList.size() - ( SIZE_OF_POKER_HAND + 1 ) ) {
            if (cardsList.get(index).getCardValue() + SIZE_OF_POKER_HAND == cardsList.get(index + SIZE_OF_POKER_HAND).getCardValue() &&
                    noDuplicateCards( cardsList.subList(index, index + SIZE_OF_POKER_HAND + 1) ) ) {
                endIndex = index + SIZE_OF_POKER_HAND;
                beginningIndex = index;
                found = true;
            }
            index++;
        }

        // No straight if found was never changed
        if ( !found ) {
            return null;
        }

        // Will check the final 5 cards and return a list or null
        // Returns a new list if a card in the final list is in the player's hand and there are no duplicate cards
        return checkCardInPlayerHand( player, cardsList.subList( beginningIndex, endIndex + 1) )
                ? cardsList.subList( beginningIndex, endIndex + 1) : null;
    }

    /**
     * Checks to see if there are any duplicate cards inside the list
     * @param cardsList list of cards
     * @return true if there are no duplicate cards, false otherwise
     */
    private boolean noDuplicateCards( List<Card> cardsList ) {
        int previousValue = 0;
        for ( Card card : cardsList ) {
            if ( card.getCardValue() == previousValue ) {
                return false;
            }
            previousValue = card.getCardValue();
        }
        return true;
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
