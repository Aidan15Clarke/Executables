package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 *
 */
public class App 
{
    public static Map<String, Executable> executables = new HashMap<>();
    static InputStream input;
    static PrintStream output;

    public static void main( String[] args )
    {
        input = System.in;
        output = System.out;
        addExecutable(new bruhleImpl());
        addExecutable(new HorridGame());
        addExecutable(new game());
        printOptions();
        Scanner scan = new Scanner(input);

        Executable e = null;
        while(e == null) {
            String userChoice = null;
            while(userChoice == null) {
                output.println("Which one do you want to execute?");
                userChoice = scan.nextLine();
                if(!executables.containsKey(userChoice)) {
                    userChoice = null;
                } else {
                    e = executables.get(userChoice);
                }
            }
            e.start(input, output);
            e = null;
        }

    }

    public static void printOptions() {
        for (Executable e : executables.values()) {
            output.println(e.getName()
                    + ": "
                    + e.getDescription()
                    + " (" + e.getAuthor() + ")");
        }
    }
    public static void addExecutable(Executable e) {
        executables.put(e.getName(), e);
    }
}
