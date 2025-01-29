package org.example.HandChecker;

import org.example.General.Card;
import org.example.General.Player;
import org.example.General.PokerHandTypes;
import org.example.HandConnector.HandConnectorManager;

import java.util.*;

public class CheckFlush extends HandCheckParent implements CheckHand {

    private final HandConnectorManager handConnectorManager;

    public CheckFlush( HandConnectorManager connectorManager ) {
        this.handConnectorManager = connectorManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean check(Player player, List<Card> dealersHand ) {

        // Creates a map for each suit
        Map<String, List<Card>> cardsMap = new HashMap<>();
        cardsMap.put( "Clubs", new ArrayList<>() );
        cardsMap.put( "Spades", new ArrayList<>() );
        cardsMap.put( "Hearts", new ArrayList<>() );
        cardsMap.put( "Diamonds", new ArrayList<>() );

        List<Card> mergedList = super.mergeLists( player.getHand(), dealersHand );
        String keyLongestList = "";
        int countLongestList = 0;

        // Checks if there is at least 5 of the same suit and keeps track of the longest list
        // Has to be at least 5 of the same suit
        for ( Card card : mergedList ) {
            cardsMap.get( card.getCardSuit() ).add( card );

            if ( card.getCardSuit().equals( keyLongestList ) ) {
                countLongestList++;
            }
            else {
                countLongestList--;
            }

            if ( countLongestList == 0 ) {
                keyLongestList = card.getCardSuit();
            }
        }

        List<Card> longestList = cardsMap.get( keyLongestList );

        // Since the lists were merged, this double checks to make sure the player has the specific suit
        boolean playerCardSuitBool = player.getHand().get(0).getCardSuit().equals( keyLongestList ) ||
                player.getHand().get(player.getHand().size() - 1).getCardSuit().equals( keyLongestList );

        if ( longestList.size() < 5 || !playerCardSuitBool ) {
            return false;
        }

        // Sorts the list to return the larger 5 elements if needed
        Collections.sort( longestList );

        // Sends the last 5 elements to make sure it is the highest flush possible
        return player.setPokerHand(
                this.handConnectorManager.sendForHand(
                        PokerHandTypes.FLUSH, longestList.subList( longestList.size() - 5, longestList.size() ) ) );
    }
}
