package org.example.General;

import org.example.Hands.PokerHand;
import org.example.Hands.TwoPair;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

public class Player {
    private final String name;
    private int moneyLimit;
    private WebSocketSession session;
    private List<Card> hand;
    private PokerHand pokerHand;
    private Set<PokerHandTypes> possibleHands;
    private Set<PokerHandTypes> notPossibleHands;

    public Player(String name, int moneyLimit, WebSocketSession session) {
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.session = session;
        this.hand = new ArrayList<>();
        this.possibleHands = new TreeSet<>();
        this.notPossibleHands = new HashSet<>();
        populatePossibleHands();
    }

    public WebSocketSession getSession() { return session; }

    private void populatePossibleHands() {
        possibleHands.addAll(Arrays.asList(PokerHandTypes.values()));
    }

    public String getName(){ return this.name; }

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
        Set<PokerHandTypes> difference = new TreeSet<>(this.possibleHands);
        difference.removeAll(this.notPossibleHands);
        return difference;
    }
    
    public List<Card> fold(){
        List<Card> returnHand = this.hand;
        this.hand.clear();
        this.notPossibleHands.clear();
        return returnHand;
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

    /**
     * For the player to bet a certain amount of money
     * @param amount number to bet
     */
    public void bet(int amount){
        this.moneyLimit -= amount;
    }

    public boolean higherValueThan( PokerHand comparingHand ) {
        if ( comparingHand == null ) return true;

        if ( this.pokerHand == null ) return false;

        return comparingHand.amountWorth() < this.pokerHand.amountWorth();
    }

    public Card getHighCard() {
        return hand.get( 0 ).getCardValue() > hand.get( 1 ).getCardValue() ? hand.get( 0 ) : hand.get( 1 );
    }

    public int getHighCardValue() {
        return Math.max( hand.get( 0 ).getCardValue(), hand.get( 1 ).getCardValue() );
    }

    @Override
    public String toString(){
        return this.name;
    }
}

