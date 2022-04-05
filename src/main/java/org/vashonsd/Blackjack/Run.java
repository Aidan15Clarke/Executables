package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Card;

public class Run {
    public static void main(String[] args) {
        Card card = new Card(14, 1);
        System.out.println(card.getRankAsString());
        //Game game = new Game(6, 2);
    }
}