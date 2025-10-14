package Default;

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
import javafx.scene.layout.Priority; 

// 👉 Import các UI riêng cho từng thể loại
import Options.VpopUI;
import Options.RockUI;
import Options.RapUI;
import Options.USUKUI;
import Options.OtherUI;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UserUI {

    public Scene getScene(Stage primaryStage) {
        // ===== Block 1 =====
        VBox option_menu = new VBox();
        option_menu.setPrefSize(200, 400); 
        Label label_1 = new Label("MusicPlayer");
        label_1.setStyle("-fx-font-size: 20px; -fx-text-fill: white;-fx-font-weight: bold;");
        label_1.setTranslateX(30);
        option_menu.getChildren().add(label_1);
        option_menu.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");
        option_menu.setSpacing(8);
        option_menu.setPadding(new Insets(12));

        // Vùng đen hiển thị nội dung
        StackPane mainDisplay = new StackPane();
        mainDisplay.setPrefSize(900, 400);
        mainDisplay.setStyle("-fx-background-color: #010101;");
        
        // nội dung mặc định của mainDisplay (tạo 1 lần)
        StackPane defaultContent = new StackPane(new Label("Welcome"));
        defaultContent.setStyle("-fx-background-color: #010101;");
        defaultContent.prefWidthProperty().bind(mainDisplay.widthProperty());
        defaultContent.prefHeightProperty().bind(mainDisplay.heightProperty());
        mainDisplay.getChildren().setAll(defaultContent);


        // Nút Home (sidebar)
        Button btnHome = new Button("Home");
        btnHome.setPrefSize(160, 50);
        btnHome.setMaxWidth(Double.MAX_VALUE);
        btnHome.setAlignment(Pos.CENTER_LEFT);
        btnHome.setStyle(
            "-fx-background-color: #2F3945;" +
            "-fx-background-radius: 6px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;"
        );
        // Hover nhẹ
        btnHome.setOnMouseEntered(e -> btnHome.setStyle(
            "-fx-background-color: #394656; -fx-background-radius: 6px;" +
            "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnHome.setOnMouseExited(e -> btnHome.setStyle(
            "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
            "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        
        btnHome.setOnAction(e -> {
            mainDisplay.getChildren().setAll(defaultContent); // quay về nội dung mặc định
        });

        
        option_menu.getChildren().add(btnHome);

        // block_1: sidebar + mainDisplay  <<< FIX: thêm mainDisplay vào đây
        HBox block_1 = new HBox(10);
        block_1.getChildren().addAll(option_menu, mainDisplay);
        block_1.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(mainDisplay, Priority.ALWAYS);
        
        // ===== Block 3: Player bar kiểu Spotify =====
           HBox playerBar = new HBox();
           playerBar.setAlignment(Pos.CENTER_LEFT);
           playerBar.setSpacing(16);
           playerBar.setPadding(new Insets(10, 14, 10, 14));
           playerBar.setPrefHeight(70);
           playerBar.setMinHeight(70);
           playerBar.setMaxHeight(70);
           playerBar.setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 10;");

           // ----- CỤM TRÁI: bìa + tiêu đề + nghệ sĩ -----
           ImageView cover = new ImageView();
           try {
               cover.setImage(new Image(getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm())); // đổi tên ảnh của bạn
           } catch (Exception ignore) { /* nếu thiếu ảnh thì để trống */ }
           cover.setFitWidth(48);
           cover.setFitHeight(48);
           // bo góc cho bìa
           Rectangle clip = new Rectangle(48, 48);
           clip.setArcWidth(10); clip.setArcHeight(10);
           cover.setClip(clip);

           Label titleLbl  = new Label("Be Cool");
           titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 800;");
           Label artistLbl = new Label("Ngọt");
           artistLbl.setStyle("-fx-text-fill: #B3B3B3; -fx-font-size: 12px; -fx-font-weight: 600;");
           VBox metaBox = new VBox(2, titleLbl, artistLbl);

           HBox leftBox = new HBox(10, cover, metaBox);
           leftBox.setAlignment(Pos.CENTER_LEFT);

           // ----- CỤM GIỮA: nút điều khiển + thanh thời gian -----
           Button btnPrev = new Button("⏮");
           
           Button btnPlay = new Button("⏵"); 
              // toggle icon khi bấm
             final boolean[] isPlaying = { false };
             btnPlay.setOnAction(e -> {
                 isPlaying[0] = !isPlaying[0];
                 btnPlay.setText(isPlaying[0] ? "⏸" : "⏵");
                 // TODO: nếu có backend thì gọi play()/pause() ở đây
             });
           
           
           Button btnNext = new Button("⏭");

           for (Button b : new Button[]{btnPrev, btnPlay, btnNext}) {
               b.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"
                       + "-fx-font-size: 18px; -fx-font-weight: 700; -fx-cursor: hand;"
                       + "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
           }

           HBox controlsRow = new HBox(20, btnPrev, btnPlay, btnNext);
           controlsRow.setAlignment(Pos.CENTER);

           // thời gian + slider
           int totalSeconds = 205; // ví dụ 3:25, thay bằng duration thật của bài
           Label lblCurrent = new Label("0:00");
           lblCurrent.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");
           Label lblTotal   = new Label(String.format("%d:%02d", totalSeconds/60, totalSeconds%60));
           lblTotal.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");

           Slider progress = new Slider(0, totalSeconds, 0);
           progress.setBlockIncrement(1);
           progress.setShowTickMarks(false);
           progress.setShowTickLabels(false);
           progress.setMaxWidth(Double.MAX_VALUE);
           HBox.setHgrow(progress, Priority.ALWAYS);
           // style đơn giản cho slider (màu sáng)
           progress.setStyle(
               "-fx-control-inner-background: #6B7280;"
             + "-fx-base: #D1D5DB;"
           );

           // hàng slider
           HBox timeRow = new HBox(10, lblCurrent, progress, lblTotal);
           timeRow.setAlignment(Pos.CENTER);

           // gộp giữa
           VBox centerBox = new VBox(6, controlsRow, timeRow);
           centerBox.setAlignment(Pos.CENTER);
           HBox.setHgrow(centerBox, Priority.ALWAYS);   // giữa chiếm rộng để các cụm trái/phải co gọn

           // ----- CỤM PHẢI: chỉ ♥ và ↻ -----
           Button btnLike = new Button();
           btnLike.setText("♥");
            btnLike.setTextFill(Color.WHITE);
            btnLike.setBackground(Background.EMPTY);              // bỏ nền
            btnLike.setBorder(Border.EMPTY);                      // bỏ viền
            btnLike.setFocusTraversable(false);                   // tắt viền focus
            btnLike.setStyle("-fx-background-color: transparent;"
                    + "-fx-font-size: 16px; -fx-font-weight: 700; "
                    + "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            final boolean[] liked = {false};
            btnLike.setOnAction(e -> {
                liked[0] = !liked[0];
                btnLike.setTextFill(liked[0] ? Color.RED : Color.WHITE);
            });
            
            
           Button btnRepeat = new Button();
           // ↻ trắng ↔ xanh dương
            btnRepeat.setText("↻");
            btnRepeat.setTextFill(Color.WHITE);
            btnRepeat.setBackground(Background.EMPTY);
            btnRepeat.setBorder(Border.EMPTY);
            btnRepeat.setFocusTraversable(false);
            btnRepeat.setStyle("-fx-background-color: transparent;"
                    + "-fx-font-size: 16px; -fx-font-weight: 700; "
                    + "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            final boolean[] repeating1 = {false};
            btnRepeat.setOnAction(e -> {
                repeating1[0] = !repeating1[0];
                btnRepeat.setTextFill(repeating1[0] ? Color.DODGERBLUE : Color.WHITE);
            });

                         // nút volume
            Button volButton = new Button("🔊");

            // bỏ viền & nền, tắt focus ring
            volButton.setBackground(Background.EMPTY);
            volButton.setBorder(Border.EMPTY);
            volButton.setFocusTraversable(false);
            volButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 0;" +
                "-fx-border-color: transparent;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-padding: 0;" +                 // gọn icon
                "-fx-text-fill: white; -fx-font-size: 14px;"
            );

            // toggle icon khi bấm
            final boolean[] Mute = { false };
            volButton.setOnAction(e -> {
                Mute[0] = !Mute[0];
                if (Mute[0]) {
                    volButton.setText("🔇");
                    // TODO: Backend xử lý mute nhạc (bật mute)
                } else {
                    volButton.setText("🔊");
                    // TODO: Backend xử lý mute nhạc (tắt mute)
                }
            });

        
            Slider vol = new Slider(0, 1, 0.8); // 0..1
            vol.setPrefWidth(110);
            vol.setMaxWidth(110);
            vol.setMinWidth(80);
            vol.setFocusTraversable(false);
            vol.setShowTickMarks(false);
            vol.setShowTickLabels(false);
            // style nhẹ, không viền
            vol.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;");

            //CHỨC NĂNG CỦA THANH VOLUME
            vol.valueProperty().addListener((o, ov, nv) -> {
                double v = nv.doubleValue();
            
                // auto đổi icon theo giá trị slider
                if (v <= 0.0001) {          // ngưỡng ~0
                    Mute[0] = true;
                    volButton.setText("🔇");
                } else {
                    Mute[0] = false;
                    volButton.setText("🔊");
                }
            
                // TODO: gọi backend nếu có
                // playerApi.setVolume(v);
            });

           HBox rightBox = new HBox(12, volButton, vol, btnLike, btnRepeat);
           rightBox.setAlignment(Pos.CENTER_RIGHT);

           // ----- LẮP RÁP 3 CỤM: trái | giữa | phải -----
           playerBar.getChildren().addAll(leftBox, centerBox, rightBox);
           HBox block_3 = new HBox(playerBar);


        // ===== Full layout =====
        VBox FullDisplay = new VBox(10);
        FullDisplay.getChildren().addAll(block_1, block_3);
        FullDisplay.setAlignment(Pos.CENTER);
        FullDisplay.setPadding(new Insets(5, 5, 5, 5));
        FullDisplay.setStyle("-fx-background-color: #010101;");

        // ===== Scene =====
        Scene scene = new Scene(FullDisplay, 1250, 700);

        // ===== Resize logic =====
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double optionMenuWidth = (primaryStage.isMaximized() || primaryStage.isFullScreen()) ? 300 : 250;
            option_menu.setPrefWidth(optionMenuWidth);
            playerBar.setPrefWidth(width - 20); // thanh phát nhạc full rộng (trừ padding)
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double optionMenuHeight = height - 90; // chừa khoảng cho thanh phát + padding
            option_menu.setPrefHeight(Math.max(optionMenuHeight, 200)); // đảm bảo không quá nhỏ
            playerBar.setPrefHeight(70);
        });

        return scene;
    }  // <--- đóng method

}  // <--- đóng class
