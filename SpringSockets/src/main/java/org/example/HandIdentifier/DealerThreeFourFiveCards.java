package org.example.HandIdentifier;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;

import java.util.List;

public class DealerThreeFourFiveCards implements HandIdentifier {

    private final HandIdentifierDistribute handIdentifierDistribute;

    public DealerThreeFourFiveCards( HandIdentifierDistribute handIdentifierDistribute ) {
        this.handIdentifierDistribute = handIdentifierDistribute;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand(Player player, List<Card> dealersHand ) {
        /*
        Combination of three card, four card, and five card hands due to similar function calls
        Each one goes through possible hands and checks each one, usually by the fourth or fifth card, the
        list of possible hands is smaller. Will only try this combination out for now and might change later.
         */

        boolean returnValue = false;

        for ( PokerHandTypes pokerHandTypes : player.getPossibleHands() ) {
            if ( !player.isHandPossible( pokerHandTypes ) ) {
                continue;
            }

            // Needs to return true if only one hand was changed
            returnValue = this.handIdentifierDistribute.checkHand( pokerHandTypes, player, dealersHand ) || returnValue;
        }


        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
        Flush
        FullHouse
        RoyalFlush
        Straight
        StraightFlush
         */

        return returnValue;
    }
}
