package main;

import Hands.PokerHand;
import Hands.TwoPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final String name;
    private int moneyLimit;
    private List<Card> hand;
    private boolean isPair;
    private PokerHand pokerHand;
    private List<String> possibleHands;

    public Player(String name, int moneyLimit){
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.hand = new ArrayList<>();
        this.isPair = false;
        this.possibleHands = new ArrayList<>();
    }

    public String getName(){ return this.name; }

    public void addPossibleHands(List<String> possibleHands){
        this.possibleHands.addAll(possibleHands);
    }

    public void removePossibleHand(String possibleHand){
        this.possibleHands.remove(possibleHand);
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

    public boolean receiveCard(Card card){
        if(this.hand.size() >= 2){ return false; }
        if(this.hand.size() == 1){ this.isPair = ( card.equals(this.hand.get(0)) ); }
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
