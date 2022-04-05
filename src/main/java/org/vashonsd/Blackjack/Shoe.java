package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Card;
import org.vashonsd.Utils.Cards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shoe {
    List<Deck> deckList = new ArrayList<>();

    ArrayList<Card> total = new ArrayList<>();

    public Shoe(int n){
        addDecks(n);
        resetDecks(n);
    }
//test
    public void resetDecks(int n){
        for(int i = 0; i < deckList.size(); i++){
            for(int j = 0; j < 52; j++){
                total.add(deckList.get(i).deal());
            }
        }

        for(int i = 0; i < total.size(); i++)
        {
            Random rand = new Random();
            int randomIndex = rand.nextInt(n*52);
            Card x = total.get(i);
            Card y = total.get(randomIndex);

            total.set(i, y);
            total.set(randomIndex, x);
        }
    }

    public void addDeck() {
        deckList.add(new Deck());
    }

    public void addDecks(int howMany) {
        for (int i=0; i<howMany; i++) {
            addDeck();
        }
    }
    public Card deal(){
        return total.remove(0);
    }
}
