package HandIdentifier;

import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.List;

public class DealerTwoCards implements HandIdentifier {

    private final HandIdentifierDistribute handIdentifierDistribute;

    public DealerTwoCards( HandIdentifierDistribute handIdentifierDistribute ) {
        this.handIdentifierDistribute = handIdentifierDistribute;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand( Player player, List<Card> dealersHand ) {
        /*
        Check Triple first for quad
        Check Single Pair next for triple and two pair

        Removes as many possible hands as possible for later hands
        Try to decrease amount of calculating for the final two cards
        */

        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
         */

        boolean returnValue = false;
        boolean playerPokerHandBool = player.getPokerHand() != null;

        if( !playerPokerHandBool ) {
            // No poker hand then check for a single pair
            return this.handIdentifierDistribute.checkHand( PokerHandTypes.ONE_PAIR, player, dealersHand );
        }

        if( player.getPokerHand().getHandName().equals( PokerHandTypes.TRIPLE ) ) {
            // If the poker hand is a triple then only possible hand would be quad
            returnValue = this.handIdentifierDistribute.checkHand( PokerHandTypes.QUAD, player, dealersHand );
        }
        else if( player.getPokerHand().getHandName().equals( PokerHandTypes.ONE_PAIR ) ) {
            // If the poker hand is a single pair then only possible beatable hand would be two pair or triple
            returnValue = this.handIdentifierDistribute.checkHand( PokerHandTypes.TRIPLE, player, dealersHand );

            // Should only check two pair if triple is not possible
            if( !returnValue ) {
                returnValue = this.handIdentifierDistribute.checkHand( PokerHandTypes.TWO_PAIR, player, dealersHand );
            }
        }

        return returnValue;
    }
}
