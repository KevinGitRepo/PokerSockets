package org.example.HandIdentifier;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DealerThreeCards implements HandIdentifier {

    private final HandIdentifierDistribute handIdentifierDistribute;
    private final Set<PokerHandTypes> checkHands;

    public DealerThreeCards( HandIdentifierDistribute handIdentifierDistribute ) {
        this.handIdentifierDistribute = handIdentifierDistribute;
        this.checkHands = new HashSet<>();
        populateSetHands();
    }

    /**
     * Adds poker hand types to set
     * These hands will be checked when dealer has 3 cards
     */
    private void populateSetHands() {
        this.checkHands.addAll(Arrays.asList( PokerHandTypes.ONE_PAIR, PokerHandTypes.TWO_PAIR,
                                                PokerHandTypes.TRIPLE, PokerHandTypes.QUAD ) );
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        /*
        Only checks for:
        One Pair
        Two Pair
        Triple ( Full House checked here also )
        Quad
         */

        boolean returnValue = false;

        for ( PokerHandTypes pokerHandTypes : player.getPossibleHands() ) {
            if ( !player.isHandPossible( pokerHandTypes ) || !checkHands.contains( pokerHandTypes ) ) {
                continue;
            }

            // Needs to return true if only one hand was changed
            returnValue = this.handIdentifierDistribute.checkHand( pokerHandTypes, player, dealersHand ) || returnValue;
        }

        return returnValue;
    }
}
