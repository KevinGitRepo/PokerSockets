package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

import java.util.List;

public class CheckStraightFlush extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckStraightFlush( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        PokerHand pokerHand = player.getPokerHand();

        if (pokerHand == null || pokerHand.getHandName() != PokerHandTypes.FLUSH) {
            return false;
        }

        // Has to be a flush to have a chance of being a straight flush
        // Checks the flush for a straight, returns null if no straight is found
        List<Card> returnedList = super.checkStraightHelper( player, player.getPokerHand().getHandCards() );

        if ( returnedList == null ) {
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.STRAIGHT_FLUSH, returnedList ) );
    }
}
