package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

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
    public boolean check( Player player, List<Card> dealersHand ) {

        Card pairCard = player.getPokerHand().getHandCards().getFirst();
        Card notPairCard;

        // Finds the card that is not a part of the One Pair
        // One pair is assumed to already exists
        if ( pairCard.equals( player.getHand().getFirst() ) ) {
            notPairCard = dealersHand.getLast();
        }
        else {
            notPairCard = dealersHand.getFirst();
        }

        if ( !notPairCard.equals(dealersHand.getLast() ) ) {
            return false;
        }

        // Both cards were found in the dealer's hand and will combine them to create the Two Pair
        List<Card> mergedList = new ArrayList<>( player.getPokerHand().getHandCards() );
        mergedList.add( notPairCard );
        mergedList.add( dealersHand.getLast() );

        player.removePossibleHand( PokerHandTypes.TWO_PAIR );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.TWO_PAIR, mergedList ) );
    }
}
