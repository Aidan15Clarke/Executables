package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class bruhleImpl implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {
        String yourGuess = "";

        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your guess: ");
        int num = scan.nextInt();


        if (num == 1) {
            yourGuess = "Rock";
        }
        if (num == 2) {
            yourGuess = "Paper";
        }
        if (num == 3) {
            yourGuess = "Scissors";
        }
        System.out.println("You guessed " + yourGuess);

        int computerGuess = rand.nextInt(3);
        computerGuess += 1;
        String computer = "";

        if (computerGuess == 1) {
            computer = "Rock";
        }
        if (computerGuess == 2) {
            computer = "Paper";
        }
        if (computerGuess == 3) {
            computer = "Scissors";

        }

        System.out.println("I guessed " + computer);


        if (computer.equals("Rock") && yourGuess.equals("Scissors")) {
            System.out.println("You lose");
        }
        if (computer.equals("Rock") && yourGuess.equals("Paper")) {
            System.out.println("You win");
        }
        if (computer.equals("Rock") && yourGuess.equals("Rock")) {
            System.out.println("You tied, nice job dipshit");
        }
        if (computer.equals("Scissors") && yourGuess.equals("Rock")) {
            System.out.println("You win");
        }
        if (computer.equals("Scissors") && yourGuess.equals("Paper")) {
            System.out.println("You lose");
        }
        if (computer.equals("Scissors") && yourGuess.equals("Scissors")) {
            System.out.println("You tied, nice job ya twat");
        }
        if (computer.equals("Paper") && yourGuess.equals("Rock")) {
            System.out.println("You lose");
        }
        if (computer.equals("Paper") && yourGuess.equals("Scissors")) {
            System.out.println("You win");
        }
        if (computer.equals("Paper") && yourGuess.equals("Paper")) {
            System.out.println("You tied, nice job dumbass");
        }

        System.out.println("press y to play again");
        String playAgain = scan.next();

        if (playAgain.equals("y")) {
            start(is,ps);
        }
    }

    @Override
    public String getName() {
        return "simple rps";
    }

    @Override
    public String getDescription() {
        return "an implementation of rock paper scissors";
    }

    @Override
    public String getAuthor() {
        return "MSP";
    }
}

