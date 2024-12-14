package main;

public class Card {
    
    private final String suit; // clubs, spades, hearts, diamonds
    private final int value; // 2-14
    private final String face; // 2-10, Jack, Queen, King

    public Card(String suit, String face, int value){
        this.suit = suit;
        this.face = face;
        this.value = value;
    }

    public String getCardSuit(){
        return this.suit;
    }

    public int getCardValue(){
        return this.value;
    }

    public String getCardFace(){
        return this.face;
    }

    @Override
    public boolean equals(Object card){
        if(card == this){ return true; }
        if(card == null || card.getClass() != getClass()){
            return false;
        }
        Card thisCard = (Card)card;
        return thisCard.getCardSuit().equals(this.suit) && thisCard.getCardFace().equals(this.face) && thisCard.getCardValue() == this.value;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + this.suit.hashCode();
        result = 31 * result + Integer.hashCode(this.value);
        result = 31 * result + this.face.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return this.face + " of " + this.suit;
    }
}
