package org.vashonsd.Utils.Cards;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    int capacity;
    int startingSize;
    String winCondition = "win";

    List<Card> cards = new ArrayList<>();

    public Hand(int capacity, int startingSize) {
        this.capacity = capacity;
        this.startingSize = startingSize;
    }

    public void setWinCondition(String s){
        winCondition = s;
    }

    public String getWinCondition(){
        return winCondition;
    }

    @Override
    public String toString() {
        return "" + cards;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public Card getCard(int n){
        return cards.get(n);
    }

    public int getSize(){
        return cards.size();
    }

    public void resetHand() {
        cards.removeAll(cards);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStartingSize() {
        return startingSize;
    }

    public int getHandValue() {

        int total = 0;
        for(Card card : cards) {
            total += card.getValue();
        }
        return total;
    }

//    public int indexOfCardbyValue(Card c) {
//        //Extract the value of c.
//        //return the index of first instance of that value.
//        //if not present, return -1.
//    }

    public boolean containsRank(Card c, String rank){
        return (c.getRankAsString().equals(rank));
    }
    public void setStartingSize(int startingSize) {
        this.startingSize = startingSize;
    }

    public void setValue(Card ace, int value){
        ace.value = value;
    }
}
