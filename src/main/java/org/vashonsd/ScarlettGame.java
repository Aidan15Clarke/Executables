package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ScarlettGame implements Executable {
    private String name;
    private String description;
    private String author;

    @Override
    public void start(InputStream is, PrintStream ps) {
        Scanner scan = new Scanner(is);
        ps.println("Hello. Enter any word to continue");
        String userResponse = scan.nextLine();
        int wordLength = userResponse.length();
        ps.println("Your word has " + wordLength + " letters.");
        if(wordLength >= 10){
            ps.println("You win! Nice word.");
        }else{
            ps.println("Yikes, you lose. Maybe try entering a cooler word next time!");
        }
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
