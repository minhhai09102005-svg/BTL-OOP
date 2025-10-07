/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class bai6 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Image i = new Image(
              getClass().getResource("/resources/9be492db-8d80-4273-a715-6e698f41d194.jpeg").toExternalForm()
        );
        
        ImageView view = new ImageView(i);
        StackPane root = new StackPane();
        root.getChildren().add(view);
        
        Scene sc = new Scene(root, 400, 800);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Image");
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
