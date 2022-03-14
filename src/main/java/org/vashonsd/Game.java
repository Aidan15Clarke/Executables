package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {

        Scanner scan = new Scanner(is);
        int secretNumber = 1 + (int) (Math.random()* 99);
        int userResponse =-1;
        int numGuesses = 0;
        ps.println("Welcome to Guess the number!");
        while (userResponse != secretNumber && numGuesses <=20) {
            System.out.println("Guess a number in between 1-100.");
            userResponse = scan.nextInt();
            numGuesses += 1;
            if(userResponse < secretNumber )
            {
                System.out.println("Your number is too low try again.");
            }
            else if(userResponse > secretNumber)
            {
                System.out.println("Your number is too high try again.");
            }
        }
        if(numGuesses > 20)
        {
            System.out.println("You guessed to many times.");
        }
        else
        {
            System.out.println("Congratulations you guessed the right number!");
        }
    }

    @Override
    public String getName() {
        return "Guess the number";
    }

    @Override
    public String getDescription() {
        return "Guess a number between 1-100";
    }

    @Override
    public String getAuthor() {
        return "SP";
    }
}
