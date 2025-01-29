package org.example.Hands;

import org.example.General.Card;
import org.example.General.PokerHandTypes;

import java.util.List;

public interface PokerHand {

    /**
     * Gets the cards which form this poker hand
     * @return List of cards
     */
    List<Card> getHandCards();

    /**
     * Gets the "name" of the poker hand
     * @return PokerHandTypes object which represents the name of the poker hand
     */
    PokerHandTypes getHandName();

    /**
     * Gets the numeral number the poker hand is worth
     * @return integer value of the poker hand
     */
    int amountWorth();
    
}
