/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Default;

/**
 *
 * @author Administrator
 */
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Option.VpopUI;
import Option.RockUI;
import Option.RapUI;
import Option.USUKUI;
import Option.OtherUI;
import Default.UserUI;
import Default.ArtistUI;

public class LoginUI {
    public Scene getScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #2c2c2c;");

        Label lbl = new Label("Màn hình đăng nhập");
        lbl.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        Button btnUser = new Button("Login as User");
        Button btnArtist = new Button("Login as Artist");

        // 🔹 Nếu login bằng User
        btnUser.setOnAction(e -> {
            UserUI userUI = new UserUI();
            stage.setScene(userUI.getScene(stage));
        });

        // 🔹 Nếu login bằng Artist
        btnArtist.setOnAction(e -> {
            ArtistUI artistUI = new ArtistUI("Demo Artist");
            stage.setScene(artistUI.getScene(stage));
        });

        layout.getChildren().addAll(lbl, btnUser, btnArtist);

        return new Scene(layout, 400, 300);
    }
}