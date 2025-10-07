package javafx;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

/**
 *
 * @author admin
 */
public class bai1 extends Application {
    public void start(Stage stage){
            Label hello = new Label("Hello, JavaFX!");
            Label jver = new Label("Java Ver: " + System.getProperty("java.version"));
            Label jfxver = new Label("JavaFX Ver: " + System.getProperty("javafx.version"));

            HBox root = new HBox(10, hello, jver, jfxver);

            stage.setScene(new Scene(root, 300, 150));
            stage.setTitle("Hello JavaFX");
            stage.show();
            
    }
    public static void main(String[] args) {
        launch();
    }
    
    
}
