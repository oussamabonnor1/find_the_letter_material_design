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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


public class MainWordController implements Initializable {

    @FXML
    private JFXButton submit;

    @FXML
    private Label state;

    @FXML
    private JFXTextField answer;

    @FXML
    private Label word;

    @FXML
    private Label lblscore;

    @FXML
    private ProgressBar Progress;

    @FXML
    private JFXButton closButton;

    @FXML
    private JFXButton minButton;

    @FXML
    private JFXButton back;

    @FXML
    private Label level;


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
        MainController.mainScore += score;
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
        score = 0;
        helpPoints = 0;

        try {
            stmt = connection.createStatement();
            sql = "UPDATE Manager " +
                    "   SET Help = " + helpPoints +
                    "   ,Score = " + score +
                    " WHERE id = 1;";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    int score = 0;
    boolean next = false;
    int currentLevel;
    int size;
    int helpPoints;

    ArrayList<String> dictionary1 = new ArrayList<>();
    Word first = new Word(1, score);
    int h = -1;
    Random d = new Random();

    public static Connection connection;
    Statement stmt;
    String sql;


    void reading() {
        //File file = new File(String.valueOf(Paths.get("com/company/File.txt")));
        File file = new File("src/com/company/dictionary/Word.txt");
        Scanner sc = null;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext()) {

            String s = sc.next();
            dictionary1.add(s);
        }
    }


    public void creatingWord() {

        if (h != -1) {
            first.dictionary.remove(h);
        } else {
            for (int i = 0; i < dictionary1.size(); i++) {
                first.dictionary.add(dictionary1.get(i));
            }
        }

        for (int i = 0; i < first.dictionary.size(); i++) {
            System.out.println(i + " " + first.dictionary.get(i));
        }


        first.wordLetters.clear();
        first.organizedCharacters.clear();
        h = d.nextInt(first.dictionary.size());
        first.testWord = first.dictionary.get(h);
        first.generateWord(first.decompose(first.testWord), d.nextInt(4));

        answer.setText("");
        word.setText("");
        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            word.setText(word.getText() + first.organizedCharacters.get(i));
        }

        String tranfer = word.getText();

        sql = "UPDATE Manager " +
                " SET Organised = " + "'" + tranfer + "'" +
                " ,Word = " + "'" + first.testWord + "'" +
                " WHERE id = 1;";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateWord() {

        word.setText("");
        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            word.setText(word.getText() + first.organizedCharacters.get(i));
        }

        String tranfer = word.getText();

        sql = "UPDATE Manager " +
                " SET Organised = " + "'" + tranfer + "'" +
                ",Score = " + score +
                ",Help = " + helpPoints +
                " WHERE id = 1;";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guess() {

        if (next == false) {
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #000000;-fx-alignment: center;");
                state.setText("Writte at least one letter !");
            } else {
                //in the guessingCharachter methode, insert the text in the textfield in lowercase
                if (first.guessingCharachter(answer.getText().toLowerCase(), 1)) {

                    if (first.organizedCharacters.equals(first.wordLetters)) {
                        //next button is activated
                        next = true;
                        submit.setText("Next");
                        state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                        state.setText("You finished this word !");
                        answer.setDisable(true);
                        Progress.setProgress(Progress.getProgress() + 0.1);
                        //sound effects
                        music(3);
                        score = first.getScore();
                        helpPoints += 5;
                        updateWord();

                    } else {
                        music(1);
                        state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                        state.setText("Good Job");
                        score = first.getScore();
                        updateWord();
                    }
                } else {
                    score = first.getScore();
                    //state is the label
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !");
                    music(2);
                    score = first.getScore();
                }
                //answer is the textfield
                answer.setText("");
            }
        } else {
            if (first.dictionary.size() <= 1) {
                submit.setDisable(true);
                word.setText("Congratulations!\nyou finished this category!");
                word.setAlignment(Pos.CENTER);
                word.setTextAlignment(TextAlignment.CENTER);
                word.setFont(Font.font("", FontWeight.BOLD, 25));
                answer.setDisable(true);
            } else if (Progress.getProgress() == 1) {
                //state is the label
                submit.setText("Check");
                state.setStyle("fx-text-fill:#000000;");
                state.setText("You Finished this Level !");
                currentLevel++;
                level.setText("level: " + currentLevel + "/" + size);
                //first.dictionary.remove(0);
                next = false;
                creatingWord();
                Progress.setProgress(0);
                answer.setDisable(false);
            } else {
                answer.setDisable(false);
                creatingWord();
                //submit is the button / check button gets activated
                next = false;
                submit.setText("Check");
                state.setText("");
            }
        }
        lblscore.setText("Points: " + score);
        System.out.println("-------------" + score);
    }

    @FXML
    void icheck(ActionEvent event) {
        guess();
        String str = Integer.toString(score);
        lblscore.setText(str + " Points");
    }

    @FXML
    void OnHelp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainHelpFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helpPoints = 0;
        score = 0;

        connection = MainController.connection;
        try {
            stmt = connection.createStatement();
            sql = "UPDATE Manager " +
                    "   SET Help = " + helpPoints +
                    "   ,Score = " + score +
                    " WHERE id = 1;";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        currentLevel = 1;
        state.setText("");
        Progress.setProgress(0);
        dictionary1.clear();
        reading();
        size = (dictionary1.size() / 10);
        level.setText("level: " + currentLevel + "/" + size);
        level.setFont(Font.font("", FontWeight.BOLD, 18));
        level.setTextAlignment(TextAlignment.CENTER);
        creatingWord();
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
