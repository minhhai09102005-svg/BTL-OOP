package javafx;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class bai3 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label l1 = new Label("Name:");
        TextField name = new TextField();
        Label l2 = new Label("Age:");
        TextField age = new TextField();
        Button btn1 = new Button("Submit");
        Button btn2 = new Button("Clear");
        VBox root = new VBox(10);
        root.getChildren().addAll(l1, name, l2, age, btn1, btn2);
        btn1.setOnAction(event -> {
            Alert al = new Alert(AlertType.INFORMATION);
            al.setTitle("Ìfnormation");
            al.setHeaderText(null);
            String ten = name.getText();
            String tuoi = age.getText();
            al.setContentText(ten + "\n" + tuoi);
            al.showAndWait();            
        });
        btn2.setOnAction(event -> {
            name.clear();
            age.clear();
        });
        Scene sc = new Scene(root, 300, 200);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Layout");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
