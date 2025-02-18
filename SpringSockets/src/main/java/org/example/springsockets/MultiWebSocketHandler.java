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

    private static int playersFolded = 0;

    private static boolean reraised = false;

    /**
     * Function for once the connection between the frontend and backend is established
     * @param session WebSocketSession object for player's session
     * @throws IOException if any issues arise with WebSocket
     */
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        // Once the player opens a tab they connect to the server
        System.out.println("New Connection established: " + session.getId());
    }

    /**
     * Handles the messages sent from the frontend client
     * Parses messages into actions
     * Runs only when a message is received
     * @param session WebSocketSession object for player's session
     * @param message WebSocketMessage object of the frontend's message
     * @throws IOException if any issues arise with WebSocket
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        // Parse Messages

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

        // Only run this if game is currently playing
        if ( gameServer.getGameState().equals( GameState.IN_PROGRESS ) ) {
            playGame();
        }

        // Will run after the last person is added to the game
        // For multiple games, need to run gameStart function iff gameOver is false; meaning
        // once the game is over, the gameState should be changed to WAITING
        if ( !gameOver && gameServer.gameStart() ) {
            broadcastToAllPlayers("Game started.");
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Cards" + gameServer.getCurrentPlayer().getHand() ) );
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Turn" ) );
        }
    }

    /**
     * Handles whenever a player readies up
     */
    private void handleReadyCase() {
        playersReady++;

        if ( playersReady == gameServer.playerCount() && gameOver ) {
            gameOver = false;
            playersReady = 1;
        }
    }

    /**
     * Handles when a new player is being created
     * @param data player's information for player creation
     * @param session WebSocketSession object for the player's session
     * @throws IOException if any issues arise with WebSocket
     */
    private void handleCreateCase( JSONObject data, WebSocketSession session ) throws IOException {
        Player receivedPlayer = playerObjectMapper.readValue( data, session );

        players.put( session, receivedPlayer );

        session.sendMessage( new TextMessage( gameServer.addPlayer( receivedPlayer ) ) );
    }

    /**
     * Handles when a player places a bet
     * @param betAmount amount of money the player will bet
     * @throws IOException if any issues arise with WebSocket
     */
    private void handleBet( int betAmount ) throws IOException {
        if ( betAmount > gameServer.getRoundBetAmount() ) {
            reraised = true;
            gameServer.setRoundReRaisedIndex();
        }
        gameServer.playerBet( betAmount );

        // Needs to send everyone in the game updates for "moves"
        broadcastToAllPlayers( gameServer.getCurrentPlayer().getName() + " bet $" + betAmount );
        broadcastToAllPlayers( "Pot: (" + gameServer.getPot() + ")" );
        broadcastToAllPlayers( "Bet Amount: (" + betAmount + ")" );
    }

    /**
     * Handles when a player folds their hand
     * @throws IOException if any issues arise with WebSocket
     */
    private void handleFold() throws IOException {
        gameServer.playerFold();
        playersFolded++;
        broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " folded");
    }

    /**
     * Handles when a player checks
     * @throws IOException if any issues arise with WebSocket
     */
    private void handleCheck() throws IOException {
        gameServer.playerCheck();
        broadcastToAllPlayers(gameServer.getCurrentPlayer().getName() + " checked");
    }

    /**
     * Sends a message to any player using their WebSocketSession
     * @param session WebSocketSession object to send a message
     * @param message TextMessage object of the message that will be sent to the frontend
     * @throws IOException if any issues arise with WebSocket
     */
    public void sendMessageToPlayer(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    /**
     * Main game function
     * @throws IOException if any issues arise with WebSocket
     */
    public void playGame() throws IOException {

        // Checks if a winner needs to be decided
        if ( gameServer.playerCount() == playerCountRound && gameServer.getDealerCards().size() == 5 ) {
            Player winner = gameServer.getWinner();
            broadcastToAllPlayers(winner.toString() + " won with hand " +
                    (winner.getPokerHand() == null ? "high " + winner.getHighCard() : winner.getPokerHand().toString()));
            gameRestart();
        }
        else {
            // Considers a new round
            if ( gameServer.playerCount() == ( playerCountRound + playersFolded ) ) {
                playerCountRound = 1;
                if (!reraised) {
                    gameServer.newRound();
                    broadcastToAllPlayers("Dealer Cards: " + gameServer.getDealerCards());
                }

            } else {
                // Continues to the next player in the same round
                playerCountRound++;
            }
            // Continues with the next player
            gameServer.nextPlayer();
            playersFolded = 0;
            reraised = false;
            // Gives next player all information they need
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage("Your Turn"));
            sendMessageToPlayer(gameServer.getCurrentPlayer().getSession(), new TextMessage("Bet Amount: (" + gameServer.getCurrentPlayer().getMoneyLimit() + ")") );
            sendMessageToPlayer( gameServer.getCurrentPlayer().getSession(),
                    new TextMessage( "Your Cards" + gameServer.getCurrentPlayer().getHand() ) );
            broadcastToAllPlayers(gameServer.getCurrentPlayer() + "'s turn.");
        }
    }

    /**
     * Restarts the game
     * Clears and resets values
     */
    private void gameRestart() {
        gameServer.restart();
        gameOver = true;
        playerCountRound = 1;
        playersFolded = 0;
        reraised = false;
    }

    /**
     * Function for when someone leaves the session (Closes browser)
     * @param session WebSocketSession object for player's session
     * @param closeStatus CloseStatus object for closing browser
     * @throws IOException if any issues arise with WebSocket
     */
    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus closeStatus ) throws IOException {
        gameServer.playerLeft( players.get( session ) );
        players.remove( session );
        System.out.println( "Connection closed: " + session.getId() );
    }

    /**
     * Sends a message to all players currently connected to the WebSocket
     * @param message String message
     * @throws IOException if any issues arise with WebSocket
     */
    private void broadcastToAllPlayers( String message ) throws IOException{
        for ( WebSocketSession session : players.keySet() ) {
            session.sendMessage( new TextMessage( message ) );
        }
    }
}
