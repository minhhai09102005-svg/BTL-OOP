package UserUI_Components;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

///** Thanh phát nhạc dưới cùng (giữ nguyên logic play/pause/volume/like/repeat) */
public class PlayerBar extends HBox {

    public PlayerBar() {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);
        setPadding(new Insets(10, 14, 10, 14));
        setPrefHeight(70);
        setMinHeight(70);
        setMaxHeight(70);
        setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 10;");

        // ----- CỤM TRÁI -----
        //Ảnh và tên bài hát đang phát
        ImageView cover = new ImageView();
        try {
            cover.setImage(new Image(getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm()));
        } catch (Exception ignore) {}
        cover.setFitWidth(48);
        cover.setFitHeight(48);
        Rectangle clip = new Rectangle(48, 48);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        cover.setClip(clip);

        Label titleLbl = new Label("Be Cool");
        titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 800;");
        Label artistLbl = new Label("Ngọt");
        artistLbl.setStyle("-fx-text-fill: #B3B3B3; -fx-font-size: 12px; -fx-font-weight: 600;");
        VBox metaBox = new VBox(2, titleLbl, artistLbl);
        metaBox.setAlignment(Pos.CENTER_LEFT);        // <--- thêm dòng này

        // (+) Nút thêm vào My Playlists
        Button btnAddToPlaylist = new Button("⊕");
        btnAddToPlaylist.setBackground(Background.EMPTY);
        btnAddToPlaylist.setBorder(Border.EMPTY);
        btnAddToPlaylist.setFocusTraversable(false);
        btnAddToPlaylist.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +      // <-- đổi từ black -> white
            "-fx-font-size: 35px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0;"
        );

        // Hover: phóng to icon (không đổi layout)
        btnAddToPlaylist.setOnMouseEntered(e -> {
            btnAddToPlaylist.setScaleX(1.25);
            btnAddToPlaylist.setScaleY(1.25);
        });
        btnAddToPlaylist.setOnMouseExited(e -> {
            btnAddToPlaylist.setScaleX(1.0);
            btnAddToPlaylist.setScaleY(1.0);
        });
        
        btnAddToPlaylist.setOnAction(e -> {
           Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Notification");
            ok.setHeaderText(null);
            ok.setContentText("Added to \"My playlist\"");
            ok.showAndWait();
        });

        // Bọc nút trong khung 48x48 để không kéo lệch chữ
        StackPane addWrap = new StackPane(btnAddToPlaylist);
        addWrap.setPrefSize(48, 48);
        addWrap.setMinSize(48, 48);
        addWrap.setMaxSize(48, 48);
        addWrap.setAlignment(Pos.CENTER);   // <-- đảm bảo icon ở giữa

        HBox leftBox = new HBox(20, cover, metaBox, addWrap);  // <--- dùng addWrap thay vì nút trực tiếp
        leftBox.setAlignment(Pos.CENTER_LEFT);


        // ----- CỤM GIỮA -----
        Button btnPrev = new Button("⏮");
        Button btnPlay = new Button("⏵");
        Button btnNext = new Button("⏭");

        for (Button b : new Button[]{btnPrev, btnPlay, btnNext}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                    "-fx-font-size: 18px; -fx-font-weight: 700; -fx-cursor: hand;" +
                    "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        }

        final boolean[] isPlaying = {false};
        btnPlay.setOnAction(e -> {
            isPlaying[0] = !isPlaying[0];
            btnPlay.setText(isPlaying[0] ? "⏸" : "⏵");
            // TODO backend play/pause
        });

        HBox controlsRow = new HBox(20, btnPrev, btnPlay, btnNext);
        controlsRow.setAlignment(Pos.CENTER);

        int totalSeconds = 205; // demo
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
        progress.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;");

        progress.valueProperty().addListener((obs, ov, nv) -> {
            int s = nv.intValue();
            lblCurrent.setText(String.format("%d:%02d", s / 60, s % 60));
            // TODO backend seekTo(s)
        });

        HBox timeRow = new HBox(10, lblCurrent, progress, lblTotal);
        timeRow.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(6, controlsRow, timeRow);
        centerBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerBox, Priority.ALWAYS);

        // ----- CỤM PHẢI -----
        Button volButton = new Button("🔊");
        volButton.setBackground(Background.EMPTY);
        volButton.setBorder(Border.EMPTY);
        volButton.setFocusTraversable(false);
        volButton.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0;" +
                "-fx-border-color: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;" +
                "-fx-padding: 0; -fx-text-fill: white; -fx-font-size: 14px;");

        Slider vol = new Slider(0, 1, 0.8);
        vol.setPrefWidth(110);
        vol.setMaxWidth(110);
        vol.setMinWidth(80);
        vol.setFocusTraversable(false);
        vol.setShowTickMarks(false);
        vol.setShowTickLabels(false);
        vol.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;");

        final double[] lastVol = {vol.getValue()};
        final boolean[] muted = {false};

        vol.valueProperty().addListener((o, ov, nv) -> {
            double v = nv.doubleValue();
            if (v <= 0.0001) {
                muted[0] = true;
                volButton.setText("🔇");
            } else {
                muted[0] = false;
                volButton.setText("🔊");
                lastVol[0] = v;
            }
            // TODO backend setVolume(v)
        });

        volButton.setOnAction(e -> {
            if (!muted[0]) {
                if (vol.getValue() <= 0.0001) lastVol[0] = 0.3;
                vol.setValue(0);
            } else {
                vol.setValue(Math.max(lastVol[0], 0.05));
            }
        });

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
            // TODO backend setLiked(liked[0])
        });

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
            // TODO backend setRepeat(repeating[0])
        });

        HBox rightBox = new HBox(12, volButton, vol, btnLike, btnRepeat);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // ----- LẮP RÁP -----
        getChildren().addAll(leftBox, centerBox, rightBox);
    }
}
