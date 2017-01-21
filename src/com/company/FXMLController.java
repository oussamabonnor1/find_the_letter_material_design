/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class FXMLController implements Initializable {
    @FXML
    private JFXButton submit;

    @FXML
    private Label state;

    @FXML
    private JFXTextField answer;
    @FXML
    private Label word;

    int score = 0;
    boolean next = false;

    String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck"};
    Word first = new Word(1, score, 0);
    int h = -1;


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
        first.testWord = first.dictionary.get(h-1);
        first.generateWord(first.decompose(first.testWord), d.nextInt(2));

        //make the textfield empty here
        answer.setText("");
        word.setText("");
        for (int i = 0; i < first.organizedCharacters.size(); i++) {

            word.setText(word.getText() + first.organizedCharacters.get(i));
        }

    }

    public void updateWord() {

        word.setText("");
        for (int i = 0; i < first.organizedCharacters.size(); i++) {

            word.setText(word.getText() + first.organizedCharacters.get(i));
        }
    }

    public void guess() {

        if (next ==false) {
            //answer is the textfield
            //checking if the textfield is empty
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #000000;-fx-alignment: center;");
                state.setText("Writte at least one letter !");
            } else {
                //in the guessingCharachter methode, insert the text in the textfield in lowercase
                if (first.guessingCharachter(answer.getText().toLowerCase())) {

                    if (first.organizedCharacters.equals(first.wordLetters)) {
                        //next button is activated
                        next = true;
                        submit.setText("Next");
                        //sound effects
                        // music(3);
                    } else {
                        // music(1);
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
                    //  music(2);
                }
                //answer is the textfield
                answer.setText("");
            }
        } else {
            if (first.dictionary.size() == 0) {
                //state is the label
                state.setStyle("fx-text-fill:#000000;");
                state.setText("You Finished this Level");
            } else {
                creatingWord();
                //submit is the button / check button gets activated
                next = false;
                submit.setText("Check");
            }
        }

    }

    @FXML
    void icheck(ActionEvent event) {
        guess();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state.setText("");
        creatingWord();
    }

}
