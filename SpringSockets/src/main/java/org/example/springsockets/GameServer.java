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
    private List<Player> foldedPlayers;

    private List<Player> waitingPlayers;
    private final HandIdentifierManager handIdentifierManager;
    private final HandConnectorManager handConnectorManager;
    private final HandIdentifierDistribute handIdentifierDistribute;
    private GameState gameState;
    private final Deck deck;
    private int currentPlayerIndex;

    public GameServer(){
        this.handConnectorManager = new HandConnectorManager();
        this.handIdentifierDistribute = new HandIdentifierDistribute(this.handConnectorManager);
        this.handIdentifierManager = new HandIdentifierManager(this.handConnectorManager, this.handIdentifierDistribute);
        this.dealerCards = new ArrayList<>();
        this.players = new ArrayList<>();
        this.foldedPlayers = new ArrayList<>();
        this.waitingPlayers = new ArrayList<>();
        this.gameState = GameState.WAITING;
        this.deck = new Deck();
        this.deck.shuffle();
    }

    /**
     * Adds player to waiting players and will add them after someone wins
     * @param player Player object to add to the list of waiting players
     */
    public String addPlayer(Player player){
        if ( gameState.equals( GameState.IN_PROGRESS ) ) {
            this.waitingPlayers.add(player);
            return "Game in progress. Waiting for winner.";
        }
        else {
            this.players.add(player);
        }

        return "Successfully joined the game.";
    }

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

    public GameState getGameState() {
        return gameState;
    }

    public List<Card> getDealerCards() { return dealerCards; }

    private void distributeCards() {

        // Deals two cards per player
        for ( Player player : this.players ) {
            player.getHand().add( this.deck.dealCard() );
            player.getHand().add( this.deck.dealCard() );
        }
    }

    public int playerCount() {
        return this.players.size();
    }

    public void newRound() {
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

    public void playerBet( int amount ) {
        players.get( this.currentPlayerIndex ).bet( amount );
        checkPlayerHand(players.get( this.currentPlayerIndex ));
    }

    public void playerFold() {
        Player currentPlayer = this.players.remove( this.currentPlayerIndex );
        this.deck.addCards( currentPlayer.fold() );
        this.foldedPlayers.add( currentPlayer );
    }

    public void playerCheck() {
        checkPlayerHand(players.get( this.currentPlayerIndex ));
    }

    public void nextPlayer() {
        this.currentPlayerIndex = ( this.currentPlayerIndex + 1 ) % MAX_PLAYERS;
    }

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

    public void playerLeft( Player player) {
        this.players.remove( player );
    }

    private void addWaitingPlayers() {
        while ( this.players.size() < MAX_PLAYERS || !this.waitingPlayers.isEmpty()) {
            this.players.add( this.waitingPlayers.remove( 0 ) );
        }
    }

    public void restart() {
        this.deck.addCards( this.dealerCards );
        this.dealerCards.clear();
        this.foldedPlayers.clear();
        this.gameState = GameState.WAITING;
        addWaitingPlayers();
        removeCardsFromPlayers();
    }

    private void removeCardsFromPlayers() {
        for ( Player player : this.players ) {
            this.deck.addCards( player.fold() ); // Removes the player's cards and possible hands
        }
    }

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
