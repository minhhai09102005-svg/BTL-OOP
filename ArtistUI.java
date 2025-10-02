package Default;

import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

// 👉 Import các UI riêng cho từng thể loại
import Option.VpopUI;
import Option.RockUI;
import Option.RapUI;
import Option.USUKUI;
import Option.OtherUI;
import Default.LoginUI;

public class ArtistUI {
    private String artistName;

    // ✅ Constructor mặc định (không tham số)
    public ArtistUI() {
        this.artistName = "Artist"; // mặc định là "Artist"
    }

    // ✅ Constructor có tham số
    public ArtistUI(String artistName) {
        this.artistName = artistName;
    }

    public Scene getScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #303030;");

        Label lblTitle = new Label("Xin chào, " + artistName);
        lblTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        Button btnManageSongs = new Button("Quản lý bài hát");
        Button btnManageAlbums = new Button("Quản lý album");
        Button btnLogout = new Button("Đăng xuất");

        btnLogout.setOnAction(e -> {
        // Quay về màn hình Login
            LoginUI loginUI = new LoginUI();
            Scene loginScene = loginUI.getScene(stage);
            stage.setScene(loginScene);
        });


        layout.getChildren().addAll(lblTitle, btnManageSongs, btnManageAlbums, btnLogout);

        return new Scene(layout, 800, 600);
    }
}