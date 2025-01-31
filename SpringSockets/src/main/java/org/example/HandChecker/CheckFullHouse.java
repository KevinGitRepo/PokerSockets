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

        int keyForListOfThree = -1;
        int keyForListOfTwo = -1;

        for ( Card card : mergedList ) {
            fullHouseMap.putIfAbsent( card.getCardValue(), new ArrayList<>() );
            fullHouseMap.get( card.getCardValue() ).add( card );

            if ( fullHouseMap.get( card.getCardValue() ).size() == 3 ) {
                if ( keyForListOfThree == -1 ) {
                    keyForListOfThree = card.getCardValue();
                }
                else {
                    keyForListOfThree = Math.max(card.getCardValue(), keyForListOfThree);
                    keyForListOfTwo = Math.max(Math.min(card.getCardValue(), keyForListOfThree), keyForListOfTwo);
                }
            }
            else if ( fullHouseMap.get( card.getCardValue() ).size() == 2 ) {
                keyForListOfTwo = card.getCardValue();
            }
        }

        List<Card> fullHouseSecondList = fullHouseMap.get( keyForListOfTwo );
        List<Card> fullHouseFirstList = fullHouseMap.get( keyForListOfThree );

        if ( fullHouseSecondList == null || fullHouseFirstList == null ) {
            return false;
        }

        if ( fullHouseSecondList.size() < 2 || fullHouseFirstList.size() < 3 ) {
            return false;
        }

        // If the second list has 3 values, removes one to keep size 5
        if ( fullHouseSecondList.size() > 2 ) {
            fullHouseSecondList.remove( fullHouseSecondList.size() - 1 );
        }

        mergedList = super.mergeLists( fullHouseFirstList, fullHouseSecondList );

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
