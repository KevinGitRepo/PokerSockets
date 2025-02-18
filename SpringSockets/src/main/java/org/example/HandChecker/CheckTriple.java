package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

import java.util.*;

public class CheckTriple implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckTriple( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        PokerHand pokerHand = player.getPokerHand();

        if ( pokerHand == null) {
            return false;
        }

        Map<Integer, List<Card>> dealersCards = new HashMap<>();
        List<Card> mergedList = new ArrayList<>();

        for ( Card card : dealersHand ) {
            dealersCards.putIfAbsent( card.getCardValue(), new ArrayList<>() );
            dealersCards.get( card.getCardValue() ).add( card );
        }

        List<Card> firstCardTripleList = dealersCards.get( player.getHand().get(0).getCardValue() );
        List<Card> secondCardTripleList = dealersCards.get( player.getHand().get(1).getCardValue() );

        // Could have a quad here but will not check until the quad checks later
        if ( ( firstCardTripleList != null && firstCardTripleList.size() == 2 ) ) {
            mergedList.addAll( firstCardTripleList );
            mergedList.add( player.getHand().get( 0 ) );
        }
        else if ( ( secondCardTripleList != null && secondCardTripleList.size() == 2 ) ) {
            mergedList.addAll( secondCardTripleList );
            mergedList.add( player.getHand().get( 1 ) );
        }

        if ( !mergedList.isEmpty() ) {
            return player.setPokerHand(
                    this.handConnectorManager.sendForHand(
                            PokerHandTypes.TRIPLE, mergedList ) );
        }

        // If Empty then it could be a quad or nothing
        // Check for quad
        if ( ( firstCardTripleList != null && firstCardTripleList.size() == 3 ) ) {
            mergedList.addAll( firstCardTripleList );
            mergedList.add( player.getHand().get( 0 ) );
        }
        else if ( ( secondCardTripleList != null && secondCardTripleList.size() == 3 ) ) {
            mergedList.addAll( secondCardTripleList );
            mergedList.add( player.getHand().get( 1 ) );
        }

        if ( !mergedList.isEmpty() ) {
            // Removes other hands too
            removePokerHandTypes( player );

            return player.setPokerHand(
                    this.handConnectorManager.sendForHand(
                            PokerHandTypes.QUAD, mergedList ) );
        }

        return false;
    }

    private void removePokerHandTypes(Player player) {
        for( PokerHandTypes potentialHandType: player.getPossibleHands() ) {

            if( !potentialHandType.equals( PokerHandTypes.ROYAL_FLUSH ) &&
                    !potentialHandType.equals( PokerHandTypes.STRAIGHT_FLUSH ) ) {

                player.removePossibleHand( potentialHandType );
            }
        }
    }
}
