package org.vashonsd;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
public class RocketShip implements Executable {

    private String name;
    private String description;
    private String author;
    @Override
    public void start(InputStream is, PrintStream ps) {
        ps.println("In order to fly the ship we need you to do some calculations! Solve the math problems to get to space!");

        int i = 0;
       Timer time = new Timer();
       while(i < 5)  {
           int x = (int) (Math.random()*10);
           int y = (int) (Math.random()*100);
           int answer = x * y;
           ps.println("Quick multiply " + x + " and " + y);
           Scanner scan = new Scanner(is);
           int guess = scan.nextInt();
            if (guess == answer) {
                ps.println("Nice job! Here comes another one!");
                i++;
            }
            if (guess != answer){
                ps.println("Yikes, we didn't make it.");
                i = -1;
            }
       }
       ps.println("Wow you saved us! Good job your a real math wiz!");


    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getAuthor() {
        return author;
    }
}
