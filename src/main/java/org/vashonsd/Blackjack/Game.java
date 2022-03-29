package org.vashonsd.Blackjack;

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
    }

    class Round{
        public Round(){

        }
    }
}

