package MAIN;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

// 👉 Import các UI riêng cho từng thể loại
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
