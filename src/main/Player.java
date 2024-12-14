package main;

import Hands.PokerHand;
import Hands.TwoPair;
import java.util.ArrayList;
import java.util.List;

public class Player {
    
    private final String name;
    private int moneyLimit;
    private List<Card> hand;
    private boolean isPair;
    private PokerHand pokerHand;

    public Player(String name, int moneyLimit){
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.hand = new ArrayList<>();
        this.isPair = false;
    }

    public String getName(){ return this.name; }
    
    public List<Card> fold(){ return this.hand; }
    
    public void win(int amount){ this.moneyLimit += amount; }
    
    public boolean isAbleToPlay(){ return this.moneyLimit > 0; }

    public boolean receiveCard(Card card){
        if(this.hand.size() >= 2){ return false; }
        if(this.hand.size() == 1){ this.isPair = ( card.equals(this.hand.get(0)) ); }
        if(this.isPair) { this.pokerHand = new TwoPair(); }
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
}
