package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Shoe shoe;
    List<Player> players;

    public Game(int numDecks, int numPlayers){
        shoe = new Shoe(numDecks);
        players = new ArrayList<>();

        for(int i = 0; i<numPlayers+1; i++){
            players.add(new Player());
        }

        playRound(numPlayers);
    }

    public void playRound(int numPlayers){
        Round round = new Round(numPlayers);
        for(int i=0; i<players.size(); i++){
            System.out.println("Player: " + i+1);
            System.out.print(players.get(i).hands.get(i).getCard(0) + " ");
            System.out.println(players.get(i).hands.get(i).getCard(1));
        }
    }

    class Round{
        public Round(int numPlayers){
            Hand hand = new Hand(11, 2);
            for(int i=0; i<numPlayers; i++){
                hand.addCard(shoe.deal());
                hand.addCard(shoe.deal());
                players.get(i).addHand(hand);
            }
        }
    }
}

