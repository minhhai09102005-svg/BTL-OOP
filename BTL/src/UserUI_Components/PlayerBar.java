package UserUI_Components;

import Default.Song;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

// Thanh phát nhạc dưới cùng + nhận play(Song) từ HomeUI
public class PlayerBar extends HBox implements Song.PlayerController {

    // --- fields cần truy cập lại trong play()/toggle ---
    private final ImageView cover = new ImageView();
    private final Label titleLbl = new Label("Be Cool");
    private final Label artistLbl = new Label("Ngọt");
    private final Label lblCurrent = new Label("0:00");
    private final Label lblTotal = new Label("0:00");
    private final Slider progress = new Slider(0, 205, 0); // range sẽ set lại khi play()
    private final Button btnPrev = new Button("⏮");
    private final Button btnPlay = new Button("⏵");
    private final Button btnNext = new Button("⏭");

    private boolean isPlaying = false;
    private Song current;        // bài đang phát (để toggle/pause, cập nhật UI)

    public PlayerBar() {
        // // khung tổng
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);
        setPadding(new Insets(10, 14, 10, 14));
        setPrefHeight(70);
        setMinHeight(70);
        setMaxHeight(70);
        setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 10;");

        // ----- CỤM TRÁI -----
        // ảnh cover + meta bài hát
        try {
            cover.setImage(new Image(getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm()));
        } catch (Exception ignore) {}
        cover.setFitWidth(48);
        cover.setFitHeight(48);
        Rectangle clip = new Rectangle(48, 48);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        cover.setClip(clip);

        titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 800;");
        artistLbl.setStyle("-fx-text-fill: #B3B3B3; -fx-font-size: 12px; -fx-font-weight: 600;");
        VBox metaBox = new VBox(2, titleLbl, artistLbl);
        metaBox.setAlignment(Pos.CENTER_LEFT);

        // (+) Nút thêm vào My Playlists
        Button btnAddToPlaylist = new Button("⊕");
        btnAddToPlaylist.setBackground(Background.EMPTY);
        btnAddToPlaylist.setBorder(Border.EMPTY);
        btnAddToPlaylist.setFocusTraversable(false);
        btnAddToPlaylist.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 35px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0;"
        );
        btnAddToPlaylist.setOnMouseEntered(e -> { btnAddToPlaylist.setScaleX(1.25); btnAddToPlaylist.setScaleY(1.25); });
        btnAddToPlaylist.setOnMouseExited(e -> { btnAddToPlaylist.setScaleX(1.0); btnAddToPlaylist.setScaleY(1.0); });
        btnAddToPlaylist.setOnAction(e -> {
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Notification"); ok.setHeaderText(null);
            ok.setContentText("Added to \"My playlist\"");
            ok.showAndWait();
        });
        StackPane addWrap = new StackPane(btnAddToPlaylist);
        addWrap.setPrefSize(48, 48); addWrap.setMinSize(48, 48); addWrap.setMaxSize(48, 48);
        addWrap.setAlignment(Pos.CENTER);

        HBox leftBox = new HBox(20, cover, metaBox, addWrap);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        // ----- CỤM GIỮA -----
        // nhóm nút prev/play/next
        for (Button b : new Button[]{btnPrev, btnPlay, btnNext}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                    "-fx-font-size: 18px; -fx-font-weight: 700; -fx-cursor: hand;" +
                    "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        }
        btnPlay.setOnAction(e -> togglePause()); // bấm -> đảo play/pause

        HBox controlsRow = new HBox(20, btnPrev, btnPlay, btnNext);
        controlsRow.setAlignment(Pos.CENTER);

        // thanh thời gian + nhãn mm:ss
        lblCurrent.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");
        lblTotal.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 12px; -fx-font-weight: 700;");

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
        vol.setPrefWidth(110); vol.setMaxWidth(110); vol.setMinWidth(80);
        vol.setFocusTraversable(false);
        vol.setShowTickMarks(false); vol.setShowTickLabels(false);
        vol.setStyle("-fx-control-inner-background: #6B7280; -fx-base: #D1D5DB;");

        final double[] lastVol = {vol.getValue()};
        final boolean[] muted = {false};
        vol.valueProperty().addListener((o, ov, nv) -> {
            double v = nv.doubleValue();
            if (v <= 0.0001) { muted[0] = true; volButton.setText("🔇"); }
            else { muted[0] = false; volButton.setText("🔊"); lastVol[0] = v; }
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

    // --- nhận lệnh từ HomeUI: phát 1 bài hát & cập nhật UI ---
    @Override
    public void play(Song song) {
        // giữ tham chiếu bài hiện tại
        this.current = song;

        // cập nhật meta: tiêu đề/nghệ sĩ
        titleLbl.setText(song.getName());
        artistLbl.setText(song.getArtist());

        // cập nhật tổng thời lượng + reset thanh progress
        int totalSeconds = Math.max(0, song.getDurationSeconds());
        progress.setMax(totalSeconds);
        progress.setValue(0);
        lblCurrent.setText("0:00");
        lblTotal.setText(String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60));

        // chuyển icon sang pause và đánh dấu đang phát
        isPlaying = true;
        btnPlay.setText("⏸");

        // (tuỳ chọn) cập nhật ảnh cover theo album/genre 
        // TODO: load cover theo song nếu có đường dẫn ảnh

        // TODO: gọi audio engine thực tế để phát "song"
        System.out.println("Playing: " + song);
    }

    // --- đảo play/pause khi bấm nút ---
    public void togglePause() {
        if (current == null) return; // chưa có bài để play/pause
        isPlaying = !isPlaying;
        btnPlay.setText(isPlaying ? "⏸" : "⏵");
        // TODO: tạm dừng/tiếp tục audio thật
        System.out.println(isPlaying ? "Resumed" : "Paused");
    }
}
