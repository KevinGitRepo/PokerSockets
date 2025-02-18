package org.example.springsockets;

import org.json.JSONObject;
import org.example.General.Player;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiWebSocketHandler extends TextWebSocketHandler {

    private static int playerCountRound = 1;

    private final Map<WebSocketSession, Player> players = new ConcurrentHashMap<>();

    private final static GameServer gameServer = new GameServer();

    private final PlayerObjectMapper playerObjectMapper = new PlayerObjectMapper();

    private static boolean gameOver = false;

    private static int playersReady = 1;

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Once the player opens a tab they connect to the server
        System.out.println("New Connection established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // This is where the actions will be parsed

        JSONObject messageJSON = new JSONObject(message.getPayload().toString());
        String action = messageJSON.getString("action");

        System.out.println("Action: " + action);

        switch (action) {
            case "ready":
                handleReadyCase();
                break;
            case "create":
                handleCreateCase(messageJSON.getJSONObject("data"), session);
                break;
            case "bet":
                handleBet(messageJSON.getInt("data"));
                break;
            case "fold":
                handleFold();
                break;
            case "check":
                handleCheck();
                break;
        }

        System.out.println("Message: " + message.getPayload());

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

    private void handleReadyCase() {
        playersReady++;

        if ( playersReady == gameServer.playerCount() && gameOver ) {
            gameServer.gameStart();
            gameOver = false;
            playersReady = 1;
        }
    }

    private void handleCreateCase(JSONObject data, WebSocketSession session) throws IOException {
        Player receivedPlayer = playerObjectMapper.readValue(data, session);

        players.put( session, receivedPlayer );

        session.sendMessage( new TextMessage( gameServer.addPlayer( receivedPlayer ) ) );
    }

    private void handleBet(int betAmount) throws IOException {
        gameServer.playerBet(betAmount);

        broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " bet $" + betAmount);
        broadcastToAllPlayers("Pot: (" + gameServer.getPot() + ")");
    }

    private void handleFold() throws IOException {
        gameServer.playerFold();
        broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " folded");
    }

    private void handleCheck() throws IOException {
        gameServer.playerCheck();
        broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " checked");
    }

    public void sendMessageToPlayer(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    public void playGame() throws IOException {
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
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage("Bet Amount: (" + gameServer.getCurrentPlayer().getMoneyLimit() + ")") );
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Cards" + gameServer.getCurrentPlayer().getHand() ) );
            broadcastToAllPlayers(gameServer.getCurrentPlayer() + "'s turn.");
        }
    }

    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus closeStatus ) throws IOException {
        gameServer.playerLeft( players.get( session ) );
        players.remove( session );
        System.out.println( "Connection closed: " + session.getId() );
    }

    private void broadcastToAllPlayers( String message ) throws IOException{
        for ( WebSocketSession session : players.keySet() ) {
            session.sendMessage( new TextMessage( message ) );
        }
    }
}
