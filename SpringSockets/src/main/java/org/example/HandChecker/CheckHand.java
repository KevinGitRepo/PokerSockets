package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;

import java.util.List;

public interface CheckHand {

    /**
     * Checks whether the player can create a specified hand. Will add to player object
     * @param player the who's hand is being checked
     * @param dealersHand the current hand of the dealer
     * @return true if player can create the specified hand, false otherwise
     */
    boolean check (Player player, List<Card> dealersHand );
}
