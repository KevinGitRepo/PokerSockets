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

        Map<Integer, List<Card>> fullHouseMap = new TreeMap<>();

        int keyForListOfThree = -1;
        int keyForListOfTwo = -1;

        for ( Card card : mergedList ) {
            fullHouseMap.putIfAbsent(card.getCardValue(), new ArrayList<>());
            fullHouseMap.get(card.getCardValue()).add(card);
        }

        for (int key : fullHouseMap.keySet()) {
            if ( fullHouseMap.get(key).size() == 3 && keyForListOfThree < 0) {
                keyForListOfThree = key;
            }
            else if ( fullHouseMap.get(key).size() >= 2 && keyForListOfTwo < 0) {
                keyForListOfTwo = key;
            }

            if ( keyForListOfThree > 0 && keyForListOfTwo > 0 ) {
                break;
            }
        }

        if (keyForListOfThree < 0 || keyForListOfTwo < 0) {
            return false;
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
