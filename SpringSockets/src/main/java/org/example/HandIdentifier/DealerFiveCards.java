package org.example.HandIdentifier;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;

import java.util.List;

public class DealerFiveCards implements HandIdentifier{

    private final HandIdentifierDistribute handIdentifierDistribute;

    public DealerFiveCards( HandIdentifierDistribute handIdentifierDistribute ) {
        this.handIdentifierDistribute = handIdentifierDistribute;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {

        // Will check every single hand

        boolean returnValue = false;

        for ( PokerHandTypes pokerHandTypes : player.getPossibleHands() ) {
            if ( !player.isHandPossible( pokerHandTypes ) ) {
                continue;
            }

            // Needs to return true if only one hand was changed
            returnValue = this.handIdentifierDistribute.checkHand( pokerHandTypes, player, dealersHand ) || returnValue;
        }

        return returnValue;
    }
}
