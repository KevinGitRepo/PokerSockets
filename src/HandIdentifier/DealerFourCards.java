//package HandIdentifier;
//
//import HandConnector.HandConnectorManager;
//import main.Card;
//import main.Player;
//import main.PokerHandTypes;
//
//import java.util.List;
//
//public class DealerFourCards implements HandIdentifier {
//
//    private final HandConnectorManager connectorManager;
//    private HandIdentifierDistribute handIdentifierDistribute;
//
//    public DealerFourCards(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
//        this.handIdentifierDistribute = handIdentifierDistribute;
//        this.connectorManager = connectorManager;
//    }
//
//    @Override
//    public boolean checkHand(Player player, List<Card> dealersHand) {
//
//        /*
//        Very similar to dealer 3 cards class
//
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
//        return true;
//    }
//}
