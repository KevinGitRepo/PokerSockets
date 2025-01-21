package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game{

    private List<Card> dealerCards;
    private int pot;
    private List<Player> players;
    private Scanner scan;

    // Main game function
    public void start(){
        this.dealerCards = new ArrayList<>();
        this.players = new ArrayList<Player>();
        this.scan = new Scanner(System.in);

        Deck deck = new Deck();

        System.out.println("Hello.\nWelcome to Poker.\nPlease enter the amount of players that would like to play.");
        int numOfPlayers = this.scan.nextInt();
        for (int i = 0; i < numOfPlayers; i++) {
            this.players.add(new Player("Player " + (i + 1), 2000));
        }
        while(true){
            // Checks if any player wants to leave
            dealWithLeavingPlayers();

            // Makes sure there are players in game
            if(this.players.isEmpty()){
                break;
            }

            // Shuffles the deck
            deck.shuffle();

            // Distribute cards to each player
            for(int i = 1 ; i < this.players.size() * 2 ; i++){
                this.players.get(Math.abs(this.players.size() - i)).receiveCard(deck.dealCard());
            }
            // Player in slot 0 needs to separately receive second card due to for loop
            this.players.getFirst().receiveCard(deck.dealCard());

            List<Player> remainingPlayers = new ArrayList<>(this.players);

            // Main Game Loop
            while(true){

                List<Player> checkPlayers = new ArrayList<>();

                int currentBet = 0;

                // Round Game Loop
                for(Player player : remainingPlayers){
                    // Removes any player without sufficient balance to play
                    if(!player.isAbleToPlay()){
                        remainingPlayers.remove(player);
                        continue;
                    }
                    System.out.println(player + "'s turn.\nYou have: " + player.getHand() + "\nPlease choose to fold (f), bet (b), or check (c).");
                    String decision = this.scan.nextLine();
                    switch (decision) {
                        // Fold case
                        case "f" -> {
                            player.fold();
                            remainingPlayers.remove(player);
                        }
                        // Bet case
                        case "b" -> {
                            System.out.println("Please enter the amount which you would like to bet. Current balance: " + player.getMoneyLimit() + " Current bet: " + currentBet + " Pot: " + pot);
                            int bet = this.scan.nextInt();
                            this.scan.nextLine();
                            if (bet < player.getMoneyLimit() && bet >= currentBet) {
                                currentBet = bet;
                                player.bet(bet);
                                this.pot += bet;
                            }
                        }
                        // Check case
                        case "c" -> {
                            continue;
                        }
                    }
                }

                // Need to check if the game needs to be reset or if it can continue
                if(checkForReset(remainingPlayers)){reset();break;}

                this.dealerCards.add(deck.dealCard());
                System.out.println("Dealer has: " + String.join(", ", this.dealerCards.toString()));
            }
        }
        System.out.println("Thanks for playing!");
    }

    /**
     * Prompts the user for any person leaving and removes them from the players list
     */
    private void dealWithLeavingPlayers(){
        while(!this.players.isEmpty()){
            System.out.println(this.players);
            System.out.println("Enter the player number if player wants to leave (0 to continue).");
            int playerNum = this.scan.nextInt();
            this.scan.nextLine();
            if(playerNum == 0){
                break;
            }
            if(playerNum < this.players.size()){
                this.players.remove(playerNum);
            }
        }
    }

    /**
     * Checks if the game needs a reset meaning that someone has won the game or no more players remain
     * @param remainingPlayers list of all remaining players
     * @return true if reset is required, false otherwise
     */
    private boolean checkForReset(List<Player> remainingPlayers){
        // No players remain
        if(remainingPlayers.isEmpty()){
            return true;
        }
        // Only one person remains
        else if(remainingPlayers.size() == 1) {
            remainingPlayers.getFirst().win(this.pot);
            System.out.println(remainingPlayers.getFirst() + " won!");
            return true;
        }
        // The dealer has 5 cards and a winner needs to be decided
        if(this.dealerCards.size() == 5){
            int indexOfWinner = 0;
            for(Player player : this.players){
                // Compares each player's card worth amount to determine winner
                if(this.players.get(indexOfWinner).getPokerHand().amountWorth() < player.getPokerHand().amountWorth()){
                    indexOfWinner = this.players.indexOf(player);
                }
            }
            this.players.get(indexOfWinner).win(this.pot);
            System.out.println(this.players.get(indexOfWinner) + " won!");
            return true;
        }

        return false;
    }

    /**
     * Resets the dealer and the pot for the next game to start
     */
    public void reset(){
        this.dealerCards.clear();
        this.pot = 0;
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }
}