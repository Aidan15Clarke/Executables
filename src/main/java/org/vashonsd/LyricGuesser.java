package org.vashonsd;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class LyricGuesser implements Executable {
    @Override
    public void start(InputStream is, PrintStream ps) {
        Scanner scan = new Scanner(is);
        ps.println("How difficult would you like your lyric to be to guess? Enter 'level1', 'level2' or 'level3' ");
        String lyricChoice = scan.nextLine();
        if (lyricChoice.length()==6 && lyricChoice.substring(0,6).equals("level1")){
            ps.println("Enter the missing word");
            ps.println("Standing here waiting at you back door, been here all along so why can't you see you ______ with me");
            String levelOneGuess = scan.nextLine();
            if (levelOneGuess.length()<6 || levelOneGuess.length()>6){
                ps.println("Wrong");
            }
            if (levelOneGuess.length()==6 && levelOneGuess.substring(0,6).equals("belong")){
                ps.println("You are correct");
            }
            if ( levelOneGuess.length()==6 && levelOneGuess.charAt(0) != 'b'){
                ps.println("Wrong");
            }
        }

        if (lyricChoice.length()==6 && lyricChoice.substring(0,6).equals("level2")){
            ps.println("Enter the missing word");
            ps.println("Nice to meet you where you've been I could show you __________ things");
            String levelTwoGuess = scan.nextLine();
            if (levelTwoGuess.length()<10 || levelTwoGuess.length()>10){
                ps.println("Wrong");
            }
            if (levelTwoGuess.length()==10 && levelTwoGuess.substring(0,10).equals("incredible")){
                ps.println("You are correct");
            }
            if ( levelTwoGuess.length()==6 && levelTwoGuess.charAt(0) != 'i'){
                ps.println("Wrong");
            }
        }

        if (lyricChoice.length()==6 && lyricChoice.substring(0,6).equals("level3")) {
            ps.println("Enter the missing word");
            ps.println("The idea you had of me who was she an ever needing, ever lovely _____ whose shine reflects on you.");
            String guess = scan.nextLine();
            if (guess.length() < 5 || guess.length() > 5) {
                ps.println("Wrong");
            }
            if (guess.length() == 5 && guess.substring(0, 5).equals("jewel")) {
                ps.println("You are correct");
            }
            if (guess.length() == 5 && guess.charAt(0) != 'j') {
                ps.println("Wrong");
            }
        }
    }

    @Override
    public String getName() {
        return "LyricGuesser";
    }

    @Override
    public String getDescription() {
        return "guess the lyric";
    }

    @Override
    public String getAuthor() {
        return "JL";
    }
}

