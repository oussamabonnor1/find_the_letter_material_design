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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application implements EventHandler<ActionEvent> {

    Stage stage;
    Scene scene;
    VBox layout;

    Label title;
    Label word;
    Label state;

    JFXButton submit;

    JFXTextField answer;

    String[] dictionary1 = {"house", "beach", "heart", "children", "shower", "money", "luck"};
    Level first = new Level(1, 0, dictionary1, 0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        layout = new VBox(100);
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("673AB7"), null, null)));
        //layout.setStyle("-fx-background: #FFFFFF");

        title = new Label("Find The Letter");
        title.setTextFill(Paint.valueOf("FFFFFF"));
        title.setTranslateY(50);
        title.setPrefWidth(600);
        title.setAlignment(Pos.BOTTOM_CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        state = new Label("test");
        state.setTextFill(Paint.valueOf("FFFFFF"));
        state.setPrefWidth(600);
        state.setAlignment(Pos.BOTTOM_CENTER);
        state.setTextAlignment(TextAlignment.CENTER);
        state.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 30));

        word = new Label("Guess this:\n\n");
        word.setTextFill(Paint.valueOf("FFFFFF"));
        word.setPrefWidth(600);
        word.setAlignment(Pos.BOTTOM_CENTER);
        word.setTextAlignment(TextAlignment.CENTER);
        word.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 35));

        submit = new JFXButton("Check");
        submit.setTextFill(Paint.valueOf("006064"));
        submit.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"), null, null)));
        submit.setFont(Font.font("FangSong", FontWeight.BOLD, 25));
        submit.setTranslateX(165);
        submit.setPrefWidth(170);
        submit.setOnAction(this);

        answer = new JFXTextField();
        answer.setMaxWidth(200);
        answer.setFocusColor(Paint.valueOf("FFFFFF"));
        answer.setFont(Font.font("FangSong", FontWeight.BOLD, 20));
        answer.setStyle("-fx-text-fill: #FFFFFF;-fx-alignment: center;");
        answer.setTranslateX(150);


        layout.getChildren().addAll(title, word, answer, submit, state);


        scene = new Scene(layout, 500, 800);
        stage.setScene(scene);
        creatingWord();
        stage.show();

    }

    public void creatingWord() {
        //variables declaration
        Scanner r = new Scanner(System.in);
        Random d = new Random();
        int answer = 0;
        String name = null;
        boolean decison = true;
        Tutorial read = new Tutorial();



        first.testWord = first.dictionary[d.nextInt(7)];
        first.generateWord(first.decompose(first.testWord), d.nextInt(2));

        for (int i = 0; i < first.organizedCharacters.size(); i++) {
            word.setText(word.getText() + first.organizedCharacters.get(i));
        }
            /*while (!first.organizedCharacters.equals(first.wordLetters) && decison == true) {
                System.out.println("enter a letter");
               // first.guessingCharachter(r.next().charAt(0));
                decison = first.help1();
            }
        first.setScore(first.getScore() + 100);
        System.out.println("great job " + name + " you finished a word! your score now is:" + first.getScore());*/
        first.organizedCharacters.clear();
        first.wordLetters.clear();
        first.setHelp(0);
        decison = true;


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
    }

    public void guess(){
        //if(!first.organizedCharacters.equals(first.wordLetters))
        System.out.println(answer.getText().charAt(0));
            if(first.guessingCharachter(answer.getText())){
                state.setText("Good Job !");
            }else {
                state.setText("wrong, guess again !");
            }
        //}
    }

    @Override
    public void handle(ActionEvent event) {
            if(event.getSource()==submit){
                guess();
            }
    }
}

