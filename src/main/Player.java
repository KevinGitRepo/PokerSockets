package main;

import Hands.PokerHand;
import Hands.TwoPair;

import java.util.*;

public class Player {
    private final String name;
    private int moneyLimit;
    private List<Card> hand;
    private boolean isPair;
    private PokerHand pokerHand;
    private Set<PokerHandTypes> possibleHands;
    private Set<PokerHandTypes> notPossibleHands;

    public Player(String name, int moneyLimit){
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.hand = new ArrayList<>();
        this.isPair = false;
        this.possibleHands = EnumSet.allOf(PokerHandTypes.class);
        this.notPossibleHands = new HashSet<>();
    }

    public String getName(){ return this.name; }

    public void addPossibleHands(List<PokerHandTypes> possibleHands){
        this.possibleHands.addAll(possibleHands);
    }

    public void removePossibleHand(PokerHandTypes possibleHand){
        this.notPossibleHands.add(possibleHand);
    }

    public void removePossibleHands(List<PokerHandTypes> possibleHands) {
        this.notPossibleHands.addAll(possibleHands);
    }

    public boolean isHandPossible(PokerHandTypes possibleHand) {
        return !this.notPossibleHands.contains(possibleHand);
    }

    public Set<PokerHandTypes> getPossibleHands() {
        Set<PokerHandTypes> difference = new HashSet<>(this.possibleHands);
        difference.removeAll(this.notPossibleHands);
        return difference;
    }
    
    public void fold(){
        this.hand.clear();
        this.possibleHands.clear();
    }
    
    public void win(int amount){ this.moneyLimit += amount; }
    
    public boolean isAbleToPlay(){ return this.moneyLimit > 0 || this.hand.isEmpty(); }

    public List<Card> getHand(){ return this.hand; }

    public int getMoneyLimit(){ return this.moneyLimit; }

    public PokerHand getPokerHand(){ return this.pokerHand; }

    public boolean setPokerHand(PokerHand hand){
        this.pokerHand = hand;
        return true;
    }

    public boolean receiveCard(Card card){
        if(this.hand.size() >= 2){ return false; }
        if(this.hand.size() == 1){ this.isPair = ( card.equals(this.hand.getFirst()) ); }
        if(this.isPair) { this.pokerHand = new TwoPair(this.hand);}
        return this.hand.add(card);
    }

    public void resetPlayer(){
        this.hand.clear();
        this.isPair = false;
    }

    public int bet(int amount){
        this.moneyLimit -= amount;
        return this.moneyLimit;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
