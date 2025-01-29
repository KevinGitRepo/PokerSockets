package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;
import org.example.Hands.PokerHand;

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
    public boolean check(Player player, List<Card> dealersHand ) {

        PokerHand pokerHand = player.getPokerHand();
        Card pokerHandFirstCard = pokerHand.getHandCards().get(0);
        Card dealersLastCard = dealersHand.get(dealersHand.size() - 1);
        List<Card> mergedList = new ArrayList<>();

        // If hand is one pair, then check single card pair for triple
        if ( pokerHand.getHandName() == PokerHandTypes.ONE_PAIR ) {
            if ( !pokerHandFirstCard.equals( dealersLastCard ) ) {
                return false;
            }

            mergedList.addAll( pokerHand.getHandCards() );
            mergedList.add( dealersLastCard );
        }

        // If hand is two pair, checks if either pair could be a triple
        // This would make it a full house, will create full house here and remove possible hands for quicker
        // functionality

        if ( pokerHand.getHandName() == PokerHandTypes.TWO_PAIR ) {

            Card playerFirstCard = player.getHand().get(0);
            Card playerSecondCard = player.getHand().get(1);

            // Add card to list if they are equal, return false otherwise
            if ( playerFirstCard.equals( dealersLastCard ) || playerSecondCard.equals( dealersLastCard ) ) {
                mergedList.addAll( pokerHand.getHandCards() );
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

        // Found a full house

        // One Pair was already removed by this point
        player.removePossibleHands( Arrays.asList( PokerHandTypes.TRIPLE, PokerHandTypes.TWO_PAIR ,
                                                    PokerHandTypes.STRAIGHT, PokerHandTypes.FLUSH) );

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.FULL_HOUSE, mergedList ) );
    }
}
