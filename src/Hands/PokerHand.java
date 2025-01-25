package Hands;

import main.Card;
import main.PokerHandTypes;

import java.util.List;

public interface PokerHand {

    public List<Card> getHandCards();
    public PokerHandTypes getHandName();
    public int amountWorth();
    
}
