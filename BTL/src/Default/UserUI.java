package Default;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import UserUI_Components.MainDisplay;
import UserUI_Components.OptionSidebar;
import UserUI_Components.PlayerBar;
import UserUI_Components.SearchBar;

public class UserUI {

    public Scene getScene(Stage primaryStage) {
        // --- Tạo PlayerBar (implements Song.PlayerController) trước để truyền đi ---
        PlayerBar playerBar = new PlayerBar(); // controller phát nhạc

        // --- Trung tâm: truyền controller vào MainDisplay  ---
        MainDisplay mainDisplay = new MainDisplay(playerBar);
        // Nếu MainDisplay của bạn chưa có ctor nhận controller, dùng bản no-arg cũ:
        // MainDisplay mainDisplay = new MainDisplay();

        // --- Sidebar: truyền cả mainDisplay + controller như chữ ký mới ---
        OptionSidebar option_menu = new OptionSidebar(mainDisplay, playerBar);

        // --- Thanh tìm kiếm + cột phải ---
        SearchBar searchBar = new SearchBar();
        VBox rightColumn = new VBox(10, searchBar, mainDisplay);
        VBox.setVgrow(mainDisplay, Priority.ALWAYS);

        // ===== Block 1: Sidebar + RightColumn (SearchBar + MainDisplay) =====
        HBox block_1 = new HBox(10, option_menu, rightColumn);
        block_1.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        HBox.setHgrow(mainDisplay, Priority.ALWAYS);

        // ===== Full layout =====
        VBox root = new VBox(10, block_1, playerBar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setStyle("-fx-background-color: #010101;");

        Scene scene = new Scene(root, 1250, 700);

        // ===== Resize logic =====
        scene.widthProperty().addListener((obs, ov, nv) -> {
            double width = nv.doubleValue();
            double optionMenuWidth =
                (primaryStage.isMaximized() || primaryStage.isFullScreen()) ? 300 : 250;
            option_menu.setPrefWidth(optionMenuWidth);
            playerBar.setPrefWidth(width - 20); // trừ padding
        });

        scene.heightProperty().addListener((obs, ov, nv) -> {
            double height = nv.doubleValue();
            double optionMenuHeight = height - 90; // chừa chỗ cho player + padding
            option_menu.setPrefHeight(Math.max(optionMenuHeight, 200));
            playerBar.setPrefHeight(70);
        });

        return scene;
    }
}
