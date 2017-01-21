package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main extends Application implements EventHandler<ActionEvent> {

    Stage stage;
    Scene scene;
    VBox layout;
    Pane background;

    Label title;
    Label word;
    Label state;
    Label pepTalk;

    Image image = new Image(new FileInputStream("C:\\Users\\Oussama\\Pictures\\Java work\\Tick_Mark_Dark-512.png"), 350, 350, false, false);
    ImageView iv = new ImageView(image);


    JFXButton submit;
    JFXButton help;

    JFXTextField answer;

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
        Rectangle rectangle = new Rectangle(0, 0, 600, 150);
        rectangle.setFill(Paint.valueOf("FFFFFF"));

        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Find The Letters");

        iv.setTranslateX(80);
        iv.setTranslateY(210);

        background = new Pane();
        background.setBackground(new Background(new BackgroundFill(Paint.valueOf("673AB7"), null, null)));

        layout = new VBox(100);

        title = new Label("Find  T-e  Lett-rs");
        title.setTextFill(Paint.valueOf("673AB7"));
        title.setTranslateY(-205);
        title.setTranslateX(-50);
        title.setPrefWidth(600);
        title.setAlignment(Pos.BOTTOM_CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Calisto MT", FontWeight.BOLD, 35));

        state = new Label("Score: " + score);
        state.setTextFill(Paint.valueOf("FFFFFF"));
        state.setPrefWidth(600);
        state.setTranslateY(-330);
        state.setTranslateX(-50);
        state.setAlignment(Pos.BOTTOM_CENTER);
        state.setTextAlignment(TextAlignment.CENTER);
        state.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 30));

        word = new Label("");
        word.setTextFill(Paint.valueOf("FFFFFF"));
        word.setPrefWidth(600);
        word.setTranslateX(-50);
        word.setTranslateY(-260);
        word.setAlignment(Pos.BOTTOM_CENTER);
        word.setTextAlignment(TextAlignment.CENTER);
        word.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        pepTalk = new Label("Guess this:");
        pepTalk.setTextFill(Paint.valueOf("FFFFFF"));
        pepTalk.setPrefWidth(600);
        pepTalk.setTranslateX(-50);
        pepTalk.setTranslateY(-220);
        pepTalk.setAlignment(Pos.BOTTOM_CENTER);
        pepTalk.setTextAlignment(TextAlignment.CENTER);
        pepTalk.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        submit = new JFXButton("Check");
        submit.setTextFill(Paint.valueOf("006064"));
        submit.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"), null, null)));
        submit.setFont(Font.font("FangSong", FontWeight.BOLD, 25));
        submit.setTranslateX(165);
        submit.setTranslateY(-300);
        submit.setPrefWidth(170);
        submit.setOnAction(this);

        help = new JFXButton("?");
        help.setTextFill(Paint.valueOf("006064"));
        help.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"), null, null)));
        help.setFont(Font.font("FangSong", FontWeight.BOLD, 25));
        help.setTranslateX(370);
        help.setTranslateY(547);
        help.setPrefWidth(25);
        help.setOnAction(this);

        answer = new JFXTextField();
        answer.setFocusColor(Paint.valueOf("FFFFFF"));
        answer.setMaxWidth(200);
        answer.setFocusColor(Paint.valueOf("FFFFFF"));
        answer.setFont(Font.font("FangSong", FontWeight.BOLD, 20));
        answer.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
        answer.setTranslateX(150);
        answer.setTranslateY(-270);


        layout.getChildren().addAll(rectangle, title, pepTalk, word, answer, submit, state);
        //layout.setAlignment(Pos.CENTER);

        background.getChildren().addAll(layout, help);

        HBox root = new HBox();
        root.getChildren().add(background);

        scene = new Scene(root, 500, 800);
        stage.setScene(scene);
        creatingWord();
        stage.show();

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
        word.setText("");

        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            word.setText(word.getText() + first.organizedCharacters.get(i));
        }

    }

    public void updateWword() {
        word.setText("");
        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            word.setText(word.getText() + first.organizedCharacters.get(i));
        }
    }

    // level two: i mean seriously guys, some graphics would be heaven now -_-
    //i learned graphics old me..great job


    public void guess() {
        //just in case things go bad

        if (submit.getText() == "Check") {
            if (answer.getText().isEmpty()) {
                state.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
                state.setText("Writte at least one letter");
            } else {


                if (first.guessingCharachter(answer.getText().toLowerCase())) {

                    if (first.organizedCharacters.equals(first.wordLetters)) {
                        background.getChildren().removeAll(layout);
                        background.getChildren().addAll(iv, layout);
                        submit.setText("Next");
                        music(3);
                        state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                        score = first.getScore();
                        state.setText("Good Job, new Score: " + score);
                    } else {
                        score = first.getScore();
                        state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                        state.setText("Good Job, new Score: " + score);
                        music(1);
                        updateWword();
                    }
                } else {
                    score = first.getScore();
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !\nnew Score: " + score);
                    music(2);
                }
                answer.setText("");
            }
        } else {
            if (first.dictionary.size() == 0) {
                submit.setStyle("fx-text-fill:#FFFFFF;");
                submit.setText("You Finished this Level");
            } else {
                creatingWord();
                submit.setText("Check");
                background.getChildren().removeAll(iv, layout);
                background.getChildren().addAll(layout);
            }
        }

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == submit) {
            guess();
        }
        if (event.getSource() == help) {

            Stage stage1 = stage;


            stage1.setScene(new Scene(new StackPane(new Label(new Tutorial().tutorial())), 600, 600));

            stage1.show();

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

