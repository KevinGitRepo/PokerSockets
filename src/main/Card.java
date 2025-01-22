package main;

public class Card {
    
    private final String suit; // clubs, spades, hearts, diamonds
    private final int value; // 2-14
    private final String face; // 2-10, Jack, Queen, King

    /**
     * Creates Card object which represents a real card
     * @param suit String value of the card's suit
     * @param face String value of the card's face value
     * @param value Integer value for the card's numerical value
     */
    public Card(String suit, String face, int value){
        this.suit = suit;
        this.face = face;
        this.value = value;
    }

    /**
     * Gets the card's suit value
     * @return String of card's suit value
     */
    public String getCardSuit(){
        return this.suit;
    }

    /**
     * Gets the card's numerical value
     * @return int of card's value
     */
    public int getCardValue(){
        return this.value;
    }

    /**
     * Gets the card's face value
     * @return String of card's face value
     */
    public String getCardFace(){
        return this.face;
    }

    /**
     * Checks if this object is the same as another object
     * @param card Object to be checked if equal to this object
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object card){
        if(card == this){ return true; }
        if(card == null || card.getClass() != getClass()){
            return false;
        }
        Card thisCard = (Card)card;
        return thisCard.getCardSuit().equals(this.suit) && thisCard.getCardFace().equals(this.face) && thisCard.getCardValue() == this.value;
    }

//    /**
//     * Converts the object to hash code (numerical value)
//     * @return int representing the object
//     */
//    @Override
//    public int hashCode(){
//        int result = 17;
//        result = 31 * result + this.suit.hashCode();
//        result = 31 * result + Integer.hashCode(this.value);
//        result = 31 * result + this.face.hashCode();
//        return result;
//    }

    /**
     * Changes the Card object into a readable format
     * @return String representation of a Card
     */
    @Override
    public String toString(){
        return this.face + " of " + this.suit;
    }
}
