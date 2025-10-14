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
import Default.LoginUI;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Khi mở app -> vào màn hình login trước
        LoginUI loginUI = new LoginUI();
        Scene loginScene = loginUI.getScene(primaryStage);

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
