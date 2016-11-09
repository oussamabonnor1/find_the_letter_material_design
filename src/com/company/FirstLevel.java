package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by user on 29/02/2016.
 */
public class FirstLevel {
    //declaration of variables
    static int help1 = 0;
    static int score1 = 0;
    static String testWord;
    static ArrayList<Character> organizedCharacters = new ArrayList<>();
    static ArrayList<Character> wordLetters = new ArrayList<>();
    static Scanner r = new Scanner(System.in);
    static String responce1;
    // the local dictionary
    static String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck"};

    //methods and shit--------
    //transform a word into a pile of letters
    static ArrayList<Character> decompose(String word) {
        for (int i = 0; i < testWord.length(); i++) {
            wordLetters.add(i, word.charAt(i));
        }
        return wordLetters;
    }

    //creates and shows the word in a pattern generated randomly
    static ArrayList<Character> generateWord(ArrayList<Character> decomposedWord, int startIndex) {
        /*
            "oussama"
            if startIndex == 0 ====> "o-s-a-a"
            if startIndex == 1 ====> "-u-s-m-"

         */

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
        return organizedCharacters;
    }

    public static boolean guessingCharachter(Character c) {
        boolean valeur = true;
        //correct answer:
        if (wordLetters.contains(c) && organizedCharacters.contains(c) == false) {
            valeur = true;
            int g = wordLetters.indexOf(c);
            organizedCharacters.set(g, c);
            System.out.println("that s correct!\nthe new word is now :");
            for (int i = 0; i < organizedCharacters.size(); i++) {
                System.out.print(organizedCharacters.get(i));
            }
            score1 += 5;
            System.out.println("\nyour new score is " + score1);
        }
        //wrong answer:
        else if (wordLetters.contains(c) == false || organizedCharacters.contains(c)) {
            System.out.println("answer uncorrect, please try again...");
            valeur = false;
            score1 -= 1;
            System.out.println("\nyour new score is " + score1);
            help1++;
        }
        return valeur;
    }

    //help method 1:
    public static boolean help1() {
        boolean helpval = true;
        //each time the user gets 3 errors, this will appear
        if (help1 >= 3) {
            System.out.println("*******seems like you re having some troubles*******");
            System.out.println("do you need help ? (y/n)");
            responce1 = r.next();


            //if the user wants help:
            if (responce1.equals("y") || responce1.equals("yes")) {
                System.out.println("okay, show the entier word(for 50 points) ? (y/n)");
                responce1 = r.next();
                if (responce1.equals("y") || responce1.equals("yes")) {
                    //helping
                    if (score1 >= 50) {
                        System.out.print("that word was: ");
                        for (int i = 0; i < wordLetters.size(); i++) {
                            System.out.print(wordLetters.get(i));
                        }
                        help1 = 0;
                        helpval = false;
                        score1 -= 50;
                        System.out.println(" your new score is " + score1);
                        score1-=100;
                    } else System.out.println("*******sorry,you don't have enough points*******");
                }
                //end of helping...
            }


            //if that bitch thinks he s smart enough:
            else {
                System.out.println("very well..good luck");
                help1 = 0;
            }
        }
        return helpval;
    }

}


