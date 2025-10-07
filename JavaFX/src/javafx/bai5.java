package javafx;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class bai5 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label color = new Label("Selected: ");
        ChoiceBox cb = new ChoiceBox<>(
             FXCollections.observableArrayList(
                  "Red", "Green", "Blue", "Yellow", "Orange", "Purple"   
             ) 
        );     
        VBox root = new VBox(10);
        root.getChildren().addAll(cb, color);
        cb.setOnAction(event -> {
            String selected = (String)cb.getValue();
            color.setText("Selected: " + selected);
        });
        Scene sc = new Scene(root, 300, 200);
        primaryStage.setScene(sc);
        primaryStage.setTitle("B5");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
