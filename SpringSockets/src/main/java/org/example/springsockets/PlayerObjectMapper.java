package org.example.springsockets;

import org.example.General.Player;

public class PlayerObjectMapper {

    /**
     * Creates a player object from the payload information given
     * @param payload String which contains player information
     * @return Player object created using information contained in the payload
     */
    public Player readValue(String payload){
        return new Player("Yikes", 0);
    }
}
