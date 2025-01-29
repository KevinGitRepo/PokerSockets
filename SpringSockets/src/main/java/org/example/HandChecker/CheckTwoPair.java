package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

import java.util.ArrayList;
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

        Card pairCard = pokerHand.getHandCards().get(0);
        Card notPairCard;

        Card dealerLastCard = dealersHand.get( dealersHand.size() - 1 );

        // Finds the card that is not a part of the One Pair
        // One pair is assumed to already exists
        if ( pairCard.equals( player.getHand().get(0) ) ) {
            notPairCard = dealerLastCard;
        }
        else {
            notPairCard = dealersHand.get(0);
        }

        if ( !notPairCard.equals( dealerLastCard ) ) {
            return false;
        }

        // Both cards were found in the dealer's hand and will combine them to create the Two Pair
        List<Card> mergedList = new ArrayList<>( player.getPokerHand().getHandCards() );
        mergedList.add( notPairCard );
        mergedList.add( dealerLastCard );

        player.removePossibleHand( PokerHandTypes.TWO_PAIR );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.TWO_PAIR, mergedList ) );
    }
}
