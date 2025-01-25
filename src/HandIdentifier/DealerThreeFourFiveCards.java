package HandIdentifier;

import HandConnector.HandConnectorManager;
import main.Card;
import main.Player;
import main.PokerHandTypes;

import java.util.List;

public class DealerThreeFourFiveCards implements HandIdentifier{

    private Player player;
    private List<Card> dealersHand;
    private HandIdentifierDistribute handIdentifierDistribute;
    private HandConnectorManager handConnectorManager;

    public DealerThreeFourFiveCards(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
        this.handIdentifierDistribute = handIdentifierDistribute;
        this.handConnectorManager = connectorManager;
    }

    @Override
    public boolean checkHand(Player player, List<Card> dealersHand) {
        /*
        Combination of three card, four card, and five card hands due to similar function calls
        Each one goes through possible hands and checks each one, usually by the fourth or fifth card, the
        list of possible hands is smaller. Will only try this combination out for now and might change later.
         */

        boolean returnValue = false;
        boolean playerPokerHandBool = player.getPokerHand() != null;

        if (!playerPokerHandBool) {
            return this.handIdentifierDistribute.checkHand(PokerHandTypes.ONE_PAIR, player, dealersHand);
        }

        for (PokerHandTypes pokerHandTypes : player.getPossibleHands()){
            if (!player.isHandPossible(pokerHandTypes)){
                continue;
            }

            if (pokerHandTypes == PokerHandTypes.STRAIGHT_FLUSH ||
                    pokerHandTypes == PokerHandTypes.ROYAL_FLUSH){
                this.handIdentifierDistribute.checkHand(PokerHandTypes.FLUSH, player, dealersHand);
            }

            // Needs to return true if only one hand was changed
            returnValue = this.handIdentifierDistribute.checkHand(pokerHandTypes, player, dealersHand) || returnValue;
        }


        /*
        Hands to check:
        OnePair
        Triple
        Quad
        TwoPair
        Flush
        FullHouse
        RoyalFlush
        Straight
        StraightFlush
         */

        return returnValue;
    }
}
