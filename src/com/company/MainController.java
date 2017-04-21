package com.company;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainController implements Initializable {

    @FXML
    private JFXButton closButton;

    @FXML
    private JFXButton minButton;

    @FXML
    private JFXButton lvlAlone;

    @FXML
    private JFXButton lvlimages;

    @FXML
    private Label mainScoreLabel;

    @FXML
    private ImageView bgImage;

    public static Connection connection = CNX.dbConnection();

    Statement stmt;
    String sql;

    static int mainScore;

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
    void word(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWordFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }


    @FXML
    void Alone(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainAloneFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    void images(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainImagesFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    void Text(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainTextFXML.fxml"));
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

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Manager;");

            if (!rs.next()) {
                sql = "INSERT INTO Manager (mainScore,Score,Help,Organised,Word) " +
                        "VALUES" + "('" + 0 + "','" + 0 + "','" + 0 + "','" + "','" + "');";
                stmt.executeUpdate(sql);
                System.out.println("CREATION");
            } else {

                sql = "UPDATE Manager " +
                        "SET Help = " + 0 +
                        "   ,Score = " + 0 +
                        " WHERE id = 1;";
                mainScore = rs.getInt("mainScore");
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainScoreLabel.setText(String.valueOf(mainScore));
    }

}
