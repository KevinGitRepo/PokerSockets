package org.example.HandIdentifier;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.List;

public class DealerNoCards implements HandIdentifier {

    private final HandConnectorManager connectorManager;
    private Player player;

    public DealerNoCards( HandConnectorManager connectorManager ) {
        this.connectorManager = connectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand( Player player, List<Card> dealersHand ) {
        // Add all possible hands to start out
        this.player = player;
        return checkSinglePair();
    }

    /**
     * Specific single pair check where this will only check if the player's two cards are a pair
     * @return true if the player's cards are a pair; false otherwise
     */
    private boolean checkSinglePair() {
        List<Card> playerHand = this.player.getHand();

        if( playerHand.get(0) == null || playerHand.get(1) == null ) {
            return false;
        }

        // Checks for a single pair in the player's current hand
        if( playerHand.get(0).getCardValue() ==  playerHand.get(1).getCardValue() ) {
            this.player.setPokerHand( this.connectorManager.sendForHand( PokerHandTypes.ONE_PAIR, playerHand ) );

            // Removes One Pair from a potential hand since another pair would be considered two pair
            this.player.removePossibleHand( PokerHandTypes.ONE_PAIR );
            return true;
        }

        return false;
    }
}
