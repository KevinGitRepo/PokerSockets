package org.example.General;

import org.example.Hands.PokerHand;
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

    public Player( String name, int moneyLimit, WebSocketSession session ) {
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.session = session;
        this.hand = new ArrayList<>();
        this.possibleHands = new TreeSet<>();
        this.notPossibleHands = new HashSet<>();
        populatePossibleHands();
    }

    /**
     * Gets the web socket session of the player
     * @return WebSocketSession object
     */
    public WebSocketSession getSession() { return session; }

    /**
     * Adds a list of possible hands to the possible hands set
     */
    private void populatePossibleHands() {
        possibleHands.addAll( Arrays.asList( PokerHandTypes.values() ) );
    }

    /**
     * Gets the name of the player
     * @return String name of player
     */
    public String getName() { return this.name; }

    /**
     * "Removes" a possible hand from the possible hands set
     * Adds to the not possible hands set instead of physically removing from the set
     * @param possibleHand PokerHandTypes object
     */
    public void removePossibleHand( PokerHandTypes possibleHand ) {
        this.notPossibleHands.add( possibleHand );
    }

    /**
     * "Removes" possible hands from the possible hands set
     * Adds a list of possible hands to the not possible hands set instead of physically removing from the set
     * @param possibleHands list of PokerHandTypes objects
     */
    public void removePossibleHands( List<PokerHandTypes> possibleHands ) {
        this.notPossibleHands.addAll( possibleHands );
    }

    /**
     * Checks if a hand is possible
     * Checks if a possible hand is inside the not possible hand set
     * @param possibleHand PokerHandTypes object for the possible hand to check
     * @return true if the hand is possible; false otherwise
     */
    public boolean isHandPossible( PokerHandTypes possibleHand ) {
        return !this.notPossibleHands.contains( possibleHand );
    }

    /**
     * Gets the set of all remaining possible hands for the player
     * @return the set of remaining possible hands
     */
    public Set<PokerHandTypes> getPossibleHands() {
        Set<PokerHandTypes> difference = new TreeSet<>( this.possibleHands );
        difference.removeAll( this.notPossibleHands );
        return difference;
    }

    /**
     * Clears the player's hand and the not possible hands set to get ready for the next game
     * @return list of cards from the player's hand
     */
    public List<Card> fold() {
        // Temp list to clear the player's hand then return the temp list
        List<Card> returnHand = this.hand;
        this.hand.clear();
        this.notPossibleHands.clear();
        return returnHand;
    }

    /**
     * Adds the winning amount of money to the player's money limit
     * @param amount int of winnings
     */
    public void win( int amount ) { this.moneyLimit += amount; }

    /**
     * Checks if the player is still able to play in the game
     * @param betAmount amount of money the player would have to bet
     * @return true if the player is still able to play; false otherwise
     */
    public boolean isAbleToPlay( int betAmount ) { return !this.hand.isEmpty() && betAmount <= this.moneyLimit; }

    /**
     * Gets the player's hand
     * @return a list of cards
     */
    public List<Card> getHand() { return this.hand; }

    /**
     * Gets the player's money limit
     * @return int of player's money limit
     */
    public int getMoneyLimit() { return this.moneyLimit; }

    /**
     * Gets the player's poker hand classification
     * @return player's poker hand
     */
    public PokerHand getPokerHand() { return this.pokerHand; }

    /**
     * Sets the player's poker hand
     * @param hand poker hand classification
     * @return true if the player's poker hand was changed
     */
    public boolean setPokerHand( PokerHand hand ) {
        this.pokerHand = hand;
        return true;
    }

    /**
     * For the player to bet a certain amount of money
     * @param amount number to bet
     */
    public void bet( int amount ){
        this.moneyLimit -= amount;
    }

    /**
     * Compares the values of this player's poker hand and another poker hand
     * @param comparingHand poker hand which will be compared
     * @return true if this player's poker hand is higher in value; false otherwise
     */
    public boolean higherValueThan( PokerHand comparingHand ) {
        if ( comparingHand == null ) return true;

        if ( this.pokerHand == null ) return false;

        return comparingHand.amountWorth() < this.pokerHand.amountWorth();
    }

    /**
     * Gets the highest card in the player's hand
     * @return highest card in player's hand
     */
    public Card getHighCard() {
        return hand.get( 0 ).getCardValue() > hand.get( 1 ).getCardValue() ? hand.get( 0 ) : hand.get( 1 );
    }

    /**
     * Gets the highest card value in player's hand
     * @return the int value of the highest card in player's hand
     */
    public int getHighCardValue() {
        return Math.max( hand.get( 0 ).getCardValue(), hand.get( 1 ).getCardValue() );
    }

    /**
     * Converts Player object to a readable format
     * @return String representation of this player
     */
    @Override
    public String toString(){
        return this.name;
    }
}

