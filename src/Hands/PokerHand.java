package Hands;

import main.Card;

import java.util.List;

public interface PokerHand {

    public List<Card> getHandCards();
    public String getHandName();
    public int amountWorth();
    
}
