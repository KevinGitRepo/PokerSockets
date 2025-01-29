package org.example.General;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    
    private List<Card> deck;
    private static Random rand;

    private final String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
    private final String[] faces = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };


    /**
     * Initializes global variables and creates the deck
     */
    public Deck() {
        this.deck = new ArrayList<>();
        Deck.rand = new Random();
        createDeck();
    }

    /**
     * Creates the deck of cards by using the suit and faces array
     */
    private void createDeck() {
        for ( String suit : suits ) {
            for ( int i = 0; i < faces.length; i++ ) {
                this.deck.add( new Card( suit, faces[i], i + 2 ) );
            }
        }
    }

    /**
     * Removes first card in deck
     * @return Card being dealt
     */
    public Card dealCard() {
        return deck.remove(0); // Removes first since adding cards to end
    }

    /**
     * Adds cards to the deck then shuffles the deck
     * @param additionalCards List of cards being added into the deck
     */
    public void addCards( List<Card> additionalCards ) {
        this.deck.addAll( additionalCards ); // Add any cards that were removed during play
        shuffle();
    }

    /**
     * Randomly swaps cards from the first half of the deck with cards from the second half of the deck
     */
    public void shuffle() {
        // Create variables before looping to decrease variable creation
        int deckHalfwayPoint = this.deck.size() / 2;
        int firstHalfRandom;
        int secondHalfRandom;

        for( int i = 0; i < ( int )deckHalfwayPoint; i++ ) {
            firstHalfRandom = rand.nextInt( deckHalfwayPoint ); // Grab random number from 0-26
            secondHalfRandom = rand.nextInt( deckHalfwayPoint ) + deckHalfwayPoint; // Grab random number from 26-52

            Collections.swap( deck, firstHalfRandom, secondHalfRandom );
        }
    }
}
