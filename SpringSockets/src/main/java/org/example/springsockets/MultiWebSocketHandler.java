package org.example.springsockets;

import org.example.General.Player;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    private final PlayerObjectMapper playerObjectMapper = new PlayerObjectMapper();

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New Connection established: " + session.getId());
        session.sendMessage(new TextMessage("Connection established! Welcome."));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // This is where the actions will be parsed

        System.out.println("Message: " + message.getPayload());

        Player receivedPlayer = playerObjectMapper.readValue(message.getPayload().toString());

        System.out.println("Welcome: " + receivedPlayer.getName());

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

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }
}
