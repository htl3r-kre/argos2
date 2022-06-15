package com.pwm.argos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ArgosGUI extends Application {
    static PwSafe rft;
    static PwSafe je;
    double x, y = 0;



    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene sc = new Scene(root);     //Scene
        stage.setTitle("Argos");

        //move around
        root.setOnMousePressed(evt -> {

            x = evt.getSceneX();
            y = evt.getSceneY();


        });
        root.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });



        //Stage
        stage.setResizable(false);
        stage.setScene(sc);
        stage.show();



    }

    public static void main(String[] args) {
        rft = new PwSafe("Adobe");
        rft.addPw(new EncryptedP("test1", "test2", "test3"));
        je = new PwSafe("Google");
        je.addPw(new EncryptedP("user", "passwort", "tag"));
        launch();
    }


}

