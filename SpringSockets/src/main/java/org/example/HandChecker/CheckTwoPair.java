package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckTwoPair implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckTwoPair( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        PokerHand pokerHand = player.getPokerHand();

        if ( pokerHand == null ) {
            return false;
        }

        Card firstCard = null;
        Card secondCard = null;

        for ( Card card : dealersHand ) {
            if ( card.getCardValue() == player.getHand().get(0).getCardValue() ) {
                firstCard = card;
            }
            else if ( card.getCardValue() == player.getHand().get(1).getCardValue() ) {
                secondCard = card;
            }

            if ( firstCard != null && secondCard != null ) {
                break;
            }
        }

        if ( firstCard == null || secondCard == null ) {
            return false;
        }

        player.removePossibleHand( PokerHandTypes.TWO_PAIR );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.TWO_PAIR,
                        Arrays.asList( firstCard, player.getHand().get(0), secondCard, player.getHand().get(1) ) ) );
    }
}
