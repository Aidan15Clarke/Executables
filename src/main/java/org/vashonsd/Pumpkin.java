package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Pumpkin implements Executable {
    private String name;
    private String description;
    private String author;

    @Override
    public void start(InputStream is, PrintStream ps) {
        Scanner input = new Scanner(is);
        ps.println("What is your favorite fruit?");
        String fruit = input.nextLine();
        String response = "";
        if (fruit.startsWith("a")||fruit.startsWith("e")||fruit.startsWith("i")||fruit.startsWith("o")||fruit.startsWith("u"))
        {
            response = fruit + "way";
        }
        else
        {
            response = fruit.substring(1)+fruit.substring(0,1) + "ay";
        }
        ps.println(response);

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
