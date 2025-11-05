package Default;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import UserUI_Components.MainDisplay;
import UserUI_Components.Sidebar_Options.OptionSidebar;
import UserUI_Components.PlayerBar;
import UserUI_Components.SearchBar.SearchBar;
import UserUI_Components.SearchBar.SearchBarUI;
import Default.Song;



public class UserUI {

    public Scene getScene(Stage primaryStage) {
        // --- Player bar (controller phát nhạc) ---
        PlayerBar playerBar = new PlayerBar();
        playerBar.setMinHeight(70);
        playerBar.setPrefHeight(70);
        playerBar.setMaxHeight(70);          // cố định 70px để VBox tính chuẩn
        VBox.setVgrow(playerBar, Priority.NEVER);

        // --- Trung tâm: truyền controller vào MainDisplay ---
        MainDisplay mainDisplay = new MainDisplay(playerBar);

        // --- Sidebar: truyền cả mainDisplay + controller ---
        OptionSidebar option_menu = new OptionSidebar(mainDisplay, playerBar);
        option_menu.setPrefWidth(250);
        option_menu.setMinWidth(250);
        option_menu.setMaxWidth(300);
        option_menu.setMaxHeight(Double.MAX_VALUE);  // cho phép cao hết phần block_1

        // --- Thanh tìm kiếm + cột phải ---
        SearchBar searchBar = new SearchBar();
        searchBar.setOnSearch(query -> {
            if (query == null || query.trim().isEmpty()) return;
            SearchBarUI view = new SearchBarUI(mainDisplay, query.trim()); // gọn
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });


        VBox rightColumn = new VBox(10, searchBar, mainDisplay);
        rightColumn.setFillWidth(true);
        VBox.setVgrow(mainDisplay, Priority.ALWAYS);  // MainDisplay chiếm hết phần còn lại

        // ===== Block 1: Sidebar + RightColumn =====
        HBox block_1 = new HBox(10, option_menu, rightColumn);
        block_1.setAlignment(Pos.CENTER_LEFT);
        block_1.setFillHeight(true);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        HBox.setHgrow(mainDisplay, Priority.ALWAYS);
        block_1.setMinHeight(Region.USE_COMPUTED_SIZE);
        block_1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        block_1.setMaxHeight(Double.MAX_VALUE);

        // ===== Root: không để khoảng trống thừa trên/dưới =====
        VBox root = new VBox();
        root.getChildren().addAll(block_1, playerBar);
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(0);
        root.setPadding(Insets.EMPTY);
        root.setFillWidth(true);
        root.setStyle("-fx-background-color: #010101;");

        // Cho block_1 chiếm toàn bộ chiều cao còn lại (đẩy playerBar sát đáy)
        VBox.setVgrow(block_1, Priority.ALWAYS);

        Scene scene = new Scene(root, 1250, 700);

        // ===== Resize logic chỉ cần theo chiều ngang =====
        scene.widthProperty().addListener((obs, ov, nv) -> {
            double w = nv.doubleValue();
            double optionMenuWidth =
                (primaryStage.isMaximized() || primaryStage.isFullScreen()) ? 300 : 250;
            option_menu.setPrefWidth(optionMenuWidth);
            playerBar.setPrefWidth(w); // root không có padding => không cần trừ
        });

        // ===== Spacebar: toggle play/pause (trừ khi đang gõ trong TextField/Area) =====
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.SPACE) {
                Object target = e.getTarget();
                if (target instanceof javafx.scene.control.TextInputControl) return;
                playerBar.togglePause();
                e.consume();
            }
        });

        // Không cần listener chiều cao nữa — VBox + Vgrow sẽ phân bổ chuẩn
        return scene;
    }
}