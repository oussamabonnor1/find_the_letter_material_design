/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.geometry.Pos;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
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
public class MainImagesFXMLController implements Initializable {


    @FXML
    private JFXButton submit;

    @FXML
    private Label state;

    @FXML
    private JFXTextField answer;
    @FXML
    private Label word;

    @FXML
    private ProgressBar Progress;

    @FXML
    private JFXButton closButton;

    @FXML
    private JFXButton minButton;
    @FXML
    private JFXButton back;

    @FXML
    private ImageView view;

    @FXML
    private Label lblscore;

    @FXML
    private Label level;

    Image image;
    int currentImage;
    ArrayList<String> dictionary1 = new ArrayList<>();
    ArrayList<Integer> used = new ArrayList<>();
    Random random = new Random();
    boolean next;
    int score;
    int size;
    int currentLevel;

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

        //CHECKING
        if (!next) {
            //EMPTY
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #000000;-fx-alignment: center;");
                state.setText("Writte at least one letter !");
            } else {
                //NOT EMPTY AND CORRECT
                if (answer.getText().toLowerCase().equals(dictionary1.get(currentImage))) {
                    next = true;
                    submit.setText("Next");
                    state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                    state.setText("You guessed this word !");
                    answer.setDisable(true);
                    answer.setDisable(true);
                    Progress.setProgress(Progress.getProgress() + 0.2);
                    score += 15;
                    //sound effects
                    music(1);
                } else {
                    //NOT CORRECT
                    score -= 3;
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !");
                    music(2);
                    answer.setText("");
                }
            }
        } else {
            //NEXTING AND DICTIONARY IS USED UP
            if (used.size() == dictionary1.size()-1) {
                submit.setDisable(true);
                state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                state.setText("you finished them all !");
                answer.setDisable(true);
            } else if (Progress.getProgress() == 1) {
                //DICTIONARY STILL AND PROGRESS BAR USED UP 5LEVEL OVER°
                //state is the label
                submit.setText("Check");
                state.setStyle("fx-text-fill:#000000;");
                state.setText("You Finished this Level !");
                currentLevel++;
                music(3);
                answer.setDisable(false);
                level.setText("level: " + currentLevel + "/" + size);
                next = false;
                Progress.setProgress(0);
                deletingImage();
                try {
                    creatImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                answer.setText("");
            } else {
                next = false;
                submit.setText("Check");
                answer.setDisable(false);
                state.setText("");
                answer.setText("");
                deletingImage();
                try {
                    creatImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        lblscore.setText(score + "Points");
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

    void creatImage() throws FileNotFoundException {
        do {
            currentImage = random.nextInt(dictionary1.size());
        } while (used.contains(currentImage));
        image = new Image(new FileInputStream(new File(String.valueOf(Paths.get("src/com/company/images/" + String.valueOf(currentImage) + ".png")))));//, 200, 150, false, false);
        //image= new Image(new FileInputStream(new File("C:\\Users\\Oussama\\IdeaProjects\\find_the_letter_material_design\\src\\com\\company\\images\\1.png")));
        view.setImage(image);
    }

    void deletingImage() {
        used.add(currentImage);
    }

    void reading() {
        //File file = new File(String.valueOf(Paths.get("com/company/File.txt")));
        File file = new File("src/com/company/images/words.txt");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        score = 0;
        next = false;
        Progress.setProgress(0);
        currentLevel = 1;
        state.setText("");
        used.clear();
        dictionary1.clear();
        reading();
        size = dictionary1.size() / 5;
        level.setText("Level: " + currentLevel + "/" + size);

        try {
            creatImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
