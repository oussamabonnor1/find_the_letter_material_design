package com.company;

import java.util.ArrayList;
import java.util.Random;

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

    //variables needed in methods only:
    String testWord;
    ArrayList<Character> organizedCharacters = new ArrayList<>();
    ArrayList<Character> wordLetters = new ArrayList<>();


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

    //constructor: this will be used to determmine each level
    public Word(int index, int score) {
        this.index = index;
        this.score = score;
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

    public boolean guessingCharachter(String c, int type) {

        boolean valeur = false;

        //one letter
        if (c.length() == 1) {
            //if the letter already mentioned ?
            if (wordLetters.contains(c.charAt(0)) && organizedCharacters.contains(c.charAt(0))) {
                int count = 0;
                //is the letter present more than once ?
                for (int i = 0; i < wordLetters.size(); i++) {
                    if (wordLetters.get(i).equals(c.charAt(0))) count++;
                }
                if (count > 1) {
                    //if it s present twice or more, is it present in different place in organised and word
                    for (int i = 0; i < organizedCharacters.size(); i++) {
                        if (wordLetters.get(i).equals(c.charAt(0)) && !organizedCharacters.get(i).equals(c.charAt(0))) {
                            organizedCharacters.set(i, c.charAt(0));
                            if (type == 2) score += 5;
                            else score += 3;
                            valeur = true;
                        }
                    }
                }
            }
            //letter is correct and not already entered
            if (wordLetters.contains(c.charAt(0)) && !organizedCharacters.contains(c.charAt(0))) {
                valeur = true;
                for (int i = 0; i < wordLetters.size(); i++) {
                    //each time the letter s found
                    if (wordLetters.get(i).equals(c.charAt(0))) {
                        organizedCharacters.set(i, c.charAt(0));
                        if (type == 2) score += 5;
                        else score += 3;
                    }
                }

            }
            //not one letter, word smaller or greater then the presented word
        } else if (c.length() < wordLetters.size() || c.length() > wordLetters.size()) {
            valeur = false;
        } else {
            //word entered is same size as word presented
            int delta = 0;
            for (int i = 0; i < wordLetters.size(); i++) {
                //is it the same letter -> word ?
                if (wordLetters.get(i).equals(c.charAt(i))) {
                    valeur = true;
                    if (!organizedCharacters.get(i).equals(c.charAt(i))) delta++;
                } else {
                    return false;
                }
            }
            if (type == 2) score += delta * 5;
            else score += delta * 3;
            //if yes , show new word
            if (valeur) {
                for (int i = 0; i < wordLetters.size(); i++) {
                    organizedCharacters.set(i, c.charAt(i));
                }
            }

        }
        return valeur;
    }

    //help method 1:
    public String help1(String organised, String word, int choice) {
        String found = null;

        if (choice == 2) {
            Random r = new Random();
            int letter;
            do {
                letter = r.nextInt(word.length());
            } while (!(organised.charAt(letter) =='-'));

            /*System.out.println(organised);
            char[] test = organised.toCharArray();
            for (int i = 0; i < test.length; i++) {
                System.out.println(test[i]);
            }
            System.out.println(test[letter]);
            test[letter] = word.charAt(letter);
            System.out.println(test[letter]);

            organised.charAt(test[letter]);*/

            found = String.valueOf(word.charAt(letter));

        }

        return found;
    }
}
