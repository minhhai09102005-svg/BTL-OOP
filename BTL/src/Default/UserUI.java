package Default;

import javafx.application.*;
import javafx.event.*;
import javafx.util.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import javafx.scene.transform.*;
import javafx.scene.canvas.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.geometry.*;
import javafx.css.*;
import javafx.scene.media.*;
import javafx.animation.*;

import Sidebar_Options.ProfileUI;
import Sidebar_Options.PlaylistUI;
import Sidebar_Options.AlbumUI;
import Sidebar_Options.ArtUI;
import Sidebar_Options.TrendingUI;
import Sidebar_Options.GenreUI;

        
public class UserUI {

    public Scene getScene(Stage primaryStage) {
        // ===== Sidebar / Option menu =====
        VBox option_menu = new VBox();
        option_menu.setPrefSize(200, 400);
        option_menu.setStyle("-fx-background-color: #4A4A4A; -fx-background-radius: 10;");
        option_menu.setSpacing(8);
        option_menu.setPadding(new Insets(12));
        
        // ===== Main display (vùng đen hiển thị nội dung) =====
        StackPane mainDisplay = new StackPane();
        mainDisplay.setPrefSize(900, 400);
        mainDisplay.setStyle("-fx-background-color: #010101;");

        // Nội dung mặc định của mainDisplay (tạo 1 lần)
        StackPane defaultContent = new StackPane(new Label("Welcome"));
        defaultContent.setStyle("-fx-background-color: #010101;");
        defaultContent.prefWidthProperty().bind(mainDisplay.widthProperty());
        defaultContent.prefHeightProperty().bind(mainDisplay.heightProperty());
        mainDisplay.getChildren().setAll(defaultContent);
        
        // Lắp vào hàng đầu sidebar
        Label label_1 = new Label("MusicPlayer");
        label_1.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        label_1.setTranslateX(30);
        option_menu.getChildren().add(label_1);
        
        // ===== Profile (avatar tròn + nút user123) =====
        HBox profileRow = new HBox(10);
        profileRow.setAlignment(Pos.CENTER_LEFT);
        
        //Tạo ảnh đại diện
        ImageView avatar = new ImageView();
        try {
            Image img = new Image(getClass().getResource("/image/quỷ_sếch.jpg").toExternalForm()); 
            avatar.setImage(img);

            // == cover: cắt giữa ảnh thành hình vuông ==
            double iw = img.getWidth(), ih = img.getHeight();
            double side = Math.min(iw, ih);
            avatar.setViewport(new Rectangle2D((iw - side) / 2, (ih - side) / 2, side, side));
        } catch (Exception ignore) {}

        avatar.setFitWidth(36);
        avatar.setFitHeight(36);
        avatar.setPreserveRatio(false);        // vì đã crop vuông
        avatar.setClip(new Circle(18, 18, 18)); // bo tròn
        
        // Nút user123 (giữ logic cũ)
        Button btnProfile = new Button("user123");
        btnProfile.setMaxWidth(Double.MAX_VALUE);
        btnProfile.setAlignment(Pos.CENTER_LEFT);

        // ❗ BỎ viền/nền & focus ring
        btnProfile.setBackground(Background.EMPTY);
        btnProfile.setBorder(Border.EMPTY);
        btnProfile.setFocusTraversable(false);

        // Style mặc định: chỉ còn chữ
        btnProfile.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 8 6 8;"        // tuỳ: giữ vùng bấm thoải mái
        );
        // Hover: gạch chân, rời chuột: bỏ gạch chân
        btnProfile.setOnMouseEntered(e -> btnProfile.setUnderline(true));
        btnProfile.setOnMouseExited(e -> btnProfile.setUnderline(false));


        // Điều hướng sang ProfileUI khi bấm
        btnProfile.setOnAction(e -> {
            // ProfileUI là class bạn sẽ tạo (Pane/Region bất kỳ)
            ProfileUI profileView = new ProfileUI();
            profileView.prefWidthProperty().bind(mainDisplay.widthProperty());
            profileView.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(profileView);
        });
        profileRow.getChildren().addAll(avatar, btnProfile);
        option_menu.getChildren().add(profileRow);
        
