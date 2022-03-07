package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class JerrysGame implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {
        Scanner scanner = new Scanner(is);
        String crimes = "";
        ps.println("What is your full name?");
        String fullName = scanner.nextLine();
        ps.println("What is your height? (format: feet' inches");
        String height = scanner.nextLine();
        ps.println("What is your age?");
        String age = scanner.nextLine();
        ps.println("Have you committed a crime?");
        String ans = scanner.nextLine();

        if(ans.equals("yes")){
            ps.println("Enter your crimes as a comma seperated list");
            crimes = scanner.nextLine();
        }

        if(ans.equals("no")){
            ps.println("What a good citizen");
            crimes = "";
        }

        ps.println("Here is your police profile");
        ps.println(fullName + ":" + " " + crimes);
        ps.println(age + " years old, " + height + " tall");




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
