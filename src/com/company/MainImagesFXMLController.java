/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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
    private ImageView view;

    Image image;
    int currentImage;
    ArrayList<String> dictionary1 = new ArrayList<>();
    Random random = new Random();
    boolean next;
    int score;

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
            if (answer.getText().isEmpty()) {
                //state is a label
                state.setStyle("-fx-text-fill: #000000;-fx-alignment: center;");
                state.setText("Writte at least one letter !");
            } else {
                if (answer.getText().toLowerCase().equals(dictionary1.get(currentImage))) {
                    next = true;
                    submit.setText("Next");
                    state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                    state.setText("You guessed this word !");
                    answer.setDisable(true);
                    answer.setDisable(true);
                    Progress.setProgress(Progress.getProgress() + 0.1);
                    score += 5;
                    //sound effects
                    music(1);
                } else {
                    score -= 1;
                    //state is the label
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !");
                    music(2);
                }
            }
        } else {
            next = false;
            submit.setText("Check");
            answer.setDisable(false);
            state.setText("");
            deletingImage();
            try {
                creatImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        answer.setText("");
        lblscore.setText(score + "Points");
    }


    void creatImage() throws FileNotFoundException {
        currentImage = random.nextInt(dictionary1.size());
        image = new Image(new FileInputStream(new File(String.valueOf(Paths.get("src/com/company/images/" + String.valueOf(currentImage) + ".png")))), 200, 150, false, false);
        //image= new Image(new FileInputStream(new File("C:\\Users\\Oussama\\IdeaProjects\\find_the_letter_material_design\\src\\com\\company\\images\\1.png")));
        view.setImage(image);
    }

    void deletingImage() {
        dictionary1.remove(currentImage);
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
        currentImage = 0;
        score = 0;
        next = false;
        Progress.setProgress(0);
        state.setText("");
        reading();
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
