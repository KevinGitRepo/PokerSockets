package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    
    private List<Card> deck;
    private static Random rand;

    public Deck(){
        this.deck = new ArrayList<>();
        Deck.rand = new Random();
    }

    public Card dealCard(){
        return deck.removeFirst(); // Removes first since adding cards to end
    }

    public void addCards(List<Card> additionalCards){
        this.deck.addAll(additionalCards); // Add any cards that were removed during play
        shuffle();
    }

    public void shuffle(){
        // Create variables before looping to decrease variable creation
        int deckHalfwayPoint = this.deck.size()/2;
        int firstHalfRandom;
        int secondHalfRandom;

        for(int i = 0; i < (int)deckHalfwayPoint; i++){
            firstHalfRandom = rand.nextInt(deckHalfwayPoint); // Grab random number from 0-26
            secondHalfRandom = rand.nextInt(this.deck.size()) + deckHalfwayPoint; // Grab random number from 26-52

            Collections.swap(deck, firstHalfRandom, secondHalfRandom);
        }
    }
}
