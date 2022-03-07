package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GameGame implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {
    Scanner scan = new Scanner(is);
    int num = (int) (Math.random() * 10.0D + 1.0D);
    int userGuess = -1;
    int userGuesses = 0;
        while (userGuess != num) {
            ps.println("Guess a number from 1 to 10:");
            userGuess = scan.nextInt();
            if (userGuess > num) {
                ps.println("The number is too big!");
                userGuesses++;
            } else if (userGuess < num) {
                ps.println("The number is too small!");
                userGuesses++;
            }
        }
        if (userGuesses <= 5) ps.println("Congratulations.");
        else ps.println("Took you awhile for only 10 numbers, eh?");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getAuthor() {
        return null;
    }
}
