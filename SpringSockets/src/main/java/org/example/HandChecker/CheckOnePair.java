package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.Arrays;
import java.util.List;

public class CheckOnePair implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckOnePair( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {
        // Only check the last card in dealersHand for a single pair instead of every card
        Card dealerLastCard = dealersHand.get(dealersHand.size() - 1);
        Card pairedCard;
        Card playersFirstCard = player.getHand().get(0);
        Card playersSecondCard = player.getHand().get(1);

        // Checks if either hand can make a single pair with the last card dealt
        if ( playersFirstCard.equals( dealerLastCard ) ) {
            pairedCard = playersFirstCard;
        }
        else if ( playersSecondCard.equals( dealerLastCard ) ) {
            pairedCard = playersSecondCard;
        }
        else {
            return false;
        }

        // If a larger pair is found later, Two Pair is created so no possible way to get a larger One Pair
        player.removePossibleHand( PokerHandTypes.ONE_PAIR );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.ONE_PAIR, Arrays.asList( dealerLastCard, pairedCard ) ) );
    }
}
