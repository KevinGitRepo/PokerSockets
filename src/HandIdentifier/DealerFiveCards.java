//package HandIdentifier;
//
//import HandConnector.HandConnectorManager;
//import main.Card;
//import main.Player;
//
//import java.util.List;
//
//public class DealerFiveCards implements HandIdentifier {
//
//    private final HandConnectorManager connectorManager;
//    private HandIdentifierDistribute handIdentifierDistribute;
//
//    public DealerFiveCards(HandConnectorManager connectorManager, HandIdentifierDistribute handIdentifierDistribute) {
//        this.handIdentifierDistribute = handIdentifierDistribute;
//        this.connectorManager = connectorManager;
//    }
//
//    @Override
//    public boolean checkHand(Player player, List<Card> dealersHand) {
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
//        return true;
//    }
//}
