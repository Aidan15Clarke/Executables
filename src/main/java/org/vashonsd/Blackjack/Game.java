package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Card;
import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    Shoe shoe;
    List<Player> players;

    public Game(int numDecks, int numPlayers){
        shoe = new Shoe(numDecks);
        players = new ArrayList<>();

        for(int i = 0; i<numPlayers+1; i++){
            players.add(new Player());
        }

        playRound(numDecks, numPlayers+1);
    }

    public Player getPlayer(int n){
        return players.get(n);
    }

    public void playRound(int numDecks, int numPlayers){
        Round round = new Round(numPlayers);
        boolean isPlaying = true;

        while(isPlaying) {
            isPlaying = round.playHands(numDecks);
        }
    }

    class Round{
        public Round(int numPlayers){
            for(int i=0; i<numPlayers; i++){
                getPlayer(i).addHand(createHand());
            }
        }

        public Hand createHand(){
            Hand hand = new Hand(11, 2);
            hand.addCard(shoe.deal());
            hand.addCard(shoe.deal());
            return hand;
        }

        public void dealToHand(Player p, int hand, int numDecks){
            if(shoe.total.size() == 0){
                shoe.resetDecks(numDecks);
            }

            p.getHand(hand).addCard(shoe.deal());
        }

        public boolean playHands(int numDecks){
            Scanner input = new Scanner(System.in);
            int currentHand;

            for(int i=1; i<players.size(); i++){
                for(int j=getPlayer(i).numHands-1; j>-1; j--) {
                    currentHand = j;

                    printRound();
                    System.out.println("Player " + i + ": " + Arrays.toString(new Hand[]{getPlayer(i).getHand(currentHand)}));
                    System.out.println("1) Hit 2) Stand 3) Double 4) Split");

                    int choice = input.nextInt();

                    if(choice == 2){
                        printRound();
                        continue;
                    }

                    if(choice == 1){
                        dealToHand(getPlayer(i), currentHand, numDecks);

                        if(isBust(getPlayer(i), currentHand)){
                            continue;
                        }
                    }

                    if(choice == 3){
                        dealToHand(getPlayer(i), currentHand, numDecks);
                        continue;
                    }

                    if(choice == 4){
                        Card temp = getPlayer(i).getHand(currentHand).getCard(0);

                        if(getPlayer(i).getHand(currentHand).getCard(0).getValue() == getPlayer(i).getHand(currentHand).getCard(1).getValue()
                                || getPlayer(i).getHand(currentHand).getCard(0).getRankAsString().equals(getPlayer(i).getHand(currentHand).getCard(1).getRankAsString())){
                            if(getPlayer(i).numHands>1){
                                getPlayer(i).resetHand(currentHand);
                            } else{
                                getPlayer(i).resetHands();
                            }

                            Hand handOne = new Hand(11, 2);
                            Hand handTwo = new Hand(11, 2);

                            handOne.addCard(temp);
                            handOne.addCard(shoe.deal());
                            handTwo.addCard(temp);
                            handTwo.addCard(shoe.deal());

                            getPlayer(i).addHand(handOne);
                            getPlayer(i).addHand(handTwo);

                            j=getPlayer(i).numHands;
                        } else{
                            j++;
                            continue;
                        }
                    }
                }
            }
            printRound();
            return false;
        }
        public void printRound(){
            System.out.println("Dealer:   " + Arrays.toString(getPlayer(0).hands.toArray()) + " " + getTotalValue(getPlayer(0), 0));

            for(int i=1; i<players.size(); i++){
                System.out.print("Player " + (i) + ": ");
                System.out.print(Arrays.toString(getPlayer(i).hands.toArray()) + " ");

                if(getPlayer(i).numHands > 1){
                    for(int j=0; j<getPlayer(i).numHands; j++){
                        System.out.print(getTotalValue(getPlayer(i), j) + " ");
                    }
                } else{
                    System.out.print(getTotalValue(getPlayer(i), 0));
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public boolean isBust(Player p, int hand){
        if(getTotalValue(p, hand) > 21){
            return true;
        }
        return false;
    }

    //This works
    public int getTotalValue(Player p, int hand){
        int totalValue = 0;

        if(numAces(p, hand) == 0){
            totalValue += noAceHandValue(p, hand);
            return totalValue;
        }

        setAceValue(p, hand);

        for(int i=0; i<p.getHand(hand).getSize(); i++){
            totalValue += p.getHand(hand).getCard(i).getValue();
        }
        return totalValue;
    }

    //This works
    public int noAceHandValue(Player p, int hand){
        int totalValue = 0;

        for(int i=0; i<p.getHand(hand).getSize(); i++){
            if(p.getHand(hand).getCard(i).getRankAsString().equals("A")){
                continue;
            }
            totalValue += p.getHand(hand).getCard(i).getValue();
        }
        return totalValue;
    }

    //This works
    public void setAceValue(Player p, int hand){
        ArrayList<Integer> indexes = aceIndex(p, hand);

        if(numAces(p, hand) > 1){
            if(noAceHandValue(p, hand) > 11){
                for(int i=0; i<indexes.size(); i++){
                    p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(i)), 1);
                }
            }
            if(noAceHandValue(p, hand) < 11){
                p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 11);
                if(noAceHandValue(p, hand) + p.getHand(hand).getCard(indexes.get(0)).getValue() < 21){
                    for(int i=1; i<indexes.size(); i++){
                        p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(i)), 1);
                    }
                }
            }
        }

        else {
            if (noAceHandValue(p, hand) < 11) {
                p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 11);
            } else {
                p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 1);
            }
        }
    }

    //This works
    public ArrayList<Integer> aceIndex(Player p, int hand){
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int i=0; i<p.getHand(hand).getSize(); i++){
            if(p.getHand(hand).getCard(i).getRankAsString().equals("A")){
                indexes.add(i);
            }
        }
        return indexes;
    }

    //This works
    public int numAces(Player p, int hand){
        int numAces = 0;
        for(int i=0; i<p.getHand(hand).getSize(); i++){
            if(p.getHand(hand).getCard(i).getRankAsString().equals("A")){
                numAces++;
            }
        }
        return numAces;
    }
}

