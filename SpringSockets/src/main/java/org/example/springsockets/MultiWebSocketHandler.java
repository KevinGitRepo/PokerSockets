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

    private static boolean firstPlayer;

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

        }

        else if ( message.getPayload().toString().contains( "fold" ) ) {

        }

        else if ( message.getPayload().toString().contains( "check" ) ) {

        }

        /*
        String messageType = message.getPayload().toString();
        switch (messageType) {
            case "bet":
                // Use the bet functionality
            case "fold":
                // Use the fold functionality
            case "check":
                // Use the check functionality
        }
        */

        // Only run this if game is currently playing
        System.out.println(gameServer.getGameState());
        if ( gameServer.getGameState().equals( GameState.IN_PROGRESS ) ) {
            System.out.println("Game is In Progress");
            if (gameServer.playerCount() == playerCountRound) {
                playerCountRound = 1;
                gameServer.newRound();
            } else {
                playerCountRound++;
            }
            gameServer.nextPlayer();
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage( "Your Turn" ) );
            broadcastToAllPlayers(gameServer.getCurrentPlayer() + "'s turn.");
        }

        if ( gameServer.gameStart() ) {
            broadcastToAllPlayers("Game started.");
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage("Your Turn"));
        }
    }

    public void sendMessageToPlayer(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        players.remove( session );
        System.out.println("Connection closed: " + session.getId());
    }

    private void broadcastToAllPlayers( String message ) throws Exception{
        for ( WebSocketSession session : players.keySet() ) {
            session.sendMessage( new TextMessage( message ) );
        }
    }
}
