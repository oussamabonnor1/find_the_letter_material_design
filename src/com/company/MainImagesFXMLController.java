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
import java.util.ResourceBundle;

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
        try {
            creatImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void creatImage() throws FileNotFoundException {
        currentImage++;
        image = new Image(new FileInputStream(new File(String.valueOf(Paths.get("src/com/company/images/" + String.valueOf(currentImage) + ".png")))),200,150,false,false);
        //image= new Image(new FileInputStream(new File("C:\\Users\\Oussama\\IdeaProjects\\find_the_letter_material_design\\src\\com\\company\\images\\1.png")));
        view.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentImage = 0;
        try {
            creatImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
