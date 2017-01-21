package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application implements EventHandler<ActionEvent> {

    int score = 0;

    String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck"};
    Word first = new Word(1, score, 0);
    int h = -1;

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void creatingWord() {
        Random d = new Random();

        if (h != -1) {
            for (int i = h; i < first.dictionary.size(); i++) {
                first.dictionary.remove(i);
            }
        } else {
            for (int i = 0; i < dictionary1.length; i++) {
                first.dictionary.add(dictionary1[i]);
            }
        }

        for (int i = 0; i < first.dictionary.size(); i++) {
            System.out.println(first.dictionary.get(i));
        }


        first.wordLetters.clear();
        first.organizedCharacters.clear();
        h = d.nextInt(dictionary1.length);
        first.testWord = first.dictionary.get(h);
        first.generateWord(first.decompose(first.testWord), d.nextInt(2));

        //make the textfield empty here
        // textfield.setText("");

       updateWord();

    }

    public void updateWord() {

        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            //textfield receives getTextField + the new letter (previous + new)
            // word.setText(word.getText() + first.organizedCharacters.get(i));
        }
    }

    // level two: i mean seriously guys, some graphics would be heaven now -_-
    //i learned graphics old me..great job


    public void guess() {
        //just in case things go bad
        //submit is the button

        if (submit.getText() == "Check") {
            //answer is the textfield
            //checking if the textfield is empty
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
                state.setText("Writte at least one letter");
            } else {
                //in the guessingCharachter methode, insert the text in the textfield in lowercase
                if (first.guessingCharachter(answer.getText().toLowerCase())) {

                    if (first.organizedCharacters.equals(first.wordLetters)) {
                       //next button is activated
                        submit.setText("Next");
                        //sound effects
                        music(3);
                    } else {
                        music(1);
                        //modifie the word, automaticaly
                        updateWord();
                    }
                    //state is the label
                    score = first.getScore();
                    state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                    state.setText("Good Job, new Score: " + score);
                } else {
                    score = first.getScore();
                    //state is the label
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !\nnew Score: " + score);
                    music(2);
                }
                //answer is the textfield
                answer.setText("");
            }
        } else {
            if (first.dictionary.size() == 0) {
                //state is the label
                state.setStyle("fx-text-fill:#FFFFFF;");
                state.setText("You Finished this Level");
            } else {
                creatingWord();
                //submit is the button / check button gets activated
                submit.setEnabeled(true);
            }
        }

    }

    @Override
    public void handle(ActionEvent event) {
        //submit is the check button
        if (event.getSource() == submit) {
            guess();
        }
    }

    public void music(int i) {
        AudioPlayer player = AudioPlayer.player;
        AudioData data;
        AudioDataStream output = null;
        AudioStream background = null;

        try {
            if (i == 1) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\Documents\\GitHub\\find_the_letter\\src\\Correct-answer.wav"));
            }
            if (i == 2) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\Documents\\GitHub\\find_the_letter\\src\\Wrong-answer-sound-effect.wav"));
            }
            if (i == 3) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\Documents\\GitHub\\find_the_letter\\src\\Next-Level-Sound.wav"));
            }

            data = background.getData();
            output = new AudioDataStream(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start(output);
    }

}

