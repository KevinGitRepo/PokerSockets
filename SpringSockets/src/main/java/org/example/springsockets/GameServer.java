package org.example.springsockets;

import org.example.General.*;
import org.example.HandConnector.HandConnectorManager;
import org.example.HandIdentifier.HandIdentifierDistribute;
import org.example.HandIdentifier.HandIdentifierManager;

import java.util.*;

class GameServer{

    private static final int MAX_PLAYERS = 4;

    private List<Card> dealerCards;
    private int pot;
    private List<Player> players;
    private Set<Player> foldedPlayers;

    private List<Player> waitingPlayers;
    private final HandIdentifierManager handIdentifierManager;
    private final HandConnectorManager handConnectorManager;
    private final HandIdentifierDistribute handIdentifierDistribute;
    private GameState gameState;
    private final Deck deck;
    private int currentPlayerIndex;
    private boolean roundFold;
    private int roundBetAmount;
    private int roundReRaisedIndex;

    /**
     * The main class for controlling game logic and information distribution
     */
    public GameServer(){
        this.handConnectorManager = new HandConnectorManager();
        this.handIdentifierDistribute = new HandIdentifierDistribute( this.handConnectorManager );
        this.handIdentifierManager = new HandIdentifierManager( this.handConnectorManager, this.handIdentifierDistribute );
        this.dealerCards = new ArrayList<>();
        this.players = new ArrayList<>();
        this.foldedPlayers = new HashSet<>();
        this.waitingPlayers = new ArrayList<>();
        this.gameState = GameState.WAITING;
        this.roundFold = false;
        this.deck = new Deck();
        this.deck.shuffle();
        this.roundBetAmount = 0;
        this.roundReRaisedIndex = -1;
    }

    /**
     * Adds player to waiting players and will add them after someone wins
     * @param player Player object to add to the list of waiting players
     */
    public String addPlayer( Player player ){

        // Only adds player to current game if there is enough room
        if ( gameState.equals( GameState.IN_PROGRESS ) ) {
            this.waitingPlayers.add( player );
            return "Game in progress. Waiting for winner.";
        }
        else {
            this.players.add( player );
        }

        return "Successfully joined the game.";
    }

    /**
     * Will check if the game can start or is in progress
     * @return true if the game is in progress or just started; false otherwise
     */
    public boolean gameStart() {
        if ( gameState.equals( GameState.IN_PROGRESS ) ) {
            return true;
        }

        if ( this.players.size() == MAX_PLAYERS ) {
            distributeCards();
            this.gameState = GameState.IN_PROGRESS;
            this.currentPlayerIndex = 0;
            return true;
        }

        return false;
    }

    /**
     * Gets the state of the current game
     * @return GameState object
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Gets the cards in the dealer's hand
     * @return List<Card> object of the dealer's cards
     */
    public List<Card> getDealerCards() { return dealerCards; }

    /**
     * Deals two cards to each player
     */
    private void distributeCards() {

        // Deals two cards per player
        for ( Player player : this.players ) {
            player.getHand().add( this.deck.dealCard() );
            player.getHand().add( this.deck.dealCard() );
        }
    }

    /**
     * Gets the money that is currently in the pot
     * @return int total pot amount
     */
    public int getPot() {
        return this.pot;
    }

    /**
     * Gets the amount of players currently in play
     * @return int amount of players in the game
     */
    public int playerCount() {
        return this.players.size();
    }

    /**
     * Adds cards to the dealer's hand to indicate the next round
     */
    public void newRound() {
        this.roundFold = false;
        if ( this.dealerCards.isEmpty() ) {
            // Flop
            this.dealerCards.addAll( Arrays.asList( this.deck.dealCard(),
                                        this.deck.dealCard(), this.deck.dealCard() ) );
        }
        else {
            // Turn and River
            this.dealerCards.add( this.deck.dealCard() );
        }
    }

    /**
     * Gets the bet amount for the current round in case of re-raises
     * @return int of current bet amount for the round
     */
    public int getRoundBetAmount() {
        return this.roundBetAmount;
    }

