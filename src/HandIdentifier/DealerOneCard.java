package HandIdentifier;

import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.List;

public class DealerOneCard implements HandIdentifier {

    private HandIdentifierDistribute handIdentifierDistribute;

    public DealerOneCard(HandIdentifierDistribute handIdentifierDistribute) {
        this.handIdentifierDistribute = handIdentifierDistribute;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        // Have to check larger hands first
        // Check single pair
        // Check triple pair iff single pair was confirmed

        if(player.getPokerHand() == null){
            return this.handIdentifierDistribute.checkHand(PokerHandTypes.ONE_PAIR, player, dealersHand);
        }

        return this.handIdentifierDistribute.checkHand(PokerHandTypes.TRIPLE, player, dealersHand);
    }
}
