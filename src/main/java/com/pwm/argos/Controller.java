package com.pwm.argos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    /* Button b1 - b6 Buttons für Einträge */
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    /* Button b7 = New Button */
    @FXML
    private Button b7;
    /*Textfelder für newEntry*/
    @FXML
    private TextField userEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private TextField tagsEntry;

    @FXML
    Label name;

    @FXML
    Label user;

    @FXML
    Label password;


    @FXML
    void onActionb1(ActionEvent event) {
        String ausgabe1 = ArgosGUI.rft.getName();
        String ausgabe2 = ArgosGUI.rft.getName();
        String ausgabe3 = ArgosGUI.rft.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b1.setText(ausgabe1);
    }

    @FXML
    void onActionb2(ActionEvent event) {
        String ausgabe1 = ArgosGUI.je.getName();
        String ausgabe2 = ArgosGUI.je.getName();
        String ausgabe3 = ArgosGUI.je.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b2.setText(ausgabe1);
    }

    @FXML
    void onActionb3(ActionEvent event) {
        String ausgabe1 = ArgosGUI.je.getName();
        String ausgabe2 = ArgosGUI.je.getName();
        String ausgabe3 = ArgosGUI.je.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b3.setText(ausgabe1);
    }

    @FXML
    void onActionb4(ActionEvent event) {
        String ausgabe1 = ArgosGUI.je.getName();
        String ausgabe2 = ArgosGUI.je.getName();
        String ausgabe3 = ArgosGUI.je.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b4.setText(ausgabe1);
    }

    @FXML
    void onActionb5(ActionEvent event) {
        String ausgabe1 = ArgosGUI.je.getName();
        String ausgabe2 = ArgosGUI.je.getName();
        String ausgabe3 = ArgosGUI.je.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b5.setText(ausgabe1);
    }

    @FXML
    void onActionb6(ActionEvent event) {
        String ausgabe1 = ArgosGUI.je.getName();
        String ausgabe2 = ArgosGUI.je.getName();
        String ausgabe3 = ArgosGUI.je.getName();

        name.setText(ausgabe1.toString());
        user.setText(ausgabe2.toString());
        password.setText(ausgabe3.toString());
        b6.setText(ausgabe1);
    }



    @FXML
    void newEntry(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newEntry.fxml")));
            Scene sc = new Scene(root);     //Scene
            Stage stage1 = new Stage();
            stage1.setTitle("New");
            stage1.setResizable(false);
            stage1.setScene(sc);
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //String ausgabe1 = ArgosGUI.je.getName();
        //b2.setText(ausgabe1);
    }
}
