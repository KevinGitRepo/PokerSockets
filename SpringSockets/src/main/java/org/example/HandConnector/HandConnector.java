package org.example.HandConnector;

import org.example.General.Card;
import org.example.Hands.PokerHand;

import java.util.List;

public interface HandConnector {

    PokerHand sendForHand(List<Card> handList );
}
