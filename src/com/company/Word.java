package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by user on 10/03/2016.
 */
public class Word {
    //variables:
    //index: display wich mode its about...
    private int index;
    //score: obvious
    private int score;
    //dictionary: contains the words generated in this game
    ArrayList<String> dictionary = new ArrayList<>();
    //help: a counter to determine if the player needs any help, further explanation in the method "help"
    private int help;

    //variables needed in methods only:
    String testWord;
    ArrayList<Character> organizedCharacters = new ArrayList<>();
    ArrayList<Character> wordLetters = new ArrayList<>();
    Scanner r = new Scanner(System.in);
    String responce;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHelp() {
        return help;
    }

    public void setHelp(int help) {
        this.help = help;
    }

    //constructor: this will be used to determmine each level
    public Word(int index, int score, int help) {
        this.index = index;
        this.score = score;
        this.help = help;
    }


    //transform a word into a pile of letters
    ArrayList<Character> decompose(String word) {
        for (int i = 0; i < testWord.length(); i++) {
            wordLetters.add(i, word.charAt(i));
        }
        return wordLetters;
    }

    //creates and shows the word in a pattern generated randomly
    ArrayList<Character> generateWord(ArrayList<Character> decomposedWord, int startIndex) {
        /*
            "oussama"
            level 1:
            if startIndex == 0 ====> "o-s-a-a"
            if startIndex == 1 ====> "-u-s-m-"
            level 2:
            if startIndex == 0 ====> "-u--am-"
            if startIndex == 1 ====> "o----ma"
         */
        if (index == 1) {
            if (startIndex == 0) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i % 2 == 0) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }

            if (startIndex == 1) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i % 2 != 0) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }
            if (startIndex == 2) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i == 1 || i == 4 || i == 5 || i == 8 || i == 9) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }

            if (startIndex == 3) {
                for (int i = 0; i < decomposedWord.size(); i++) {
                    if (i == 0 || i == 5 || i == 6 || i == 7 || i == 11) {
                        organizedCharacters.add(i, decomposedWord.get(i));
                    } else {
                        organizedCharacters.add(i, '-');
                    }
                }
            }
        }
        //for level two:
        if (index == 2) {
            int hiden = new Integer(new Random().nextInt(decomposedWord.size()));
            for (int i = 0; i < decomposedWord.size(); i++) {
                if (i == hiden) {
                    organizedCharacters.add(i, decomposedWord.get(i));
                } else {
                    organizedCharacters.add(i, '-');
                }
            }
        }
        return organizedCharacters;

    }

    public boolean guessingCharachter(String c) {

        boolean valeur = false;

        //one letter
        if (c.length() == 1) {
            //letter is correct and not already entered
            if (wordLetters.contains(c.charAt(0)) && !organizedCharacters.contains(c.charAt(0))) {
                valeur = true;
                for (int i = 0; i < wordLetters.size(); i++) {
                    //each time the letter s found
                    if (wordLetters.get(i).equals(c.charAt(0))) {
                        organizedCharacters.set(i, c.charAt(0));
                        score+=5;
                    }
                }

            }
            //not one letter, word smaller or greater then the presented word
        } else if (c.length() < wordLetters.size() || c.length() > wordLetters.size()) {
            valeur = false;
            score-=3;
        } else {
            //word entered is same size as word presented
            for (int i = 0; i < wordLetters.size(); i++) {
                //is it the same letter -> word ?
                if (wordLetters.get(i).equals(c.charAt(i))) {
                    valeur = true;
                } else {
                    if(score > 0) score-=3;
                    return false;
                }
            }
            //if yes , show new word
            if (valeur) {
                score+=5;
                for (int i = 0; i < wordLetters.size(); i++) {
                    organizedCharacters.set(i, c.charAt(i));
                }
            }

        }
        return valeur;
    }

    //help method 1:
    public boolean help1() {
        boolean helpval = true;
        //each time the user gets 3 errors, this will appear
        if (help >= 3) {
            System.out.println("*******seems like you re having some troubles*******");
            System.out.println("do you need help ? (y/n)");
            responce = r.next();


            //if the user wants help:
            if (responce.equals("y") || responce.equals("yes")) {
                System.out.println("okay, show the entier word(for 50 points) ? (y/n)");
                responce = r.next();
                if (responce.equals("y") || responce.equals("yes")) {
                    //helping
                    if (score >= 50) {
                        System.out.print("that word was: ");
                        for (int i = 0; i < wordLetters.size(); i++) {
                            System.out.print(wordLetters.get(i));
                        }
                        help = 0;
                        helpval = false;
                        score -= 50;
                        System.out.println(" your new score is " + score);
                        score -= 100;
                    } else System.out.println("*******sorry,you don't have enough points*******");
                }
                //end of helping...
            }
            //if that bitch thinks he s smart enough:
            else {
                System.out.println("very well..good luck");
                help = 0;
            }
        }
        return helpval;
    }

}
