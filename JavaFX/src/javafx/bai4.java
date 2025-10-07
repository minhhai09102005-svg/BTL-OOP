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
public class bai4 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TextField txt = new TextField();
        Button btn = new Button("Display Text");
        Label res = new Label("Entered text:");
        VBox root = new VBox(10);
        root.getChildren().addAll(txt, btn, res);
        btn.setOnAction(event -> {
            String s = txt.getText();
            res.setText("Entered text:" + " " + s);
        });
        Scene sc = new Scene(root, 300, 200);
        primaryStage.setScene(sc);
        primaryStage.setTitle("App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
