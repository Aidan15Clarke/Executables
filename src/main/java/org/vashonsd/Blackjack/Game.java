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
        Round round = resetRound(numPlayers);

        for(int i=0; i<100; i++) {
            round.playHands(numDecks);
            round = resetRound(numPlayers);
        }
    }

    public Round resetRound(int numPlayers){
        return new Round(numPlayers);
    }

    class Round{
        public Round(int numPlayers){
            for(int i=0; i<numPlayers; i++){
                getPlayer(i).addHand(createHand());
            }
        }

        //Creates a fresh hand
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

        public void playHands(int numDecks){
            Scanner input = new Scanner(System.in);
            int currentHand;

            for(int i=1; i<players.size(); i++){
                for(int j=getPlayer(i).numHands-1; j>-1; j--) {
                    currentHand = j;

                    printRound();
                    System.out.println("Player " + i + ": " + Arrays.toString(new Hand[]{getPlayer(i).getHand(currentHand)}) + " " + getTotalValue(getPlayer(i), currentHand));
                    System.out.println("1) Hit 2) Stand 3) Double 4) Split");

                    int choice = input.nextInt();
                    System.out.println("");

                    if(choice == 2){
                        continue;
                    }

                    if(choice == 1){
                        dealToHand(getPlayer(i), currentHand, numDecks);
                        if(isBust(getPlayer(i), currentHand)){
                            continue;
                        }
                        i--;
                        continue;
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

            //Dealer plays
            boolean belowSeventeen = getTotalValue(getPlayer(0), 0) < 17;

            while(belowSeventeen){
                getPlayer(0).getHand(0).addCard(shoe.deal());
                belowSeventeen = getTotalValue(getPlayer(0), 0) < 17;
                printRound();
            }

            //Sets winCondition for each player
            for(int i=1; i<players.size(); i++){
                for(int j=getPlayer(i).numHands-1; j>-1; j--) {
                    if (isBust(getPlayer(0), 0) && getPlayer(i).getHand(j).getWinCondition().equals("win")) {
                        continue;
                    } else if (getTotalValue(getPlayer(0), 0) > getTotalValue(getPlayer(i), j)) {
                        getPlayer(i).getHand(j).setWinCondition("lose");
                    } else if (getTotalValue(getPlayer(0), 0) == getTotalValue(getPlayer(i), j)){
                        getPlayer(i).getHand(j).setWinCondition("push");
                    }
                }
            }

            for(int i=1; i<players.size(); i++){
                System.out.println("Player " + i + ": ");
                for(int j=0; j<getPlayer(i).numHands; j++){
                    System.out.println("\tHand " + (j+1) + " " + getPlayer(i).getHand(j).getWinCondition());
                }
            }

            for(int i=0; i<players.size(); i++){
                getPlayer(i).resetHands();
            }

            System.out.println("");
        }

        //Prints/Updates each Round/Play
        public void printRound(){
            System.out.println("Dealer:   " + "[[" + getPlayer(0).getHand(0).getCard(0) + ", X]] " + Arrays.toString(getPlayer(0).hands.toArray()) + " " + getTotalValue(getPlayer(0), 0));

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

    //Checks whether a players hand is over 21
    public boolean isBust(Player p, int hand){
        if(getTotalValue(p, hand) > 21){
            p.getHand(hand).setWinCondition("lose");
            return true;
        }
        return false;
    }

    //Gets the total value of a players hand
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

    //Gets the value of a players hand not including Aces
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

    //Determines and sets the value of each Ace in a players hand
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
        } else {
            if (noAceHandValue(p, hand) < 11) {
                p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 11);
            } else {
                p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 1);
            }
        }
    }

    //Returns a List of Integers of the Indexes of Aces in a players hand
    public ArrayList<Integer> aceIndex(Player p, int hand){
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int i=0; i<p.getHand(hand).getSize(); i++){
            if(p.getHand(hand).getCard(i).getRankAsString().equals("A")){
                indexes.add(i);
            }
        }
        return indexes;
    }

    //Returns the number of Aces in a players hand
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