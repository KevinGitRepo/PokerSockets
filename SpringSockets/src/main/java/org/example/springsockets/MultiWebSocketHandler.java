package org.example.springsockets;

import org.example.General.Player;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiWebSocketHandler extends TextWebSocketHandler {

    private static int playerCountRound = 1;

    private final Map<WebSocketSession, Player> players = new ConcurrentHashMap<>();

    private final static GameServer gameServer = new GameServer();

    private final PlayerObjectMapper playerObjectMapper = new PlayerObjectMapper();

    private static boolean gameOver = false;

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Once the player opens a tab they connect to the server
        System.out.println("New Connection established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // This is where the actions will be parsed

        System.out.println("Message: " + message.getPayload());

        // Creates new player and adds to players map
        if ( message.getPayload().toString().contains( "create" ) ) {
            Player receivedPlayer = playerObjectMapper.readValue(message.getPayload().toString(), session);

            players.put( session, receivedPlayer );

            session.sendMessage( new TextMessage( gameServer.addPlayer( receivedPlayer ) ) );
        }

        else if ( message.getPayload().toString().contains( "bet" ) ) {
            gameServer.playerBet(30);
            broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " bet $30");
        }

        else if ( message.getPayload().toString().contains( "fold" ) ) {
            gameServer.playerFold();
            broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " folded");
        }

        else if ( message.getPayload().toString().contains( "check" ) ) {
            gameServer.playerCheck();
            broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " checked");
        }

        // Only run this if game is currently playing
        System.out.println(gameServer.getGameState());
        if ( gameServer.getGameState().equals( GameState.IN_PROGRESS ) ) {
            playGame();
        }

        if ( gameServer.gameStart() && !gameOver) {
            broadcastToAllPlayers("Game started.");
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Cards" + gameServer.getCurrentPlayer().getHand() ) );
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Turn" ) );
        }
    }

    public void sendMessageToPlayer(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }

    public void playGame() throws Exception {
        System.out.println("Game is In Progress");
        if ( gameServer.playerCount() == playerCountRound && gameServer.getDealerCards().size() == 5 ) {
            Player winner = gameServer.getWinner();
            broadcastToAllPlayers( winner.toString() + " won with hand " +
                    ( winner.getPokerHand() == null ? "high " + winner.getHighCard() : winner.getPokerHand().toString() ) );
            gameServer.restart();
            gameOver = true;
            playerCountRound = 1;
        }
        else {
            if (gameServer.playerCount() == playerCountRound) {
                playerCountRound = 1;
                gameServer.newRound();
                broadcastToAllPlayers("Dealer Cards: " + gameServer.getDealerCards());
            } else {
                playerCountRound++;
            }
            gameServer.nextPlayer();
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage("Your Turn"));
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Cards" + gameServer.getCurrentPlayer().getHand() ) );
            broadcastToAllPlayers(gameServer.getCurrentPlayer() + "'s turn.");
        }
    }

    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus closeStatus ) throws Exception {
        gameServer.playerLeft( players.get( session ) );
        players.remove( session );
        System.out.println( "Connection closed: " + session.getId() );
    }

    private void broadcastToAllPlayers( String message ) throws Exception{
        for ( WebSocketSession session : players.keySet() ) {
            session.sendMessage( new TextMessage( message ) );
        }
    }
}
