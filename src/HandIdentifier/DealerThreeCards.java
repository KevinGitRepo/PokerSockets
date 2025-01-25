//package HandIdentifier;
//
//import HandConnector.HandConnectorManager;
//import main.Card;
//import main.Player;
//import main.PokerHandTypes;
//
//import java.util.List;
//
//public class DealerThreeCards implements HandIdentifier {
//
//    private Player player;
//    private List<Card> dealersHand;
//    private HandIdentifierDistribute handIdentifierDistribute;
//    private HandConnectorManager handConnectorManager;
//
//    public DealerThreeCards(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
//        this.handIdentifierDistribute = handIdentifierDistribute;
//        this.handConnectorManager = connectorManager;
//    }
//
//    @Override
//    public boolean checkHand(Player player, List<Card> dealersHand) {
//        /*
//        Potential hands would have been eliminated from the players' list by this point
//        Dealer with Three card would only need to check for specific cases
//        Only will eliminate flushes if not possible (Royal Flush, Straight Flush, Flush)
//        Will always recheck for Quad, Triple, Two Pair, and One Pair
//        Possible Hands that could have been found so far:
//            Quad, Triple, Two Pair, One Pair
//         */
//
//        boolean returnValue = false;
//        boolean playerPokerHandBool = player.getPokerHand() != null;
//
//        if (!playerPokerHandBool) {
//            return this.handIdentifierDistribute.checkHand(PokerHandTypes.ONE_PAIR, player, dealersHand);
//        }
//
//        for (PokerHandTypes pokerHandTypes : player.getPossibleHands()){
//            if (!player.isHandPossible(pokerHandTypes)){
//                continue;
//            }
//
//            if (pokerHandTypes == PokerHandTypes.STRAIGHT_FLUSH ||
//                    pokerHandTypes == PokerHandTypes.ROYAL_FLUSH){
//                this.handIdentifierDistribute.checkHand(PokerHandTypes.FLUSH, player, dealersHand);
//            }
//
//            // Needs to return true if only one hand was changed
//            returnValue = this.handIdentifierDistribute.checkHand(pokerHandTypes, player, dealersHand) || returnValue;
//        }
//
//
//        /*
//        Hands to check:
//        OnePair
//        Triple
//        Quad
//        TwoPair
//        Flush
//        FullHouse
//        RoyalFlush
//        Straight
//        StraightFlush
//         */
//
//        return returnValue;
//    }
//}
