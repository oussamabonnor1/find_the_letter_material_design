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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    JFXTextField answer;

    String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck", "phone", "daisy"};
    Level first = new Level(1, 0, dictionary1, 0);
    int h = -1;

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Find The Letters");

        iv.setTranslateX(80);
        iv.setTranslateY(210);

        background = new Pane();
        background.setBackground(new Background(new BackgroundFill(Paint.valueOf("673AB7"), null, null)));

        layout = new VBox(100);
        //layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("673AB7"), null, null)));
        //layout.setStyle("-fx-background: #FFFFFF");

        title = new Label("Find T-e Lett-rs");
        title.setTextFill(Paint.valueOf("FFFFFF"));
        title.setTranslateY(50);
        title.setTranslateX(-50);
        title.setPrefWidth(600);
        title.setAlignment(Pos.BOTTOM_CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        state = new Label("Score: " + first.getScore());
        state.setTextFill(Paint.valueOf("FFFFFF"));
        state.setPrefWidth(600);
        state.setTranslateY(-50);
        state.setTranslateX(-50);
        state.setAlignment(Pos.BOTTOM_CENTER);
        state.setTextAlignment(TextAlignment.CENTER);
        state.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 30));

        word = new Label("");
        word.setTextFill(Paint.valueOf("FFFFFF"));
        word.setPrefWidth(600);
        word.setTranslateX(-50);
        word.setAlignment(Pos.BOTTOM_CENTER);
        word.setTextAlignment(TextAlignment.CENTER);
        word.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        pepTalk = new Label("Guess this:");
        pepTalk.setTextFill(Paint.valueOf("FFFFFF"));
        pepTalk.setPrefWidth(600);
        pepTalk.setTranslateX(-50);
        pepTalk.setAlignment(Pos.BOTTOM_CENTER);
        pepTalk.setTextAlignment(TextAlignment.CENTER);
        pepTalk.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        submit = new JFXButton("Check");
        submit.setTextFill(Paint.valueOf("006064"));
        submit.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"), null, null)));
        submit.setFont(Font.font("FangSong", FontWeight.BOLD, 25));
        submit.setTranslateX(165);
        submit.setPrefWidth(170);
        submit.setOnAction(this);

        answer = new JFXTextField();
        answer.setFocusColor(Paint.valueOf("FFFFFF"));
        answer.setMaxWidth(200);
        answer.setFocusColor(Paint.valueOf("FFFFFF"));
        answer.setFont(Font.font("FangSong", FontWeight.BOLD, 20));
        answer.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
        answer.setTranslateX(150);


        layout.getChildren().addAll(title, pepTalk, word, answer, submit, state);
        //layout.setAlignment(Pos.CENTER);

        background.getChildren().addAll(layout);

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
            for (int i = h; i < dictionary1.length; i++) {
                if(i<dictionary1.length-1) dictionary1[i] = dictionary1[i + 1];
            }
        }
        first.wordLetters.clear();
        first.organizedCharacters.clear();
        h = d.nextInt(dictionary1.length);
        first.testWord = first.dictionary[h];
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

            /*while (!first.organizedCharacters.equals(first.wordLetters) && decison == true) {
                System.out.println("enter a letter");
               // first.guessingCharachter(r.next().charAt(0));
                decison = first.help1();
            }
        first.setScore(first.getScore() + 100);
        System.out.println("great job " + name + " you finished a word! your score now is:" + first.getScore());*/
    //first.organizedCharacters.clear();
    //first.wordLetters.clear();
    //first.setHelp(0);
    //decison = true;


    // level two: i mean seriously guys, some graphics would be heaven now -_-
    //i learned graphics old me..great job

   /* String[] dictionary2 = {"player", "nature", "extra", "drive", "guitar", "tragic", "creation"};
    Level second = new Level(2, first.getScore(), dictionary2, 0);

        while(second.getScore() < 600){
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
        second.setScore(first.getScore() + 120);
        System.out.println("great job " + name + " you finished a word! your score now is:" + second.getScore());
        second.organizedCharacters.clear();
        second.wordLetters.clear();
        second.setHelp(0);
        decison = true;*/


    public void guess() {
        //just in case things go bad

        if (submit.getText() == "Check") {
            if (answer.getText().isEmpty()) {
                state.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
                state.setText("Writte at least one letter");
            } else {
                if (first.guessingCharachter(answer.getText().toLowerCase())) {

                    state.setStyle("-fx-text-fill: #00C853;-fx-alignment: center;");
                    state.setText("Good Job, new Score: " + first.getScore());
                    updateWword();
                    if (first.organizedCharacters.equals(first.wordLetters)) {
                        background.getChildren().removeAll(layout);
                        background.getChildren().addAll(iv, layout);
                        submit.setText("Next");
                    }
                } else {
                    state.setStyle("-fx-text-fill: #D50000;-fx-alignment: center;");
                    state.setText("wrong, guess again !\nnew Score: " + first.getScore());
                }
                answer.setText("");
            }
        } else {
            creatingWord();
            submit.setText("Check");
            background.getChildren().removeAll(iv, layout);
            background.getChildren().addAll(layout);
        }

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == submit) {
            guess();
        }
    }
}

