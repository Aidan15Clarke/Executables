package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Hand> hands = new ArrayList<>();
    int numHands = 0;

    public void addHand(Hand hand){
        hands.add(hand);
        numHands = hands.size();
    }

    public void resetHands(){
        hands.clear();
    }

    public void resetHand(int num){
        hands.remove(num);
    }

    public Hand getHand(int n){
        return hands.get(n);
    }
}
