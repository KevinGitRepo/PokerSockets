//package HandIdentifier;
//
//import HandConnector.HandConnectorManager;
//import main.Card;
//import main.Player;
//
//import java.util.*;
//
//public abstract class HandIdentifierAbstract {
//    private Player player;
//    private List<Card> dealersHand;
//    private final List<Card> mergedList = new ArrayList<>();
//    private HandConnectorManager connectorManager;
//
//    public void setConnectorManager(HandConnectorManager connectorManager) {
//        this.connectorManager = connectorManager;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }
//
//    public void setDealersHand(List<Card> dealersHand) {
//        this.dealersHand = dealersHand;
//    }
//
//    public boolean checkStraight() {
//        clearList();
//
//        mergeLists(this.player.getHand(), this.dealersHand);
//
//        List<Card> returnedList = checkStraightHelper(this.mergedList);
//
//        if (returnedList == null){
//            return false;
//        }
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Straight", returnedList));
//    }
//
//    private List<Card> checkStraightHelper(List<Card> cardsList) {
//        Collections.sort(cardsList);
//
//        List<Card> cardsListCopy = new ArrayList<>(cardsList);
//
//        int straightCount = 1;
//        int previousCardValue = 0;
//
//        for (Card card : cardsList) {
//            if (card.getCardValue() == previousCardValue + 1){
//                straightCount++;
//            }
//            else {
//                straightCount = 1;
//                cardsListCopy.remove(card);
//            }
//
//            previousCardValue = card.getCardValue();
//        }
//
//        return straightCount == 5 && checkCardInPlayerHand() ? cardsListCopy : null;
//    }
//
//    public boolean checkStraightFlush(List<Card> flushCards) {
//        List<Card> returnedList = checkStraightHelper(flushCards);
//
//        if (returnedList == null){
//            return false;
//        }
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Straight Flush", returnedList));
//    }
//
//    public boolean checkRoyalFlush(List<Card> flushCards) {
//        Collections.sort(flushCards);
//
//        if (flushCards.getFirst().getCardValue() != 10 || flushCards.getLast().getCardValue() != 14){
//            return false;
//        }
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Royal Flush", flushCards));
//    }
//
//    public boolean checkFullHouse() {
//        clearList();
//
//        mergeLists(this.player.getHand(), this.dealersHand);
//
//        Map<Integer, List<Card>> fullHouseMap = new HashMap<>();
//
//        for (Card card : this.mergedList) {
//            fullHouseMap.computeIfAbsent(card.getCardValue(), k -> new ArrayList<>());
//            fullHouseMap.get(card.getCardValue()).add(card);
//        }
//
//        // Check if at least 1 list has 3 and one has 2
//        int keyForListOfThree = -1;
//        int keyForListOfTwo = -1;
//
//        for (Integer integerKey : fullHouseMap.keySet()) {
//            if (fullHouseMap.get(integerKey).size() == 3) {
//                if (keyForListOfThree > integerKey) {
//                    keyForListOfTwo = Integer.max(keyForListOfTwo, integerKey);
//                }
//                else {
//                    keyForListOfTwo = Integer.max(keyForListOfThree, keyForListOfTwo);
//                    keyForListOfThree = integerKey;
//                }
//            }
//            else if (fullHouseMap.get(integerKey).size() == 2) {
//                keyForListOfTwo = Integer.max(keyForListOfTwo, integerKey);
//            }
//        }
//
//        clearList();
//
//        if (fullHouseMap.get(keyForListOfTwo).size() > 2) {
//            fullHouseMap.get(keyForListOfTwo).removeLast();
//        }
//
//        mergeLists(fullHouseMap.get(keyForListOfThree), fullHouseMap.get(keyForListOfTwo));
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Full House", this.mergedList));
//    }
//
//    public boolean checkFlush() {
//        clearList();
//
//        Map<String, List<Card>> cardsMap = new HashMap<>();
//        cardsMap.put("Clubs", new ArrayList<>());
//        cardsMap.put("Spades", new ArrayList<>());
//        cardsMap.put("Hearts", new ArrayList<>());
//        cardsMap.put("Diamonds", new ArrayList<>());
//
//        mergeLists(player.getHand(), dealersHand);
//        String keyLongestList = "";
//        int countLongestList = 0;
//
//        for (Card card : this.mergedList) {
//            cardsMap.get(card.getCardSuit()).add(card);
//
//            if (card.getCardSuit().equals(keyLongestList)) {
//                countLongestList++;
//            }
//            else {
//                countLongestList--;
//            }
//
//            if (countLongestList == 0) {
//                keyLongestList = card.getCardSuit();
//            }
//        }
//
//        if (cardsMap.get(keyLongestList).size() < 5) {
//            return false;
//        }
//
//
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Flush", cardsMap.get(keyLongestList)));
//    }
//
//    public boolean checkQuad() {
//        clearList();
//
//        for (Card card : this.player.getPokerHand().getHandCards()) {
//            if (card.equals(this.dealersHand.getLast())) {
//                this.mergedList.add(card);
//            }
//        }
//
//        if(this.mergedList.size() != 3){
//            return false;
//        }
//
//        // 3 cards must equal the last card in the dealers hand in order to create a quad
//        this.mergedList.add(this.dealersHand.getLast());
//
//        // Removes every potential hand except ones that can beat a quad
//        for(String potentialHandString: this.player.getPossibleHands()){
//
//            if(!potentialHandString.equals("Royal Flush") &&
//                    !potentialHandString.equals("Straight Flush")){
//
//                player.removePossibleHand(potentialHandString);
//            }
//        }
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Quad", this.mergedList));
//    }
//
//    public boolean checkTriple() {
//        clearList();
//
//        // Gets the card in a pair to check for triple
//        Card pairCard = this.player.getPokerHand().getHandCards().getFirst();
//
//        if (!pairCard.equals(this.dealersHand.getLast())) {
//            return false;
//        }
//
//        this.mergedList.addAll(this.player.getHand());
//        this.mergedList.add(this.dealersHand.getLast());
//        this.player.removePossibleHands(Arrays.asList("Triple", "Two Pair"));
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Triple", this.mergedList));
//    }
//
//    public boolean checkTwoPair() {
//        clearList();
//
//        Card pairCard = this.player.getPokerHand().getHandCards().getFirst();
//        Card notPairCard;
//
//        if (pairCard.equals(this.player.getHand().getFirst())) {
//            notPairCard = this.dealersHand.getLast();
//        }
//        else {
//            notPairCard = this.dealersHand.getFirst();
//        }
//
//        if (!notPairCard.equals(this.dealersHand.getLast())) {
//            return false;
//        }
//
//        this.mergedList.addAll(this.player.getPokerHand().getHandCards());
//        this.mergedList.add(notPairCard);
//        this.player.removePossibleHand("Two Pair");
//
//        return this.player.setPokerHand(
//                this.connectorManager.sendForHand(
//                        "Two Pair", this.mergedList));
//    }
//
//    public boolean checkSinglePair() {
//
//        // Only check the last card in dealersHand for a single pair instead of every card
//        for(Card card : this.player.getHand()){
//            if(card.equals(this.dealersHand.getLast())){
//                this.player.removePossibleHand("One Pair");
//                return this.player.setPokerHand(
//                        this.connectorManager.sendForHand(
//                                "One Pair", Arrays.asList(this.dealersHand.getLast(), card)));
//            }
//        }
//        return false;
//    }
//
//    public void clearList(){
//        this.mergedList.clear();
//    }
//
//    private boolean checkCardInPlayerHand() {
//        boolean firstCardFound = false;
//        boolean secondCardFound = false;
//
//        for (Card card : this.mergedList) {
//            if (card.equals(this.player.getHand().getFirst())) {
//                firstCardFound = true;
//            }
//            else if (card.equals(this.player.getHand().getLast())) {
//                secondCardFound = true;
//            }
//        }
//
//        return firstCardFound || secondCardFound;
//    }
//
//    private void mergeLists(List<Card> list1, List<Card> list2){
//        this.mergedList.addAll(list1);
//        this.mergedList.addAll(list2);
//    }
//}
