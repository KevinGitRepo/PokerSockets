package org.example.springsockets;

import org.example.General.Player;
import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

public class PlayerObjectMapper {

    /**
     * Creates a player object from the payload information given
     * @param payload String which contains player information
     * @param session String of the session id
     * @return Player object created using information contained in the payload
     */
    public Player readValue(JSONObject payload, WebSocketSession session){
        String name = payload.getString("name");
        int money = payload.getInt("money");
        return new Player(name, money, session);
    }
}
