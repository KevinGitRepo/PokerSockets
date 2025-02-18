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

public class CheckQuad extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckQuad( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {
        /*
        Checks to make sure that the player already has a triple or a full house in or to check for a quad.
        If a triple, the list of cards will be added to a list.
        If a full house, the cards will be split up to determine the triple and the pair.
        The value of the triple cards will be saved and used for determining if the last card in the dealers hand
        is equivalent.
        If equivalent, removes any card type less than a quad and changes the poker hand to quad
         */

        PokerHand pokerHand = player.getPokerHand();

        if ( pokerHand == null ) {
            return false;
        }

        if ( !pokerHand.getHandName().equals( PokerHandTypes.FULL_HOUSE ) &&
                !pokerHand.getHandName().equals( PokerHandTypes.TRIPLE ) ) {
            return false;
        }

        List<Card> mergedList = new ArrayList<>( dealersHand );
        mergedList.addAll( player.getHand() );
        Map<Integer, List<Card>> cards = new HashMap<>();
        int quadValue = 0;

        // If the poker hand is not a triple or full house then it can't be a quad
        for ( Card card : mergedList ) {
            cards.putIfAbsent( card.getCardValue(), new ArrayList<>() );
            cards.get( card.getCardValue() ).add( card );
            if ( cards.get( card.getCardValue() ).size() == 4  &&
                super.checkCardInPlayerHand( player, cards.get( card.getCardValue() ) ) ) {
                quadValue = card.getCardValue();
            }
        }

        if ( quadValue <= 0 ) {
            return false;
        }

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
