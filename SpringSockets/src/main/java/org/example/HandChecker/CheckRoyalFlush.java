package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

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

        PokerHand pokerHand = player.getPokerHand();

        if (pokerHand == null || pokerHand.getHandName() != PokerHandTypes.FLUSH) {
            return false;
        }

        // Has to be flush in order to check for royal flush
        // When the flush is created, it's in order
        List<Card> flushCards = player.getPokerHand().getHandCards();

        // Makes sure first card is a 10 and last card is a 14 to create Royal Flush
        if ( flushCards.get(0).getCardValue() != 10 || flushCards.get(flushCards.size() - 1).getCardValue() != 14 ) {
            return false;
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.ROYAL_FLUSH, flushCards ) );
    }
}
