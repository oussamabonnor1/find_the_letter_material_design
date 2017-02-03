/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class MainHelpFXMLController implements Initializable, EventHandler {

    @FXML
    private JFXButton closButton;

    @FXML
    private JFXButton minButton;

    @FXML
    public Label helpPoints;

    @FXML
    private JFXButton showLetter;

    @FXML
    private JFXButton showWord;

    @FXML
    private Label txtShow;


    public Connection connection;
    Statement stmt;
    int help = 0;


    @FXML
    void closewindow(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    void OnMinimiz(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = MainController.connection;
        txtShow.setText("");
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Manager;");

            if (!rs.next()) {
                helpPoints.setText("00");
            } else {
                helpPoints.setText(String.valueOf(rs.getInt("Help")));
                help = rs.getInt("Help");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLetter.setOnAction(this);
        showWord.setOnAction(this);
    }


    @Override
    public void handle(Event event) {
        ResultSet rs;
        String word = null;
        String organised = null;
        Word manipulation = new Word(0, 0);
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Manager;");
            help = rs.getInt("Help");
            word = rs.getString("Word");
            organised = rs.getString("Organised");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (event.getSource() == showLetter) {
            if (help >= 3) {
                organised = (manipulation.help1(organised, word, 2));
                txtShow.setText(organised);
                help -= 3;
                helpPoints.setText(String.valueOf(help));
            }

        }

        if (event.getSource() == showWord) {
            if (help >= 10) {
                txtShow.setText(word);
                help -= 10;
                helpPoints.setText(String.valueOf(help));
            }

        }

        String sql = "UPDATE Manager " +
                " SET Organised = " + "'" + organised + "'" +
                ",Help = " + help +
                " WHERE id = 1;";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