        // ===== Nút Home (sidebar) =====
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
        // Hover nhẹ cho Home
        btnHome.setOnMouseEntered(e -> btnHome.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnHome.setOnMouseExited(e -> btnHome.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Quay về nội dung mặc định (không tạo view mới)
        btnHome.setOnAction(e -> mainDisplay.getChildren().setAll(defaultContent));

        option_menu.getChildren().add(btnHome);
        
        //Tạo nút My Playlists
        Button btnPlaylist = new Button("My Playlists");
        btnPlaylist.setPrefSize(160, 50);
        btnPlaylist.setMaxWidth(Double.MAX_VALUE);
        btnPlaylist.setAlignment(Pos.CENTER_LEFT);
        btnPlaylist.setStyle(
                "-fx-background-color: #2F3945;" +
                "-fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;"
        );
        // Hover nhẹ cho My Playlists
        btnPlaylist.setOnMouseEntered(e -> btnPlaylist.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnPlaylist.setOnMouseExited(e -> btnPlaylist.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Điều hướng sang PlaylistUI khi bấm
        btnPlaylist.setOnAction(e -> {
            // ProfileUI là class bạn sẽ tạo (Pane/Region bất kỳ)
            PlaylistUI playlistView = new PlaylistUI();
            playlistView.prefWidthProperty().bind(mainDisplay.widthProperty());
            playlistView.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(playlistView);
        });
        option_menu.getChildren().add(btnPlaylist);
        
        // Tạo nút My Albums
        Button btnAlbum = new Button("My Album");
        btnAlbum.setPrefSize(160, 50);
        btnAlbum.setMaxWidth(Double.MAX_VALUE);
        btnAlbum.setAlignment(Pos.CENTER_LEFT);
        btnAlbum.setStyle(
                "-fx-background-color: #2F3945;" +
                "-fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;"
        );
        // Hover nhẹ cho My Album
        btnAlbum.setOnMouseEntered(e -> btnAlbum.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnAlbum.setOnMouseExited(e -> btnAlbum.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Điều hướng sang AlbumUI khi bấm
        btnAlbum.setOnAction(e -> {
            AlbumUI albumView = new AlbumUI();
            albumView.prefWidthProperty().bind(mainDisplay.widthProperty());
            albumView.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(albumView);
        });

        // Thêm vào sidebar
        option_menu.getChildren().add(btnAlbum);
        
        // ========== Trending ==========
        Button btnTrending = new Button("Trending");
        btnTrending.setPrefSize(160, 50);
        btnTrending.setMaxWidth(Double.MAX_VALUE);
        btnTrending.setAlignment(Pos.CENTER_LEFT);
        btnTrending.setStyle(
                "-fx-background-color: #2F3945;" +
                "-fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;"
        );
        btnTrending.setOnMouseEntered(e -> btnTrending.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnTrending.setOnMouseExited(e -> btnTrending.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Điều hướng sang TrendingUI
        btnTrending.setOnAction(e -> {
            TrendingUI view = new TrendingUI();     // TODO: tạo class này (extends StackPane/VBox)
            view.prefWidthProperty().bind(mainDisplay.widthProperty());
            view.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(view);
        });

        // ========== Genres ==========
        Button btnGenres = new Button("Genres");
        btnGenres.setPrefSize(160, 50);
        btnGenres.setMaxWidth(Double.MAX_VALUE);
        btnGenres.setAlignment(Pos.CENTER_LEFT);
        btnGenres.setStyle(
                "-fx-background-color: #2F3945;" +
                "-fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;"
        );
        btnGenres.setOnMouseEntered(e -> btnGenres.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnGenres.setOnMouseExited(e -> btnGenres.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Điều hướng sang GenreUI
        btnGenres.setOnAction(e -> {
            GenreUI view = new GenreUI();           // TODO: tạo class này
            view.prefWidthProperty().bind(mainDisplay.widthProperty());
            view.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(view);
        });

        // ========== Artists ==========
        Button btnArtists = new Button("Artists");
        btnArtists.setPrefSize(160, 50);
        btnArtists.setMaxWidth(Double.MAX_VALUE);
        btnArtists.setAlignment(Pos.CENTER_LEFT);
        btnArtists.setStyle(
                "-fx-background-color: #2F3945;" +
                "-fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;"
        );
        btnArtists.setOnMouseEntered(e -> btnArtists.setStyle(
                "-fx-background-color: #394656; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        btnArtists.setOnMouseExited(e -> btnArtists.setStyle(
                "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
                "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        // Điều hướng sang ArtUI
        btnArtists.setOnAction(e -> {
            ArtUI view = new ArtUI();               // TODO: tạo class này
            view.prefWidthProperty().bind(mainDisplay.widthProperty());
            view.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().setAll(view);
        });

        // Thêm vào sidebar
        option_menu.getChildren().addAll(btnTrending, btnGenres, btnArtists);



        // ===== Block 1: Sidebar + MainDisplay =====
        HBox block_1 = new HBox(10, option_menu, mainDisplay);
        block_1.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(mainDisplay, Priority.ALWAYS); // cho mainDisplay chiếm hết phần còn lại

        // =====================================================================
        // ===================  BLOCK 3: PLAYER BAR  ============================
        // =====================================================================

        HBox playerBar = new HBox();
        playerBar.setAlignment(Pos.CENTER_LEFT);
        playerBar.setSpacing(16);
        playerBar.setPadding(new Insets(10, 14, 10, 14));
        playerBar.setPrefHeight(70);
        playerBar.setMinHeight(70);
        playerBar.setMaxHeight(70);
        playerBar.setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 10;");

        // ----- CỤM TRÁI: Bìa + tiêu đề + nghệ sĩ -----
        ImageView cover = new ImageView();
        try {
            // Đổi tên ảnh cho đúng classpath của bạn
            cover.setImage(new Image(getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm()));
        } catch (Exception ignore) { /* fallback nếu thiếu ảnh */ }
        cover.setFitWidth(48);
        cover.setFitHeight(48);
        // Bo góc bìa
        Rectangle clip = new Rectangle(48, 48);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        cover.setClip(clip);

        Label titleLbl = new Label("Be Cool");
        titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 800;");
        Label artistLbl = new Label("Ngọt");
        artistLbl.setStyle("-fx-text-fill: #B3B3B3; -fx-font-size: 12px; -fx-font-weight: 600;");
        VBox metaBox = new VBox(2, titleLbl, artistLbl);

        HBox leftBox = new HBox(10, cover, metaBox);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        // ----- CỤM GIỮA: Nút điều khiển + Thanh thời gian -----
        Button btnPrev = new Button("⏮");
        Button btnPlay = new Button("⏵");   // đổi ⏵/⏸ khi click
        Button btnNext = new Button("⏭");

        for (Button b : new Button[]{btnPrev, btnPlay, btnNext}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                       "-fx-font-size: 18px; -fx-font-weight: 700; -fx-cursor: hand;" +
                       "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        }

        // Toggle play/pause icon (front-end)
        final boolean[] isPlaying = {false};
        btnPlay.setOnAction(e -> {
            isPlaying[0] = !isPlaying[0];
            btnPlay.setText(isPlaying[0] ? "⏸" : "⏵");
            // TODO: gọi backend play()/pause() nếu có
        });

        HBox controlsRow = new HBox(20, btnPrev, btnPlay, btnNext);
        controlsRow.setAlignment(Pos.CENTER);

        // Slider tiến độ + nhãn thời gian
        int totalSeconds = 205; // demo: 3:25 (thời lượng thật nên lấy từ backend)
        Label lblCurrent = new Label("0:00");
        lblCurrent.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");
        Label lblTotal = new Label(String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60));
        lblTotal.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");

        Slider progress = new Slider(0, totalSeconds, 0);
        progress.setBlockIncrement(1);
        progress.setShowTickMarks(false);
        progress.setShowTickLabels(false);
        progress.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progress, Priority.ALWAYS);
        progress.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;"); // style nhẹ

        // Cập nhật nhãn thời gian khi kéo slider (front-end)
        progress.valueProperty().addListener((obs, ov, nv) -> {
            int s = nv.intValue();
            lblCurrent.setText(String.format("%d:%02d", s / 60, s % 60));
            // TODO: backend: seekTo(s) nếu cần
        });

        HBox timeRow = new HBox(10, lblCurrent, progress, lblTotal);
        timeRow.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(6, controlsRow, timeRow);
        centerBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerBox, Priority.ALWAYS); // cụm giữa co giãn chiếm rộng

        // ----- CỤM PHẢI: Nút âm lượng + slider + yêu thích + lặp -----
        // Nút volume (🔊/🔇)
        Button volButton = new Button("🔊");
        volButton.setBackground(Background.EMPTY);
        volButton.setBorder(Border.EMPTY);
        volButton.setFocusTraversable(false);
        volButton.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0;" +
                           "-fx-border-color: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;" +
                           "-fx-padding: 0; -fx-text-fill: white; -fx-font-size: 14px;");

        // Slider volume (0..1)
        Slider vol = new Slider(0, 1, 0.8);
        vol.setPrefWidth(110);
        vol.setMaxWidth(110);
        vol.setMinWidth(80);
        vol.setFocusTraversable(false);
        vol.setShowTickMarks(false);
        vol.setShowTickLabels(false);
        vol.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;");

        // Nhớ âm lượng > 0 gần nhất để unmute khôi phục
        final double[] lastVol = {vol.getValue()};
        final boolean[] muted = {false};

        // Auto đổi icon khi kéo slider
        vol.valueProperty().addListener((o, ov, nv) -> {
            double v = nv.doubleValue();
            if (v <= 0.0001) {           // ~0 => muted
                muted[0] = true;
                volButton.setText("🔇");
            } else {
                muted[0] = false;
                volButton.setText("🔊");
                lastVol[0] = v;
            }
            // TODO: backend: setVolume(v)
        });

        // Click nút volume: mute -> slider về 0, unmute -> khôi phục lastVol
        volButton.setOnAction(e -> {
            if (!muted[0]) {                      // đang unmute -> bật mute
                if (vol.getValue() <= 0.0001) lastVol[0] = 0.3; // fallback nếu đang 0
                vol.setValue(0);                  // listener sẽ đổi icon
            } else {                              // đang mute -> tắt mute
                vol.setValue(Math.max(lastVol[0], 0.05));
            }
        });

        // Nút yêu thích ♥ (trắng/đỏ)
        Button btnLike = new Button("♥");
        btnLike.setTextFill(Color.WHITE);
        btnLike.setBackground(Background.EMPTY);
        btnLike.setBorder(Border.EMPTY);
        btnLike.setFocusTraversable(false);
        btnLike.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-font-weight: 700;" +
                         " -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        final boolean[] liked = {false};
        btnLike.setOnAction(e -> {
            liked[0] = !liked[0];
            btnLike.setTextFill(liked[0] ? Color.RED : Color.WHITE);
            // TODO: backend: setLiked(liked[0])
        });

        // Nút lặp ↻ (trắng/xanh dương)
        Button btnRepeat = new Button("↻");
        btnRepeat.setTextFill(Color.WHITE);
        btnRepeat.setBackground(Background.EMPTY);
        btnRepeat.setBorder(Border.EMPTY);
        btnRepeat.setFocusTraversable(false);
        btnRepeat.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-font-weight: 700;" +
                           " -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        final boolean[] repeating = {false};
        btnRepeat.setOnAction(e -> {
            repeating[0] = !repeating[0];
            btnRepeat.setTextFill(repeating[0] ? Color.DODGERBLUE : Color.WHITE);
            // TODO: backend: setRepeat(repeating[0])
        });

        HBox rightBox = new HBox(12, volButton, vol, btnLike, btnRepeat);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // ----- Lắp ráp 3 cụm: trái | giữa | phải -----
        playerBar.getChildren().addAll(leftBox, centerBox, rightBox);

        // Gói playerBar vào block_3 để giữ cấu trúc cũ
        HBox block_3 = new HBox(playerBar);

        // ===== Full layout =====
        VBox FullDisplay = new VBox(10, block_1, block_3);
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
            playerBar.setPrefWidth(width - 20); // thanh phát nhạc full rộng (trừ padding của FullDisplay)
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double optionMenuHeight = height - 90; // chừa khoảng cho thanh phát + padding
            option_menu.setPrefHeight(Math.max(optionMenuHeight, 200));
            playerBar.setPrefHeight(70);
        });

        return scene;
    }
}
