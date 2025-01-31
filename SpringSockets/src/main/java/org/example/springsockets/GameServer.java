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
            return false;
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

    // Main game function
    public static void start(){















//        while(true){
//            // Checks if any player wants to leave
//            dealWithLeavingPlayers();
//
//            // Makes sure there are players in game
//            if(this.players.isEmpty()){
//                break;
//            }
//
//            // Shuffles the deck
//            deck.shuffle();
//
//            // Distribute cards to each player
//            for(int i = 1 ; i < this.players.size() * 2 ; i++){
//                this.players.get(Math.abs(this.players.size() - i)).receiveCard(deck.dealCard());
//            }
//            // Player in slot 0 needs to separately receive second card due to for loop
//            this.players.getFirst().receiveCard(deck.dealCard());
//
//            List<Player> remainingPlayers = new ArrayList<>(this.players);
//
//            // Main Game Loop
//            while(true){
//
//                List<Player> checkPlayers = new ArrayList<>();
//
//                int currentBet = 0;
//
//                // Round Game Loop
//                for(Player player : this.players){
//
//                    // Continues if player is not a remaining player (Need to check for folding cases)
//                    if(!remainingPlayers.contains(player)){
//                        continue;
//                    }
//
//                    // Removes any player without sufficient balance to play
//                    if(!player.isAbleToPlay() && remainingPlayers.contains(player)){
//                        remainingPlayers.remove(player);
//                        continue;
//                    }
//                    System.out.println(player + "'s turn.\nYou have: " + player.getHand() + "\nPlease choose to fold (f), bet (b), or check (c).");
//                    String decision = this.scan.nextLine();
//                    switch (decision) {
//                        // Fold case
//                        case "f" -> {
//                            player.fold();
//                            remainingPlayers.remove(player);
//                        }
//                        // Bet case
//                        case "b" -> {
//                            System.out.println("Please enter the amount which you would like to bet. Current balance: " + player.getMoneyLimit() + " Current bet: " + currentBet + " Pot: " + pot);
//                            int bet = this.scan.nextInt();
//                            this.scan.nextLine();
//                            if (bet < player.getMoneyLimit() && bet >= currentBet) {
//                                currentBet = bet;
//                                player.bet(bet);
//                                this.pot += bet;
//                            }
//                        }
//                        // Check case
//                        case "c" -> {
//                            if(currentBet > 0){
//                                System.out.println("You need to bet at least $" + currentBet);
//                            }
//                            continue;
//                        }
//                    }
//                    // Checks the players hand to identify which type they have
//                    checkPlayerHand(player);
//                }
//
//                // Need to check if the game needs to be reset or if it can continue
//                if(checkForReset(remainingPlayers)){reset();break;}
//
//                this.dealerCards.add(deck.dealCard());
//                System.out.println("Dealer has: " + String.join(", ", this.dealerCards.toString()));
//            }
//        }
        System.out.println("Thanks for playing!");
    }


//
//    /**
//     * Prompts the user for any person leaving and removes them from the players list
//     */
//    private void dealWithLeavingPlayers(){
//        while(!this.players.isEmpty()){
//            System.out.println(this.players);
//            System.out.println("Enter the player number if player wants to leave (0 to continue).");
//            int playerNum = this.scan.nextInt();
//            this.scan.nextLine();
//            if(playerNum == 0){
//                break;
//            }
//            if(playerNum < this.players.size()){
//                this.players.remove(playerNum);
//            }
//        }
//    }
//
//    /**
//     * Checks if the game needs a reset meaning that someone has won the game or no more players remain
//     * @param remainingPlayers list of all remaining players
//     * @return true if reset is required, false otherwise
//     */
//    private boolean checkForReset(List<Player> remainingPlayers){
//        // No players remain
//        if(remainingPlayers.isEmpty()){
//            return true;
//        }
//        // Only one person remains
//        else if(remainingPlayers.size() == 1) {
//            remainingPlayers.getFirst().win(this.pot);
//            System.out.println(remainingPlayers.getFirst() + " won!");
//            return true;
//        }
//        // The dealer has 5 cards and a winner needs to be decided
//        if(this.dealerCards.size() == 5){
//            int indexOfWinner = 0;
//            for(Player player : this.players){
//                // Compares each player's card worth amount to determine winner
//                if(this.players.get(indexOfWinner).getPokerHand().amountWorth() < player.getPokerHand().amountWorth()){
//                    indexOfWinner = this.players.indexOf(player);
//                }
//            }
//            this.players.get(indexOfWinner).win(this.pot);
//            System.out.println(this.players.get(indexOfWinner) + " won!");
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * Resets the dealer and the pot for the next game to start
//     */
//    public void reset(){
//        this.dealerCards.clear();
//        this.pot = 0;
//    }

//    public static void main(String[] args){
//        Game game = new Game();
//        game.start();
//    }

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
