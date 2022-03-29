package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Hand> hands = new ArrayList<>();

    public void addHand(Hand hand){
        hands.add(hand);
    }
}
