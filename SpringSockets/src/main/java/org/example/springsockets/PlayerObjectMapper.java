package org.example.springsockets;

import org.example.General.Player;
import org.springframework.web.socket.WebSocketSession;

public class PlayerObjectMapper {

    /**
     * Creates a player object from the payload information given
     * @param payload String which contains player information
     * @param session String of the session id
     * @return Player object created using information contained in the payload
     */
    public Player readValue(String payload, WebSocketSession session){
        return new Player("Yikes", 0, session);
    }
}
