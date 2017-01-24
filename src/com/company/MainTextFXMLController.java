/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class MainTextFXMLController implements Initializable {

    @FXML
    private Label lblscore;

    @FXML
    private JFXButton back;

    @FXML
    private ProgressBar Progress;

    @FXML
    private JFXButton closButton;

    @FXML
    private JFXButton minButton;

    @FXML
    private Label word;

    @FXML
    private JFXButton submit;

    @FXML
    private Label state;

    @FXML
    private JFXTextField answer;

    @FXML
    private Label level;

    boolean next;
    ArrayList<String> hints = new ArrayList<>();
    ArrayList<String> dictionary = new ArrayList<>();
    ArrayList<Integer> used = new ArrayList<>();
    int score;
    int currentLevel;
    int size;
    int h;
    Random d = new Random();

    @FXML
    void OnBack(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    void OnMinimiz(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);
    }

    @FXML
    void closewindow(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    void icheck(ActionEvent event) {

        if (!next) {
            //EMPTY
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #000000;-fx-alignment: center;");
                state.setText("Writte at least one letter !");
            } else {
                //NOT EMPTY AND CORRECT
                if (answer.getText().toLowerCase().equals(dictionary.get(h))) {
                    next = true;
                    submit.setText("Next");
                    state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                    state.setText("You guessed this word !");
                    answer.setDisable(true);
                    answer.setDisable(true);
                    Progress.setProgress(Progress.getProgress() + 0.2);
                    score += 15;
                    music(1);
                } else {
                    //NOT CORRECT
                    score -= 3;
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !");
                    music(2);
                }
            }

            //NEXTING AND DICTIONARY IS USED UP
        } else {
            if (used.size() == dictionary.size() - 1) {
                submit.setDisable(true);
                state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                state.setText("you finished them all !");
                answer.setDisable(true);
            } else if (Progress.getProgress() == 1) {
                //DICTIONARY STILL AND PROGRESS BAR USED UP (LEVEL OVER)
                submit.setText("Check");
                state.setStyle("fx-text-fill:#000000;");
                state.setText("You Finished this Level !");
                music(3);
                currentLevel++;
                answer.setDisable(false);
                level.setText("level: " + currentLevel + "/" + size);
                next = false;
                Progress.setProgress(0);
                deletingHint();
                creatingHint();
            } else {
                next = false;
                submit.setText("Check");
                answer.setDisable(false);
                state.setText("");
                deletingHint();
                creatingHint();
            }
            answer.setText("");
            lblscore.setText(score + "Points");
        }
    }
    
 @FXML
    void OnHelp(ActionEvent event) {

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        word.setFont(Font.font("", 30));
        score = 0;
        h = -1;
        next = false;
        Progress.setProgress(0);
        currentLevel = 1;
        state.setText("");
        hints.clear();
        dictionary.clear();
        used.clear();
        reading();
        creatingHint();
        size = dictionary.size() / 5;
        level.setText("Level: " + currentLevel + "/" + size);
    }

    void creatingHint() {
        do {
            h = d.nextInt(dictionary.size());
        } while (used.contains(h));

        answer.setText("");
        word.setText(hints.get(h));
        System.out.println(hints.get(h) + "\n" + dictionary.get(h));
    }

    void deletingHint() {
        used.add(h);
    }


    void reading() {
        //File file = new File(String.valueOf(Paths.get("com/company/File.txt")));
        File hint = new File("src/com/company/Text/hints.txt");
        Scanner sc = null;
        String s;
        try {
            sc = new Scanner(hint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            hints.add(s);
        }
        //words addition
        File word = new File("src/com/company/Text/words.txt");
        sc = null;
        try {
            sc = new Scanner(word);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext()) {
            s = sc.next();
            dictionary.add(s);
        }
    }

    public void music(int i) {
        AudioPlayer player = AudioPlayer.player;
        AudioData data;
        AudioDataStream output = null;
        AudioStream background = null;

        try {
            if (i == 1) {
                background = new AudioStream(new FileInputStream(new File("src/com/company/sound/Correct-answer.wav")));
            }
            if (i == 2) {
                background = new AudioStream(new FileInputStream("src/com/company/sound/Wrong-answer-sound-effect.wav"));
            }
            if (i == 3) {
                background = new AudioStream(new FileInputStream("src/com/company/sound/Next-Level-Sound.wav"));
            }

            data = background.getData();
            output = new AudioDataStream(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start(output);
    }
}
