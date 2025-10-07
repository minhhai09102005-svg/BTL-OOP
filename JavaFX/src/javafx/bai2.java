package javafx;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;


/**
 *
 * @author admin
 */
public class bai2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
            Button btn = new Button("Bam di");
            btn.setOnAction(event -> {
                Alert al = new Alert(AlertType.INFORMATION);
                al.setTitle("Tui iu HP");
                al.setHeaderText(null);
                al.setContentText("💖");
                al.showAndWait();
            });
            VBox root = new VBox(10);
            root.getChildren().add(btn);
            root.setAlignment(Pos.CENTER);

            primaryStage.setScene(new Scene(root, 300, 150));
            primaryStage.setTitle("Button Action App");
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
