package com.pwm.argos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ArgosGUI extends Application {
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
        stage.setScene(sc);
        stage.show();



    }


}

