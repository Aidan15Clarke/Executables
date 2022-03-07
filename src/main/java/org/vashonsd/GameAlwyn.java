package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import static java.util.concurrent.TimeUnit.SECONDS;


public class GameAlwyn implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {

        Scanner scan = new Scanner(System.in);

        int p1Guess = -1;
        int p2Guess = -1;
        ps.println("This is Guess the random number its a two player game and whoever wins will get a prize!");
        try {
            SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (p1Guess < 0){
            ps.println("Player one guess a positive number:");
            try {
                p1Guess = scan.nextInt();
            } catch (Exception e) {
                ps.println("Try Again");
                p1Guess = -1;
                scan.nextLine();
            }
        }

        while (p2Guess < 0){
            ps.println("Player two guess a positive number:");
            try {
                p2Guess = scan.nextInt();
            } catch (Exception e) {
                ps.println("bro buggin... try that again");
                p1Guess = -1;
                scan.nextLine();
            }
        }


        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ps.println("READY");
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ps.println("SET");
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int u = 0;
        int i = 0;
        while (i < 9) {
            ps.println(u);
            Random rand = new Random();
            i = rand.nextInt(10);
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            u ++;



        }

        double closer = Math.abs(u - p1Guess) < Math.abs(u - p2Guess) ? p1Guess : p2Guess;

        if(closer == p1Guess) {
            ps.println("THE NUMBER IS " + u + "! PLAYER 1 WINS WITH THE GUESS OF " + (int)closer);
        }
        else {
            ps.println("THE NUMBER IS " + u + "! PLAYER 2 WINS WITH THE GUESS OF " + (int)closer);
        }

    }
    @Override
    public String getName() {
        return "rando!";
    }

    @Override
    public String getDescription() {
        return "a custom number randomiser game ";
    }

    @Override
    public String getAuthor() {
        return "AHS";
    }
}
