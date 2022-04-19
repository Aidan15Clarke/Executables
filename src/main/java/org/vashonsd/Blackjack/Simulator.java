package org.vashonsd.Blackjack;

import org.vashonsd.Utils.Cards.Card;
import org.vashonsd.Utils.Cards.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Simulator {
    Shoe shoe;
    List<Player> players;
    List<List<Object>> strategy;
    int numRounds;

    public Simulator(int numDecks, int numPlayers, List<List<Object>> strategy, int numRounds) {
        shoe = new Shoe(numDecks);
        players = new ArrayList<>();
        this.strategy = strategy;
        this.numRounds = numRounds;

        for (int i = 0; i < numPlayers + 1; i++) {
            players.add(new Player());
        }
        runSimulator(numDecks, numPlayers + 1, numRounds);
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public void runSimulator(int numDecks, int numPlayers, int numRounds) {
        Round round;

        for(int i=0; i<numRounds; i++) {
            round = resetRound(numPlayers);

            System.out.println("[[" + getPlayer(0).getHand(0).getCard(0) + ", X]] " + Arrays.toString(getPlayer(0).hands.toArray()));

            for(int j=1; j<players.size(); j++){
                System.out.println(Arrays.toString(getPlayer(j).hands.toArray()));
            }

            round.playHands(numDecks);
        }
    }

    public Round resetRound(int numPlayers) {
        return new Round(numPlayers);
    }

    class Round {
        public Round(int numPlayers) {
            for (int i = 0; i < numPlayers; i++) {
                getPlayer(i).resetHands();
                getPlayer(i).addHand(createHand());
            }
        }

        //Creates a fresh hand
        public Hand createHand() {
            Hand hand = new Hand(11, 2);
            hand.addCard(shoe.deal());
            hand.addCard(shoe.deal());
            return hand;
        }

        public void dealToHand(Player p, int hand, int numDecks) {
            if (shoe.total.size() == 0) {
                shoe.resetDecks(numDecks);
            }
            p.getHand(hand).addCard(shoe.deal());
        }

        public void playHands(int numDecks) {
            Scanner input = new Scanner(System.in);
            int currentHand;
            int dealerIndex = 0;
            int playerIndex = 0;
            int secondHalf = 0;
            String temp = "";
            String playerChoice = "";

            for (int i = 1; i < players.size(); i++) {
                for (int j = getPlayer(i).numHands - 1; j > -1; j--) {
                    currentHand = j;

                    if(getPlayer(i).getHand(currentHand).getCard(0).getRankAsString().equals("A") && getPlayer(i).getHand(currentHand).getCard(1).getRankAsString().equals("A")){
                        System.out.println("Pair of Aces");
                    }

                    //Gets dealerIndex for dealer's up-card
                    for (int k = 0; k < 11; k++) {
                        if (String.valueOf(getPlayer(0).getHand(0).getCard(0).getValue()).equals(strategy.get(0).get(k).toString())
                        || getPlayer(0).getHand(0).getCard(0).getRankAsString().equals(strategy.get(0).get(k).toString())) {
                            dealerIndex = k;
                        }
                    }

                    //Gets playerChoice for a hard 17 or higher
                    if (getTotalValue(getPlayer(i), currentHand) >= 17 && noAceHandValue(getPlayer(i), currentHand) >= 11 &&
                            (getPlayer(i).getHand(currentHand).getSize() > 2 || !(isPair(getPlayer(i), currentHand)))) {
                        playerChoice = "S";
                    }

                    //Gets playerChoice for a hard total under 17
                    else if(getTotalValue(getPlayer(i), currentHand) <= 8
                            && !(isPair(getPlayer(i), currentHand))) {
                        playerChoice = "H";
                    }

                    //Gets playerIndex if player has a pair
                    else if (isPair(getPlayer(i), currentHand)) {
                        for (int k = 0; k < getPlayer(i).getHand(currentHand).getSize(); k++) {
                            temp += String.valueOf(getPlayer(i).getHand(currentHand).getCard(k).getValue());
                        }

                        for (int k = 0; k < strategy.size(); k++) {
                            if (temp.equals(strategy.get(k).get(0).toString())) {
                                playerIndex = k;
                            }
                        }
                    }

                    //Gets playerIndex if player has 1 ace
                    else if (numAces(getPlayer(i), currentHand) == 1) {
                        setAceValue(getPlayer(i), currentHand);
                        ArrayList<Integer> indexes = aceIndex(getPlayer(i), currentHand);

                        if(getPlayer(i).getHand(currentHand).getCard(indexes.get(0)).getValue() == 11){
                            for(int k=0; k<getPlayer(i).getHand(currentHand).getSize(); k++){
                                if(k == indexes.get(0)){
                                    continue;
                                }
                                secondHalf += getPlayer(i).getHand(currentHand).getCard(k).getValue();
                            }
                            temp += "A" + String.valueOf(secondHalf);
                        } else{
                            temp += String.valueOf(getTotalValue(getPlayer(i), currentHand));
                        }

                        for (int k = 0; k < strategy.size(); k++) {
                            if (temp.equals(strategy.get(k).get(0).toString())) {
                                playerIndex = k;
                            }
                        }
                    }

                    //UNTESTED - gets playerIndex if player has more than 1 ace with the first worth 11
                    else if (numAces(getPlayer(i), currentHand) > 1 && noAceHandValue(getPlayer(i), currentHand) <= 9) {
                        setAceValue(getPlayer(i), currentHand);
                        ArrayList<Integer> indexes = aceIndex(getPlayer(i), currentHand);

                        if (getPlayer(i).getHand(currentHand).getCard(indexes.get(0)).getValue() == 11) {
                            for (int k = 0; k < getPlayer(i).getHand(currentHand).getSize(); k++) {
                                if (k == indexes.get(0)) {
                                    continue;
                                }
                                secondHalf += getPlayer(i).getHand(currentHand).getCard(k).getValue();
                            }
                        }

                        temp = "A" + String.valueOf(secondHalf);

                        for (int k = 0; k < strategy.size(); k++) {
                            if (temp.equals(strategy.get(k).get(0).toString())) {
                                playerIndex = k;
                            }
                        }
                    }

                    //UNTESTED - gets playerIndex for hard total
                    else {
                        int total = getTotalValue(getPlayer(i), currentHand);

                        for (int k = 0; k < strategy.size(); k++) {
                            if (String.valueOf(total).equals(strategy.get(k).get(0).toString())) {
                                playerIndex = k;
                            }
                        }
                    }

                    String playChoice = playChoice(playerIndex, dealerIndex);
                    if(!(playerChoice.equals(""))){
                        if (playerChoice.equals("S")) {
                            continue;
                        } else if (playerChoice.equals("H")) {
                            dealToHand(getPlayer(i), currentHand, numDecks);
                            if (isBust(getPlayer(i), currentHand)) {
                                continue;
                            }
                            i--;
                            continue;
                        }
                    }

                    if (playChoice.equals("S")) {
                        continue;
                    } else if (playChoice.equals("H")) {
                        dealToHand(getPlayer(i), currentHand, numDecks);
                        if (isBust(getPlayer(i), currentHand)) {
                            continue;
                        }
                        i--;
                        continue;
                    } else if (playChoice.equals("D") || playChoice.equals("DS")) {
                        dealToHand(getPlayer(i), currentHand, numDecks);
                        System.out.println("Double");
                        continue;
                    } else if (playChoice.equals("Y") || playChoice.equals("YN")) {
                        System.out.println("Split");
                        Card tempCard = getPlayer(i).getHand(currentHand).getCard(0);

                        if (getPlayer(i).getHand(currentHand).getCard(0).getValue() == getPlayer(i).getHand(currentHand).getCard(1).getValue()
                                || getPlayer(i).getHand(currentHand).getCard(0).getRankAsString().equals(getPlayer(i).getHand(currentHand).getCard(1).getRankAsString())) {
                            if (getPlayer(i).numHands > 1) {
                                getPlayer(i).resetHand(currentHand);
                            } else {
                                getPlayer(i).resetHands();
                            }

                            Hand handOne = new Hand(11, 2);
                            Hand handTwo = new Hand(11, 2);

                            handOne.addCard(tempCard);
                            handOne.addCard(shoe.deal());
                            handTwo.addCard(tempCard);
                            handTwo.addCard(shoe.deal());

                            getPlayer(i).addHand(handOne);
                            getPlayer(i).addHand(handTwo);

                            j = getPlayer(i).numHands;
                        }
                    } else if(playChoice.equals("N")){
                        for(int k = 0; k < strategy.size(); k++) {
                            if(String.valueOf(getTotalValue(getPlayer(i), currentHand)).equals(strategy.get(k).get(0).toString())) {
                                playerIndex = k;
                            }
                        }
                        playChoice = playChoice(playerIndex, dealerIndex);
                        if(playChoice.equals("S")){
                            continue;
                        } else if(playChoice.equals("H")){
                            dealToHand(getPlayer(i), currentHand, numDecks);
                            if (isBust(getPlayer(i), currentHand)) {
                                continue;
                            }
                            i--;
                            continue;
                        } else if(playChoice.equals("D") || playChoice.equals("DS")){
                            dealToHand(getPlayer(i), currentHand, numDecks);
                            System.out.println("Double");
                            continue;
                        }
                    }
                }

                //Dealer plays
                boolean belowSeventeen = getTotalValue(getPlayer(0), 0) < 17;

                while (belowSeventeen) {
                    getPlayer(0).getHand(0).addCard(shoe.deal());
                    belowSeventeen = getTotalValue(getPlayer(0), 0) < 17;
                }
            }

            System.out.println("\n" + Arrays.toString(getPlayer(0).hands.toArray()));
            for(int i=1; i<players.size(); i++) {
                System.out.println(Arrays.toString(getPlayer(i).hands.toArray()));
            }
            System.out.println("");
        }

        public boolean isPair(Player p, int n){
            return p.getHand(n).getCard(0).getValue() == p.getHand(n).getCard(1).getValue()
                    || p.getHand(n).getCard(0).getRankAsString().equals(p.getHand(n).getCard(1).getRankAsString());
        }

        public String playChoice(int playerIndex, int dealerIndex) {
            return strategy.get(playerIndex).get(dealerIndex).toString();
        }

        //Checks whether a players hand is over 21
        public boolean isBust(Player p, int hand) {
            if (getTotalValue(p, hand) > 21) {
                p.getHand(hand).setWinCondition("lose");
                return true;
            }
            return false;
        }

        //Gets the total value of a players hand
        public int getTotalValue(Player p, int hand) {
            int totalValue = 0;

            if (numAces(p, hand) == 0) {
                totalValue += noAceHandValue(p, hand);
                return totalValue;
            }

            setAceValue(p, hand);

            for (int i = 0; i < p.getHand(hand).getSize(); i++) {
                totalValue += p.getHand(hand).getCard(i).getValue();
            }
            return totalValue;
        }

        //Gets the value of a players hand not including Aces
        public int noAceHandValue(Player p, int hand) {
            int totalValue = 0;

            for (int i = 0; i < p.getHand(hand).getSize(); i++) {
                if (p.getHand(hand).getCard(i).getRankAsString().equals("A")) {
                    continue;
                }
                totalValue += p.getHand(hand).getCard(i).getValue();
            }
            return totalValue;
        }

        //Determines and sets the value of each Ace in a players hand
        public void setAceValue(Player p, int hand) {
            ArrayList<Integer> indexes = aceIndex(p, hand);

            if (numAces(p, hand) > 1) {
                if (noAceHandValue(p, hand) > 11) {
                    for (int i = 0; i < indexes.size(); i++) {
                        p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(i)), 1);
                    }
                }
                if (noAceHandValue(p, hand) < 11) {
                    p.getHand(hand).setValue(p.getHand(hand).getCard(indexes.get(0)), 11);
                    if (noAceHandValue(p, hand) + p.getHand(hand).getCard(indexes.get(0)).getValue() < 21) {
                        for (int i = 1; i < indexes.size(); i++) {
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
        public ArrayList<Integer> aceIndex(Player p, int hand) {
            ArrayList<Integer> indexes = new ArrayList<>();

            for (int i = 0; i < p.getHand(hand).getSize(); i++) {
                if (p.getHand(hand).getCard(i).getRankAsString().equals("A")) {
                    indexes.add(i);
                }
            }
            return indexes;
        }

        //Returns the number of Aces in a players hand
        public int numAces(Player p, int hand) {
            int numAces = 0;
            for (int i = 0; i < p.getHand(hand).getSize(); i++) {
                if (p.getHand(hand).getCard(i).getRankAsString().equals("A")) {
                    numAces++;
                }
            }
            return numAces;
        }
    }
}
