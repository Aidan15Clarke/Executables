package org.vashonsd.Blackjack;

import junit.framework.TestCase;
import org.junit.Assert;
import org.vashonsd.Utils.Cards.Card;
import org.vashonsd.Utils.Cards.Hand;

public class GameTest extends TestCase {
    Game g = new Game(6, 2);
    Player p = new Player();

    public void testIsBust() {
    }

    public void testContainsAce() {
    }

    public void testGetAceValue() {
        Hand hand = new Hand(11, 2);
        hand.addCard(new Card(14, 1));
        hand.addCard(new Card(10, 1));
        p.addHand(hand);

        System.out.println(g.noAceHandValue(p, 0));
    }
}