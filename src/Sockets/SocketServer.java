package Sockets;

import main.Card;
import main.Deck;
import main.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private static final List<Player> players = new ArrayList<>();
    private static final Deck deck = new Deck();
    private static int currentPlayerInt = 0;
    private static List<Card> dealersCards = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Poker Server started. Listening on port 5000...");

            while (players.size() < 1) {
                System.out.println("Waiting for players...");
                Socket clientSocket = serverSocket.accept();
                Player newPlayer = new Player("Player 1", 1000, clientSocket);
                players.add(newPlayer);
//                new Thread(new PlayerHandler(newPlayer)).start();
                System.out.println("New player created." + newPlayer);
            }

            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startGame() throws IOException {
        deck.shuffle();
        for (Player player : players) {
            player.receiveCard(deck.dealCard());
            player.receiveCard(deck.dealCard());
            player.sendMessage("Welcome to Poker! Your hand: " + player.getHand());
        }

        List<Player> remainingPlayers = new ArrayList<>(players);

        while (true) {
            Player currentPlayer = players.get(currentPlayerInt);

            if (!currentPlayer.isAbleToPlay()) {
                remainingPlayers.remove(currentPlayer);
                continue;
            }

            currentPlayer.sendMessage("Your Turn! Dealer Cards: " + dealersCards);

            String action = currentPlayer.receiveMessage();
            handlePlayerAction(currentPlayer, action);

            currentPlayerInt = (currentPlayerInt + 1) % players.size();

            if ( ( dealersCards.size() == 5 || remainingPlayers.size() == 2 ) && currentPlayerInt == 0 ) {
                broadcastMessage(findWinner());
                break;
            }

            addCardsToDealer();
        }
    }

    private static String findWinner() {
        return "WON!";
    }

    public static void broadcastMessage(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    private static void addCardsToDealer(){
        System.out.println("Adding cards to dealer...");
        if (currentPlayerInt == 0) {
            dealersCards.add(deck.dealCard());
        }
    }

//    private static String getGameState() {
//        StringBuilder cardsString = new StringBuilder();
//        for (Card card : dealersCards) {
//            cardsString.append(card.toString()).append(" ");
//        }
//        return cardsString.toString();
//    }

    private static void handlePlayerAction(Player player, String action) {
        System.out.println("Handling action: " + action);
        switch (action.toLowerCase()) {
            case "bet":
                // Add ability to bet as much as you want
                player.bet(20);
                break;
            case "fold":
                player.fold();
                break;
            case "check":
                break;
        }
    }

//    static class PlayerHandler implements Runnable {
//        private Player player;
//
//        public PlayerHandler(Player player) {
//            this.player = player;
//        }
//
//        @Override
//        public void run() {
//            try {
//                while (true) {
//                    String message = player.receiveMessage();
//                    System.out.println("Action received from " + player.getName() + ": " + message);
//                    handlePlayerAction(player, message);
//                }
//            } catch ( IOException e ) {
//                e.printStackTrace();
//            }
//        }
//    }
}
