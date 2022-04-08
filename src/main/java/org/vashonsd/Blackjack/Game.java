package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
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
        System.out.println("Dealer:\n" + getPlayer(0).getHand(0).getCard(0) + " " + getPlayer(0).getHand(0).getCard(1) + "\n");
        System.out.println(getTotalValue(getPlayer(0), 0));

        while(isPlaying) {
            round.printRound();
            round.playHands(numDecks);
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

        public void dealToHand(Player p, int hand){
            p.getHand(hand).addCard(shoe.deal());
        }

        public void printRound(){
            System.out.println("Dealer:\n" + getPlayer(0).getHand(0).getCard(0) + " " + getPlayer(0).getHand(0).getCard(1) + "\n");

            for(int i=1; i<players.size(); i++){
                System.out.print("Player: " + (i) + "\t");
            }

            System.out.println("");

            for(int i=1; i<players.size(); i++){
                Hand theHand = getPlayer(i).getHand(0);
                for(int j=0; j<theHand.getSize(); j++){
                    System.out.print(theHand.getCard(j) + " ");
                }
                System.out.print("   \t");
            }
            System.out.println("\n");
        }

        public void playHands(int numDecks){
            Scanner input = new Scanner(System.in);
            boolean isPlaying;

            for(int i=1; i<players.size(); i++){
                isPlaying = true;
                while(isPlaying) {
                    System.out.println("Player " + i + ":");
                    System.out.println("1) Hit 2) Stand 3) Double 4) Split");
                    int choice = input.nextInt();

                    if(choice == 2){
                        isPlaying = false;
                        getPlayer(i).resetHands();
                        break;
                    }

                    if(choice == 1){
                        if(shoe.total.size() == 0){
                            shoe.resetDecks(numDecks);
                        }

                        dealToHand(getPlayer(i), 0);
                        printRound();

                        System.out.println(getTotalValue(getPlayer(1), 0));
//                        if(getPlayer(i).numHands > 2){
//
//                        }
                    }
                }
                System.out.println("test");
            }
        }
    }

//    public boolean isBust(Player p, int n){
//        if(containsAce(p, n) != -1){
//            getAceValue(p, n);
//        }
//    }

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

