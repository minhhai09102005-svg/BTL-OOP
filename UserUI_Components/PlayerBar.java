package UserUI_Components;

import Default.Song;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import UserUI_Components.Sidebar_Options.PlaylistUI;
import UserUI_Components.Sidebar_Options.FavouriteUI;


///** Thanh phát nhạc dưới cùng + nhận play(Song) từ HomeUI */
public class PlayerBar extends HBox implements Song.PlayerController {

    // --- fields cần truy cập lại trong play()/toggle ---
    private final Button btnLike = new Button("♥"); // [ADDED] giữ tham chiếu để sync màu khi đổi bài
    private final ImageView cover = new ImageView();
    private final Label titleLbl = new Label("Song");
    private final Label artistLbl = new Label("Artist");
    private final Label lblCurrent = new Label("0:00");
    private final Label lblTotal = new Label("0:00");
    private final Slider progress = new Slider(0, 205, 0); // range sẽ set lại khi play()
    private final Button btnPrev = new Button("⏮");
    private final Button btnPlay = new Button("⏵");
    private final Button btnNext = new Button("⏭");

    // --- state ---
    private boolean isPlaying = false;
    private boolean liked = false;       // trạng thái trái tim ♥ (đồng bộ với current.isFavourite) [CHANGED: dùng làm cache hiển thị]
    private boolean repeating = false;   // trạng thái lặp ↻
    private Song current;                // bài đang phát

    public PlayerBar() {
        // ===== Khung tổng =====
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);
        setPadding(new Insets(10, 14, 10, 14));
        setPrefHeight(70);
        setMinHeight(70);
        setMaxHeight(70);
        setStyle("-fx-background-color: #000000; -fx-background-radius: 10;");

