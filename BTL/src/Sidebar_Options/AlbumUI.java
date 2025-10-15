package Sidebar_Options;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class AlbumUI extends StackPane {
    public AlbumUI() {
        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // cho phép fill
        getChildren().add(new Label("Album Screen"));
    }
}