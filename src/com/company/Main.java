package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //variables declaration
        Scanner r = new Scanner(System.in);
        Random d = new Random();
        int answer;
        String name;
        boolean decison = true;
        Tutorial read = new Tutorial();

        //where shit goes down
        System.out.println("=====MENU=====\n1-tutorial\n2-start game");
        answer = r.nextInt();
        if (answer == 1) {
            //tutorial choice:
            read.tutorial();
        }
        System.out.print("enter your name to start: ");
        name = r.next();
        System.out.println("=====level 1=====");

        String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck"};
        Level first = new Level(1, 0, dictionary1, 0);
        while (first.getScore()< 300) {
            first.testWord = first.dictionary[d.nextInt(7)];
            first.generateWord(first.decompose(first.testWord), d.nextInt(2));
            System.out.println("the word in game is:");

            for (int i = 0; i < first.organizedCharacters.size(); i++) {
                System.out.print(first.organizedCharacters.get(i));
            }
            System.out.println("");
            while (!first.organizedCharacters.equals(first.wordLetters) && decison == true) {
                System.out.println("enter a letter");
                first.guessingCharachter(r.next().charAt(0));
                decison = first.help1();
            }
            first.setScore(first.getScore()+100);
            System.out.println("great job " + name + " you finished a word! your score now is:" + first.getScore());
            first.organizedCharacters.clear();
            first.wordLetters.clear();
            first.setHelp(0);
            decison = true;
        }
        System.out.println(name + " you finished level 1! with a score of " + first.getScore());
        System.out.println("=====second level=====");

        // level two: i mean seriously guys, some graphics would be heaven now -_-

        String[] dictionary2 = {"player", "nature", "extra", "drive", "guitar", "tragic", "creation"};
        Level second = new Level(2, first.getScore(), dictionary2, 0);

        while (second.getScore() < 600) {
            second.testWord = second.dictionary[d.nextInt(7)];
            second.generateWord(second.decompose(second.testWord), d.nextInt(2));
            System.out.println("the word in game is:");

            for (int i = 0; i < second.organizedCharacters.size(); i++) {
                System.out.print(second.organizedCharacters.get(i));
            }
            System.out.println("");
            while (!second.organizedCharacters.equals(second.wordLetters) && decison == true) {
                System.out.println("enter a letter");
                second.guessingCharachter(r.next().charAt(0));
                decison = second.help1();
            }
            second.setScore(first.getScore()+120);
            System.out.println("great job " + name + " you finished a word! your score now is:" + second.getScore());
            second.organizedCharacters.clear();
            second.wordLetters.clear();
            second.setHelp(0);
            decison = true;
        }
        System.out.println("========congratulations!!! " + name + " you have beaten the game !========");
    }
}
