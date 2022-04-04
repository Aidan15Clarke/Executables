package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Hand> hands = new ArrayList<>();
    int numHands = 0;

    public void addHand(Hand hand){
        hands.add(hand);
    }

    public void resetHands(){
        for(int i=0; i<hands.size(); i++){
            hands.remove(i);
        }
    }

    public Hand getHand(int n){
        return hands.get(n);
    }
}
