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

        while(isPlaying) {
            round.printRound();
            round.playHands(numDecks);
        }
    }

    class Round{
        public Round(int numPlayers){
            for(int i=0; i<numPlayers; i++){
                getPlayer(i).addHand(createHand());
                getPlayer(i).numHands = getPlayer(i).numHands + 1;
            }
        }

        public Hand createHand(){
            Hand hand = new Hand(11, 2);
            hand.addCard(shoe.deal());
            hand.addCard(shoe.deal());
            return hand;
        }

        public void printRound(){
            System.out.println("Dealer:\n" + players.get(0).hands.get(0).getCard(0) + " " + players.get(0).hands.get(0).getCard(1) + "\n");

            for(int i=1; i<players.size(); i++){
                System.out.print("Player: " + (i) + "\t");
            }

            System.out.println("");

            for(int i=1; i<players.size(); i++){
                System.out.print(players.get(i).hands.get(0).getCard(0) + " ");
                System.out.print(players.get(i).hands.get(0).getCard(1));
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
                        players.get(i).resetHands();
                        break;
                    }

                    if(choice == 1){
                        if(shoe.total.size() == 0){
                            shoe.resetDecks(numDecks);
                        }
                        if(players.get(i).numHands > 2){

                        }
                    }
                }
                System.out.println("test");
            }
        }

        public boolean isBust(Player player, int num){
            for(int i=0; i<players.get(num).hands.size(); i++){

            }
            return (player.hands.get(num).getHandValue() > 21);
        }

        public int getAceValue(Player p, int hand){
            for(int i=0; i<p.hands.get())
        }
    }
}

