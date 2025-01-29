package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.List;

public class CheckRoyalFlush extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckRoyalFlush( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        // Has to be flush in order to check for royal flush
        List<Card> flushCards = player.getPokerHand().getHandCards();

        // Flush will always be in order based on how CheckFlush was implemented
        // Makes sure first card is a 10 and last card is a 14 to create Royal Flush
        if ( flushCards.get(0).getCardValue() != 10 || flushCards.get(flushCards.size() - 1).getCardValue() != 14 ) {
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.ROYAL_FLUSH, flushCards ) );
    }
}
