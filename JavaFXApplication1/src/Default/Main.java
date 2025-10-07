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
import Default.UserUI;
import Default.ArtistUI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ===== Đăng nhập phân quyền =====
        boolean isAdmin = false; // 🔹 Sau này thay bằng logic đăng nhập

        Scene scene;
        if (isAdmin ) {
            ArtistUI artistUI = new ArtistUI();
            scene = artistUI.getScene(primaryStage);
        } else {
            UserUI userUI = new UserUI();
            scene = userUI.getScene(primaryStage);
        }

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