    /**
     * Handles when a player makes a bet
     * @param amount money that the player will bet
     */
    public void playerBet( int amount ) {
        this.pot += amount;
        this.roundBetAmount = amount;
        players.get( this.currentPlayerIndex ).bet( amount );
        checkPlayerHand( players.get( this.currentPlayerIndex ));
    }

    /**
     * Sets the index of the player who re-raised during a round
     */
    public void setRoundReRaisedIndex() {
        this.roundReRaisedIndex = this.currentPlayerIndex;
    }

    /**
     * Handles when a player folds
     */
    public void playerFold() {
        this.roundFold = true;
        this.deck.addCards( this.players.get( this.currentPlayerIndex ).fold() );
        this.foldedPlayers.add( this.players.get( this.currentPlayerIndex ) );
    }

    /**
     * Handles when a player checks
     */
    public void playerCheck() {
        checkPlayerHand(players.get( this.currentPlayerIndex ));
    }

    /**
     * Handles the next active player
     * Will take into consideration folded players, re-raises, and players unable to play
     */
    public void nextPlayer() {

        // Has to increase the player index before checking loop condition
        do {
            this.currentPlayerIndex = ( this.currentPlayerIndex + 1 ) % MAX_PLAYERS;
            if ( !this.players.get( this.currentPlayerIndex ).isAbleToPlay( this.roundBetAmount ) &&
                    this.currentPlayerIndex != this.roundReRaisedIndex ) {
                this.foldedPlayers.add( this.players.get( this.currentPlayerIndex ) );
            }
        } while (this.foldedPlayers.size() < MAX_PLAYERS &&
                this.foldedPlayers.contains(this.players.get(this.currentPlayerIndex)));
    }

    /**
     * Gets the current player
     * @return Player object of the current player
     */
    public Player getCurrentPlayer() {
        return this.players.get( this.currentPlayerIndex );
    }

    /**
     * Uses a hashmap of objects to check to see if the player currently has a certain hand.
     * This will only check the possible hands given the amount of cards in play
     * @param player who will have their cards checked
     */
    private void checkPlayerHand(Player player){
        this.handIdentifierManager.checkHand(player, this.dealerCards);
    }

    /**
     * Handles when a player leaves the game
     * @param player who has left the game
     */
    public void playerLeft( Player player) {
        this.deck.addCards( player.fold() );
        this.players.remove( player );
    }

    /**
     * Adds any waiting players to the current game if space is provided
     */
    private void addWaitingPlayers() {
        while ( this.players.size() < MAX_PLAYERS || !this.waitingPlayers.isEmpty()) {
            this.players.add( this.waitingPlayers.remove( 0 ) );
        }
    }

    /**
     * Handles a restart of a game
     * Only ran once a winner has been decided
     */
    public void restart() {
        this.deck.addCards( this.dealerCards );
        this.dealerCards.clear();
        this.foldedPlayers.clear();
        this.gameState = GameState.WAITING;
        this.currentPlayerIndex = 0;
        this.pot = 0;
        addWaitingPlayers();
        removeCardsFromPlayers();
    }

    /**
     * Removes all cards from each active player
     */
    private void removeCardsFromPlayers() {
        for ( Player player : this.players ) {
            this.deck.addCards( player.fold() ); // Removes the player's cards and possible hands
        }
    }

    /**
     * Decides on a winner based on card value, poker hand value, or last player left
     * @return Player object of the winner
     */
    public Player getWinner() {
        Player winner = null;
        for ( Player player : this.players ) {
            if ( winner == null ) {
                winner = player;
            }
            else if ( winner.getPokerHand() == null && player.getPokerHand() == null ) {
                winner = winner.getHighCardValue() < player.getHighCardValue() ? player : winner;
            }
            else if ( player.higherValueThan( winner.getPokerHand() ) ) {
                winner = player;
            }
        }

        if ( winner != null ) {
            winner.win(this.pot);
        }

        return winner;
    }

    /*
    Poker Hands Ranked in order:

    Royal Flush
    Straight Flush
    Quad
    Full House
    Flush
    Straight
    Triple
    Two Pair
    Single Pair
    High Card
     */
}
