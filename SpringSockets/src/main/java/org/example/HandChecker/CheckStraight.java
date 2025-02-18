package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.List;

public class CheckStraight extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckStraight( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        // Combines lists
        List<Card> mergedList = super.mergeLists( player.getHand(), dealersHand );

        // Checks if list contains a straight, if no straight, returns null
        List<Card> returnedList = super.checkStraightHelper( player, mergedList );

        if ( returnedList == null ) {
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.STRAIGHT, returnedList ) );
    }
}
