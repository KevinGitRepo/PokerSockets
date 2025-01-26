package HandChecker;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.ArrayList;
import java.util.List;

public class CheckQuad implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckQuad( HandConnectorManager handConnectorManager ) {
        this.handConnectorManager = handConnectorManager;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean check( Player player, List<Card> dealersHand ) {

        List<Card> mergedList = new ArrayList<>();

        // Assumes player already has a triple to consider a quad
        // Retrieves one of the triple cards, if last card in dealers hand
        for ( Card card : player.getPokerHand().getHandCards() ) {
            if ( card.equals( dealersHand.getLast() ) ) {
                mergedList.add( card );
            }
        }

        // 3 cards must equal the last card in the dealers hand in order to create a quad
        if( mergedList.size() != 3 ){
            return false;
        }

        mergedList.add( dealersHand.getLast() );

        // Removes every potential hand except ones that can beat a quad
        for( PokerHandTypes potentialHandType: player.getPossibleHands() ) {

            if( !potentialHandType.equals( PokerHandTypes.ROYAL_FLUSH ) &&
                    !potentialHandType.equals( PokerHandTypes.STRAIGHT_FLUSH ) ) {

                player.removePossibleHand( potentialHandType );
            }
        }

        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.QUAD, mergedList ) );
    }
}
