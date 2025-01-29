package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.*;

public class CheckFullHouse extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckFullHouse( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * {@inheritDoc}
     */
    public boolean check(Player player, List<Card> dealersHand ) {

        List<Card> mergedList = super.mergeLists( player.getHand(), dealersHand );

        Map<Integer, List<Card>> fullHouseMap = new HashMap<>();

        for ( Card card : mergedList ) {
            fullHouseMap.putIfAbsent( card.getCardValue(), new ArrayList<>() );
            fullHouseMap.get( card.getCardValue() ).add( card );
        }

        // Check if at least 1 list has 3 and one has 2
        int keyForListOfThree = -1;
        int keyForListOfTwo = -1;

        // Makes sure there is at least 3 of the same number and 2 of another number
        for ( Integer integerKey : fullHouseMap.keySet() ) {
            // Gets the key for the list with size 3 and size 2
            if ( fullHouseMap.get( integerKey ).size() == 3 ) {
                // If two lists of 3's appear, the larger valued list will be considered the listOfThree
                if ( keyForListOfThree > integerKey ) {
                    keyForListOfTwo = Integer.max( keyForListOfTwo, integerKey );
                }
                else {
                    keyForListOfTwo = Integer.max( keyForListOfThree, keyForListOfTwo );
                    keyForListOfThree = integerKey;
                }
            }
            else if ( fullHouseMap.get( integerKey ).size() == 2 ) {
                keyForListOfTwo = Integer.max( keyForListOfTwo, integerKey );
            }
        }

        // If the second list has 3 values, removes one to keep size 5
        if ( fullHouseMap.get( keyForListOfTwo ).size() > 2 ) {
            fullHouseMap.get( keyForListOfTwo ).removeLast();
        }

        mergedList = super.mergeLists( fullHouseMap.get( keyForListOfThree ), fullHouseMap.get( keyForListOfTwo ) );

        // Makes sure the player has at least one card in the full house
        if ( !super.checkCardInPlayerHand( player, mergedList ) ) {
            return false;
        }

        // Removes all hands a Full House can beat
        player.removePossibleHands( Arrays.asList( PokerHandTypes.FULL_HOUSE, PokerHandTypes.TRIPLE, PokerHandTypes.TWO_PAIR ,
                                                    PokerHandTypes.STRAIGHT, PokerHandTypes.FLUSH) );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.FULL_HOUSE, mergedList ) );
    }
}
