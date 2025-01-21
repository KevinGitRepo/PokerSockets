package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game{

    private List<Card> dealerCards;
    private int pot;

    public void start(){
        this.dealerCards = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        List<Player> players = new ArrayList<Player>();
        Deck deck = new Deck();

        System.out.println("Hello.\nWelcome to Poker.\nPlease enter the amount of players that would like to play.");
        int numOfPlayers = scan.nextInt();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1), 2000));
        }
        while(true){
            while(!players.isEmpty()){
                System.out.println(players);
                System.out.println("Enter the player number if player wants to leave (0 to continue).");
                int playerNum = scan.nextInt();
                scan.nextLine();
                if(playerNum == 0){
                    break;
                }
                if(playerNum < players.size()){
                    players.remove(playerNum);
                }
            }

            deck.shuffle();

            if(players.isEmpty()){
                break;
            }

            for(int i = 1 ; i < players.size() * 2 ; i++){
                players.get(Math.abs(players.size() - i)).receiveCard(deck.dealCard());
            }
            players.getFirst().receiveCard(deck.dealCard());

            while(true){
                List<Player> remainingPlayers = new ArrayList<>(players);
                int currentBet = 0;
                for(Player player : players){
                    if(!player.isAbleToPlay() && remainingPlayers.contains(player)){
                        remainingPlayers.remove(player);
                        continue;
                    }
                    System.out.println(player + "'s turn.\nYou have: " + player.getHand() + "\nPlease choose to fold (f), bet (b), or check (c).");
                    String decision = scan.nextLine();
                    switch (decision) {
                        case "f" -> {
                            player.fold();
                            remainingPlayers.remove(player);
                        }
                        case "b" -> {
                            System.out.println("Please enter the amount which you would like to bet. Current bet: " + currentBet + " Pot: " + pot);
                            int bet = scan.nextInt();
                            scan.nextLine();
                            if (bet < player.getMoneyLimit() && bet >= currentBet) {
                                currentBet = bet;
                                player.bet(bet);
                                pot += bet;
                            }
                        }
                        case "c" -> {
                            continue;
                        }
                    }
                }
                if(remainingPlayers.isEmpty()){
                    reset();
                    break;
                }
                if(remainingPlayers.size() == 1) {
                    remainingPlayers.getFirst().win(pot);
                    reset();
                    break;
                }

                if(this.dealerCards.size() == 5){
                    int indexOfWinner = 0;
                    for(Player player : players){
                        if(players.get(indexOfWinner).getPokerHand().amountWorth() < player.getPokerHand().amountWorth()){
                            indexOfWinner = players.indexOf(player);
                        }
                    }
                    players.get(indexOfWinner).win(pot);
                    reset();
                    break;
                }

                dealerCards.add(deck.dealCard());
                System.out.println("Dealer has: " + String.join(", ", dealerCards.toString()));
            }
        }
        System.out.println("Thanks for playing!");
    }

    public void reset(){
        this.dealerCards.clear();
        this.pot = 0;
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }
}