package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckQuad implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckQuad( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {
        // TODO

        PokerHand pokerHand = player.getPokerHand();
        List<Card> mergedList = new ArrayList<>();
        Card tripleCard;
        Card fullHouseSecondCard;

        // If the poker hand is not a triple or full house then it can't be a quad
        if ( pokerHand.getHandName().equals( PokerHandTypes.TRIPLE ) ) {
            tripleCard = pokerHand.getHandCards().get( 0 );
        }
        // Gets both cards from the full house
        else if ( pokerHand.getHandName().equals( PokerHandTypes.FULL_HOUSE ) ) {
            Map<Integer, Integer> cards = new HashMap<>();
            for ( Card card : pokerHand.getHandCards() ) {
                cards.put( card.getCardValue(), cards.getOrDefault( card.getCardValue(), 1 ) + 1 );
                if ( cards.get( card.getCardValue() ) == 3 ) {
                    tripleCard = card;
                }
                else if ( cards.get( card.getCardValue() ) == 2 ) {
                    fullHouseSecondCard = card;
                }
            }
        }
        else {
            return false;
        }

        // Assumes player already has a triple to consider a quad
        // Retrieves one of the triple cards, if last card in dealers hand
        for ( Card card : pokerHand.getHandCards() ) {
            if ( card.equals( dealersHand.get(dealersHand.size() - 1) ) ) {
                mergedList.add( card );
            }
        }

        // 3 cards must equal the last card in the dealers hand in order to create a quad
        if( mergedList.size() != 3 ){
            return false;
        }

        mergedList.add( dealersHand.get(dealersHand.size() - 1) );

        // Removes every potential hand except ones that can beat a quad
        for( PokerHandTypes potentialHandType: player.getPossibleHands() ) {

            if( !potentialHandType.equals( PokerHandTypes.ROYAL_FLUSH ) &&
                    !potentialHandType.equals( PokerHandTypes.STRAIGHT_FLUSH ) ) {

                player.removePossibleHand( potentialHandType );
            }
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.QUAD, mergedList ) );
    }
}
