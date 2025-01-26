package HandChecker;

import HandConnector.HandConnectorManager;
import Hands.PokerHand;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckTriple implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckTriple( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check( Player player, List<Card> dealersHand ) {

        PokerHand pokerHand = player.getPokerHand();
        Card dealersLastCard = dealersHand.getLast();
        List<Card> mergedList = new ArrayList<>();

        // If hand is one pair, then check single card pair for triple
        if ( pokerHand.getHandName() == PokerHandTypes.ONE_PAIR ) {
            if ( !pokerHand.getHandCards().getFirst().equals( dealersLastCard ) ) {
                return false;
            }

            mergedList.addAll( pokerHand.getHandCards() );
            mergedList.add( pokerHand.getHandCards().getFirst() );
        }

        // If hand is two pair, checks if either pair could be a triple
        // This would make it a full house, but won't check here and will check later on
        if ( pokerHand.getHandName() == PokerHandTypes.TWO_PAIR ) {
            if ( player.getHand().getFirst().equals( dealersLastCard ) ) {
                mergedList.add( player.getHand().getFirst() );
            }
            else if ( player.getHand().getLast().equals( dealersLastCard ) ) {
                mergedList.add( player.getHand().getLast() );
            }
            else {
                return false;
            }

            mergedList.add( dealersLastCard );
        }

        // Returns if triple is already found
        if ( mergedList.size() == 3 ) {
            return player.setPokerHand(
                    this.handConnectorManager.sendForHand(
                            PokerHandTypes.TRIPLE, mergedList ) );
        }

        // Find third card for triple if Two Pair was true
        for ( Card card : pokerHand.getHandCards() ) {
            if ( !mergedList.contains( card ) && card.getCardValue() == mergedList.getFirst().getCardValue() ) {
                mergedList.add( card );
                break;
            }
        }

        // A better triple would change into a full house which will be checked by that point
        // One Pair was already removed by this point
        player.removePossibleHands( Arrays.asList( PokerHandTypes.TRIPLE, PokerHandTypes.TWO_PAIR ) );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.TRIPLE, mergedList ) );
    }
}