        // ===== CỤM TRÁI: cover + meta + add-to-playlist =====
        try {
            cover.setImage(new Image(getClass().getResource("/image/download.png").toExternalForm()));
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

        // (+) Nút thêm vào My Playlists (giữ hiệu ứng phóng to khi hover, đúng code cũ)
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
            if (current == null) return;           // chưa có bài nào đang phát
            if (!current.isPlaylist()) {           // chưa nằm trong playlist -> đánh dấu
                current.setPlaylist(true);
            }
            PlaylistUI.add(current);               // gọi thẳng sang PlaylistUI (static)
        });

        StackPane addWrap = new StackPane(btnAddToPlaylist);
        addWrap.setPrefSize(48, 48); addWrap.setMinSize(48, 48); addWrap.setMaxSize(48, 48);
        addWrap.setAlignment(Pos.CENTER);

        HBox leftBox = new HBox(20, cover, metaBox, addWrap);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        // ===== CỤM GIỮA: prev/play/next + thời gian =====
        for (Button b : new Button[]{btnPrev, btnPlay, btnNext}) {
            b.setBackground(Background.EMPTY);
            b.setBorder(Border.EMPTY);
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                    "-fx-font-size: 18px; -fx-font-weight: 700; -fx-cursor: hand;" +
                    "-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        }
        btnPrev.setOnAction(e -> onPrev());
        btnPlay.setOnAction(e -> togglePause());
        btnNext.setOnAction(e -> onNext());

        HBox controlsRow = new HBox(20, btnPrev, btnPlay, btnNext);
        controlsRow.setAlignment(Pos.CENTER);

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
            // TODO backend: seekTo(s)
        });

        HBox timeRow = new HBox(10, lblCurrent, progress, lblTotal);
        timeRow.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(6, controlsRow, timeRow);
        centerBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerBox, Priority.ALWAYS);

        // ===== CỤM PHẢI: volume + like + repeat =====
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
                vol.setValue(0); // mute
            } else {
                vol.setValue(Math.max(lastVol[0], 0.05)); // unmute
            }
        });

        // ♥ Like (toggle đỏ ⇄ trắng) — đồng bộ với Song.isFavourite
        btnLike.setTextFill(Color.WHITE);
        btnLike.setBackground(Background.EMPTY);
        btnLike.setBorder(Border.EMPTY);
        btnLike.setFocusTraversable(false);
        btnLike.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-font-weight: 700;" +
                " -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        btnLike.setOnAction(e -> {
            if (current == null) return;                 // không có bài thì bỏ qua
            boolean newFav = !current.isFavourite();     // đảo trạng thái yêu thích hiện tại
            current.setFavourite(newFav);                // ghi vào model bài hát
            liked = newFav;                              // cache hiển thị
            FavouriteUI.onFavouriteToggled(current);
            btnLike.setTextFill(newFav ? Color.RED : Color.WHITE); // đỏ nếu like, trắng nếu bỏ like
            // TODO backend: setLiked(current, newFav)
        });



        // ↻ Repeat toggle — giữ nguyên như cũ
        Button btnRepeat = new Button("↻");
        btnRepeat.setTextFill(Color.WHITE);
        btnRepeat.setBackground(Background.EMPTY);
        btnRepeat.setBorder(Border.EMPTY);
        btnRepeat.setFocusTraversable(false);
        btnRepeat.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-font-weight: 700;" +
                " -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        btnRepeat.setOnAction(e -> {
            repeating = !repeating;
            btnRepeat.setTextFill(repeating ? Color.DODGERBLUE : Color.WHITE);
            // TODO backend: setRepeat(repeating)
        });

        HBox rightBox = new HBox(12, volButton, vol, btnLike, btnRepeat);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        // ===== LẮP RÁP =====
        getChildren().addAll(leftBox, centerBox, rightBox);
    }

    // --- nhận lệnh từ HomeUI: phát 1 bài hát & cập nhật UI ---
    @Override
    public void play(Song song) {
        this.current = song;

        // Meta
        titleLbl.setText(song.getName());
        artistLbl.setText(song.getArtist());

        // Tổng thời lượng + reset progress
        int totalSeconds = Math.max(0, song.getDurationSeconds());
        progress.setMax(totalSeconds);
        progress.setValue(0);
        lblCurrent.setText("0:00");
        lblTotal.setText(String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60));

        // Play state
        isPlaying = true;
        btnPlay.setText("⏸");

        // [ADDED] Đồng bộ trạng thái ♥ theo bài đang phát
        // Sync trạng thái favourite -> màu trái tim
        liked = song.isFavourite();                               // lấy trạng thái từ model
        btnLike.setTextFill(liked ? Color.RED : Color.WHITE);     // đỏ nếu đã like, trắng nếu chưa

        // (ở đây không giữ tham chiếu btnLike nên không set được trực tiếp; ta đã set trong onAction.
        // => Giải pháp tối giản: set lại màu bằng cách dò trong rightBox hoặc giữ tham chiếu nút.)
        // ==> Giữ tối giản: lưu tham chiếu khi tạo nút:
    }

    // --- đảo play/pause khi bấm nút (giữ logic cũ) ---
    @Override
    public void togglePause() {
        if (current == null) return; // chưa có bài để play/pause
        isPlaying = !isPlaying;
        btnPlay.setText(isPlaying ? "⏸" : "⏵");
        // TODO: tạm dừng/tiếp tục audio thật
        System.out.println(isPlaying ? "Resumed" : "Paused");
    }

    // --- xử lý Prev/Next (đặt TODO để gọi playlist) ---
    private void onPrev() {
        // TODO: gọi playlist controller để lùi bài, sau đó play(nextSong)
        System.out.println("Prev clicked");
    }
    private void onNext() {
        // TODO: gọi playlist controller để tiến bài, sau đó play(nextSong)
        System.out.println("Next clicked");
    }

    // --- expose state nếu cần ---
    public boolean isLiked() { return liked; }
    public boolean isRepeating() { return repeating; }
    public boolean isPlaying() { return isPlaying; }
}