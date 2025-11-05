package UserUI_Components;

import Default.Song;
import Utils.AudioPlayer;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import UserUI_Components.Sidebar_Options.PlaylistUI;
import UserUI_Components.Sidebar_Options.FavouriteUI;
import UserUI_Components.Sidebar_Options.HomeUI;
import Backend.Controller.SessionController;
import Backend.Database.SongDAO;
import Utils.PlaybackQueue;


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
    private final AudioPlayer audioPlayer = new AudioPlayer(); // ⭐ AudioPlayer để phát nhạc thật

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
            
            // ⭐ Lưu vào database
            try {
                SessionController session = SessionController.getInstance();
                if (session.isLoggedIn() && current.getFilePath() != null) {
                    SongDAO songDAO = new SongDAO();
                    Backend.Model.Song dbSong = songDAO.getSongByFilePath(current.getFilePath());
                    
                    // Nếu bài hát chưa có trong database, thêm vào trước
                    if (dbSong == null) {
                        System.out.println("⚠️ Bài hát chưa có trong database, đang thêm...");
                        Backend.Model.Song newSong = new Backend.Model.Song();
                        newSong.setSongTitle(current.getName());
                        newSong.setArtists(current.getArtist());
                        newSong.setDuration(current.getDurationSeconds());
                        newSong.setFilePath(current.getFilePath());
                        
                        int songId = songDAO.addSong(newSong);
                        if (songId > 0) {
                            dbSong = songDAO.getSongById(songId);
                            System.out.println("✅ Đã thêm bài hát vào database (ID: " + songId + ")");
                        } else {
                            System.err.println("❌ Không thể thêm bài hát vào database");
                            return;
                        }
                    }
                    
                    if (dbSong != null) {
                        int userId = session.getCurrentUserId();
                        int songId = dbSong.getId();
                        
                        boolean success = session.addToPlaylist(songId);
                        if (success) {
                            System.out.println("✅ Đã thêm vào Playlist (DB)");
                            // Refresh lại danh sách từ database
                            PlaylistUI.loadFromDatabase();
                        } else {
                            System.err.println("❌ Không thể thêm vào playlist (DB)");
                        }
                    }
                } else if (!session.isLoggedIn()) {
                    System.err.println("⚠️ Cần đăng nhập để lưu vào playlist");
                }
            } catch (Exception ex) {
                System.err.println("⚠️ Lỗi lưu playlist vào database: " + ex.getMessage());
                ex.printStackTrace();
            }
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
        
        // ⭐ Tham khảo MasterClass: Xử lý seek (di chuyển trong bài hát)
        final boolean[] isSeeking = {false}; // Flag để tránh vòng lặp
        progress.valueProperty().addListener((obs, ov, nv) -> {
            if (isSeeking[0]) return; // Đang seek, không update từ MediaPlayer
            int s = nv.intValue();
            lblCurrent.setText(String.format("%d:%02d", s / 60, s % 60));
        });
        
        // Khi user kéo slider, seek đến vị trí đó
        progress.setOnMousePressed(e -> isSeeking[0] = true);
        progress.setOnMouseReleased(e -> {
            if (isSeeking[0] && current != null) {
                int seekSeconds = (int) progress.getValue();
                audioPlayer.seek(seekSeconds);
                lblCurrent.setText(String.format("%d:%02d", seekSeconds / 60, seekSeconds % 60));
                isSeeking[0] = false;
            }
        });
        
        // Cập nhật progress từ MediaPlayer (tham khảo MasterClass - tracking progress)
        Timeline progressUpdater = new Timeline(
            new KeyFrame(javafx.util.Duration.seconds(0.1), e -> {
                if (!isSeeking[0] && audioPlayer.isPlaying()) {
                    double currentTime = audioPlayer.getCurrentTime();
                    if (currentTime > 0 && progress.getMax() > 0) {
                        progress.setValue(currentTime);
                    }
                }
            })
        );
        progressUpdater.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        progressUpdater.play();

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
            // ⭐ Tham khảo MasterClass: Set volume thực tế
            if (audioPlayer.getMediaPlayer() != null) {
                audioPlayer.getMediaPlayer().setVolume(v);
            }
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
            
            // ⭐ Lưu vào database
            try {
                SessionController session = SessionController.getInstance();
                if (session.isLoggedIn() && current.getFilePath() != null) {
                    // Tìm song ID từ database
                    SongDAO songDAO = new SongDAO();
                    Backend.Model.Song dbSong = songDAO.getSongByFilePath(current.getFilePath());
                    
                    // Nếu bài hát chưa có trong database, thêm vào trước
                    if (dbSong == null) {
                        System.out.println("⚠️ Bài hát chưa có trong database, đang thêm...");
                        Backend.Model.Song newSong = new Backend.Model.Song();
                        newSong.setSongTitle(current.getName());
                        newSong.setArtists(current.getArtist());
                        newSong.setDuration(current.getDurationSeconds());
                        newSong.setFilePath(current.getFilePath());
                        
                        int songId = songDAO.addSong(newSong);
                        if (songId > 0) {
                            dbSong = songDAO.getSongById(songId);
                            System.out.println("✅ Đã thêm bài hát vào database (ID: " + songId + ")");
                        } else {
                            System.err.println("❌ Không thể thêm bài hát vào database");
                            return;
                        }
                    }
                    
                    if (dbSong != null) {
                        int userId = session.getCurrentUserId();
                        int songId = dbSong.getId();
                        
                        // Dùng SongService để toggle favourite
                        boolean success = session.getSongService().toggleFavourite(userId, songId);
                        if (success) {
                            System.out.println("✅ Đã " + (newFav ? "thêm vào" : "xóa khỏi") + " Favourite (DB)");
                            // Refresh lại danh sách từ database
                            FavouriteUI.loadFromDatabase();
                        } else {
                            System.err.println("❌ Không thể " + (newFav ? "thêm vào" : "xóa khỏi") + " favourite (DB)");
                        }
                    }
                } else if (!session.isLoggedIn()) {
                    System.err.println("⚠️ Cần đăng nhập để lưu vào favourite");
                }
            } catch (Exception ex) {
                System.err.println("⚠️ Lỗi lưu favourite vào database: " + ex.getMessage());
                ex.printStackTrace();
                // Không block UI nếu lỗi database
            }
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

        // ⭐ PHÁT NHẠC THẬT - Tham khảo MasterClass.Play()
        String filePath = song.getFilePath();
        if (filePath != null && !filePath.isEmpty()) {
            // Load file (MediaPlayer sẽ tự động đợi ready trước khi play)
            audioPlayer.load(filePath);
            
            // ⭐ Tham khảo MasterClass: Đợi MediaPlayer ready rồi mới play
            // Đảm bảo MediaPlayer ready trước khi play (như MasterClass dòng 90-100)
            javafx.application.Platform.runLater(() -> {
                javafx.application.Platform.runLater(() -> {
                    audioPlayer.play();
                    // Khi bài hát phát xong
                    if (audioPlayer.getMediaPlayer() != null) {
                        audioPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                            System.out.println("✅ Bài hát đã phát xong");
                            if (repeating && current != null) {
                                System.out.println("🔁 Repeat đang bật — phát lại bài hiện tại");
                                audioPlayer.stop();
                                play(current);
                                return;
                            }

                            // Repeat tắt: chuyển bài tiếp theo trong queue hiện tại
                            isPlaying = false;
                            btnPlay.setText("⏵");
                            progress.setValue(0);
                            lblCurrent.setText("0:00");
                            
                            javafx.application.Platform.runLater(() -> {
                                javafx.collections.ObservableList<Song> playlist = PlaybackQueue.getQueue();
                                if (playlist != null && !playlist.isEmpty() && current != null) {
                                    int currentIndex = playlist.indexOf(current);
                                    if (currentIndex >= 0 && currentIndex < playlist.size() - 1) {
                                        System.out.println("🔄 Tự động chuyển sang bài tiếp theo...");
                                        onNext();
                                    } else {
                                        System.out.println("ℹ️ Đã phát hết danh sách");
                                    }
                                }
                            });
                        });
                    }
                });
            });
            
            isPlaying = true;
            btnPlay.setText("⏸");
        } else {
            System.err.println("⚠️ Bài hát không có filePath để phát: " + song.getName());
            isPlaying = false;
            btnPlay.setText("⏵");
        }

        // [ADDED] Đồng bộ trạng thái ♥ theo bài đang phát
        // Sync trạng thái favourite -> màu trái tim
        liked = song.isFavourite();                               // lấy trạng thái từ model
        btnLike.setTextFill(liked ? Color.RED : Color.WHITE);     // đỏ nếu đã like, trắng nếu chưa
    }

    // --- đảo play/pause khi bấm nút (giữ logic cũ) ---
    @Override
    public void togglePause() {
        if (current == null) return; // chưa có bài để play/pause
        
        isPlaying = !isPlaying;
        if (isPlaying) {
            audioPlayer.play();
            btnPlay.setText("⏸");
            System.out.println("▶️ Resumed");
        } else {
            audioPlayer.pause();
            btnPlay.setText("⏵");
            System.out.println("⏸ Paused");
        }
    }

    // --- xử lý Prev/Next - Tham khảo MusicPlayer.java dòng 137-203 ---
    private void onPrev() {
        if (current == null) {
            System.out.println("⚠️ Chưa có bài hát nào đang phát");
            return;
        }
        
        // ⭐ Lấy danh sách bài hát hiện tại từ PlaybackQueue
        javafx.collections.ObservableList<Song> playlist = PlaybackQueue.getQueue();
        if (playlist == null || playlist.isEmpty()) {
            System.out.println("⚠️ Danh sách bài hát trống");
            return;
        }
        
        // Tìm index của bài hiện tại
        int currentIndex = playlist.indexOf(current);
        if (currentIndex == -1) {
            System.out.println("⚠️ Không tìm thấy bài hát trong danh sách");
            return;
        }
        
        // Kiểm tra có thể lùi không (tham khảo MusicPlayer.java dòng 176)
        if (currentIndex - 1 < 0) {
            System.out.println("ℹ️ Đã ở bài đầu tiên, không thể lùi");
            return;
        }
        
        // Tham khảo MusicPlayer.java dòng 178-202: Stop và chuyển bài
        System.out.println("⏮ Chuyển về bài trước...");
        
        // Stop bài hiện tại
        audioPlayer.stop();
        
        // Giảm index và lấy bài trước
        currentIndex--;
        Song prevSong = playlist.get(currentIndex);
        
        // Phát bài trước
        play(prevSong);
    }
    
    private void onNext() {
        if (current == null) {
            System.out.println("⚠️ Chưa có bài hát nào đang phát");
            return;
        }
        
        // ⭐ Lấy danh sách bài hát hiện tại từ PlaybackQueue
        javafx.collections.ObservableList<Song> playlist = PlaybackQueue.getQueue();
        if (playlist == null || playlist.isEmpty()) {
            System.out.println("⚠️ Danh sách bài hát trống");
            return;
        }
        
        // Tìm index của bài hiện tại
        int currentIndex = playlist.indexOf(current);
        if (currentIndex == -1) {
            System.out.println("⚠️ Không tìm thấy bài hát trong danh sách");
            return;
        }
        
        // Kiểm tra có thể tiến không (tham khảo MusicPlayer.java dòng 142)
        if (currentIndex + 1 > playlist.size() - 1) {
            System.out.println("ℹ️ Đã ở bài cuối cùng, không thể tiến");
            return;
        }
        
        // Tham khảo MusicPlayer.java dòng 144-168: Stop và chuyển bài
        System.out.println("⏭ Chuyển sang bài tiếp theo...");
        
        // Stop bài hiện tại
        audioPlayer.stop();
        
        // Tăng index và lấy bài tiếp theo
        currentIndex++;
        Song nextSong = playlist.get(currentIndex);
        
        // Phát bài tiếp theo
        play(nextSong);
    }

    // --- expose state nếu cần ---
    public boolean isLiked() { return liked; }
    public boolean isRepeating() { return repeating; }
    public boolean isPlaying() { return isPlaying; }
    public Default.Song getCurrentSong() { return current; }

    // Reset UI về trạng thái mặc định và dừng nhạc
    public void stopAndReset() {
        try { audioPlayer.stop(); } catch (Exception ignore) {}
        isPlaying = false;
        btnPlay.setText("⏵");
        progress.setValue(0);
        lblCurrent.setText("0:00");
        lblTotal.setText("0:00");
        titleLbl.setText("Song");
        artistLbl.setText("Artist");
        try {
            cover.setImage(new Image(getClass().getResource("/image/download.png").toExternalForm()));
        } catch (Exception ignore) {}
        liked = false;
        btnLike.setTextFill(Color.WHITE);
        current = null;
    }
}