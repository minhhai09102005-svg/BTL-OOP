package UserUI_Components.Sidebar_Options;                                                                 // [OLD]

import Default.Song;                                                                     // [OLD]
import javafx.beans.binding.Bindings;                                                    // [OLD]
import javafx.collections.FXCollections;                                                 // [OLD]
import javafx.collections.ObservableList;                                                // [OLD]
import javafx.geometry.Insets;                                                           // [OLD]
import javafx.geometry.Pos;                                                              // [OLD]
import javafx.scene.Node;                                                                // [OLD]
import javafx.scene.control.*;                                                           // [OLD]
import javafx.scene.control.OverrunStyle;                                                // [OLD]
import javafx.scene.image.Image;                                                         // [OLD]
import javafx.scene.image.ImageView;                                                     // [OLD]
import javafx.scene.layout.*;                                                            // [OLD]
import javafx.scene.shape.Rectangle;                                                     // [OLD]
import javafx.scene.text.Text;                                                           // [OLD]

// ===== [ADD] import để làm CSS scrollbar y hệt HomeUI (debounce + re-apply) =====
import javafx.animation.PauseTransition;                                                 // [ADD] debounce tránh áp CSS liên tục
import javafx.application.Platform;                                                      // [ADD] chờ skin/children sẵn sàng rồi mới lookup
import javafx.scene.input.MouseEvent;                                                    // [ADD] lắng nghe chuột để re-apply
import javafx.scene.input.KeyEvent;                                                      // [ADD] lắng nghe phím để re-apply
import javafx.scene.input.ScrollEvent;                                                   // [ADD] lắng nghe cuộn để re-apply
import javafx.util.Duration;                                                             // [ADD] thời lượng cho PauseTransition
import java.util.List;

public class FavouriteUI extends StackPane {                                            // [OLD]

    // ======= kho Favourite dùng chung =======
    private static final ObservableList<Song> DATA = FXCollections.observableArrayList();

    /** Gọi khi toggle ♥ ở bất kỳ đâu */
    public static void onFavouriteToggled(Song s) {
        if (s == null) return;
        // Cập nhật UI tạm thời (để phản hồi ngay lập tức)
        Platform.runLater(() -> {
            if (s.isFavourite()) {
                if (!DATA.contains(s)) DATA.add(s);
            } else {
                DATA.remove(s);
            }
        });
        // Lưu ý: Database sẽ được cập nhật bởi code gọi method này
        // Sau đó sẽ gọi loadFromDatabase() để sync lại từ database
    }

    // (optional) tiện ích nếu muốn thao tác thủ công
    public static ObservableList<Song> items() { return DATA; }
    
    /**
     * Load favourite từ database
     */
    public static void loadFromDatabase() {
        try {
            Backend.Controller.SessionController session = Backend.Controller.SessionController.getInstance();
            if (!session.isLoggedIn()) {
                return; // Chưa đăng nhập
            }
            
            int userId = session.getCurrentUserId();
            Backend.Service.SongService songService = session.getSongService();
            List<Backend.Model.Song> dbFavourites = songService.getFavouriteSongs(userId);
            
            Platform.runLater(() -> {
                DATA.clear();
                
                for (Backend.Model.Song dbSong : dbFavourites) {
                    if (dbSong.getFilePath() != null) {
                        java.io.File file = new java.io.File(dbSong.getFilePath());
                        if (file.exists()) {
                            Default.Song uiSong = new Default.Song(
                                dbSong.getSongTitle() != null ? dbSong.getSongTitle() : "Unknown",
                                dbSong.getArtists() != null ? dbSong.getArtists() : "Unknown Artist",
                                dbSong.getDuration(),
                                "Single",
                                "Other",
                                dbSong.getFilePath()
                            );
                            uiSong.setFavourite(true);
                            DATA.add(uiSong);
                        }
                    }
                }
                System.out.println("✅ Đã load " + DATA.size() + " bài hát favourite từ database");
            });
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi load favourite từ database: " + e.getMessage());
        }
    }

    // ======= UI =======
    private final Song.PlayerController controller;                                     // [OLD]
    private ListView<Song> lv;                                                          // [OLD]

    // cỡ cột (khớp với HomeUI/PlaylistUI)
    private static final double W_IDX   = 36;                                           // [OLD]
    private static final double W_COVER = 40;                                           // [OLD]
    private static final double W_GAP   = 8;                                            // [OLD]
    private static final double W_TIME  = 64;                                           // [OLD]
    private static final double W_ART   = 280;                                          // [OLD]
    private static final double PAD_LR  = 28;                                           // [OLD]

    // ===== [ADD] Debounce cho việc re-apply CSS scrollbar =====
    private final PauseTransition sbDebounce = new PauseTransition(Duration.millis(50)); // [ADD]

    public FavouriteUI(Song.PlayerController controller) {                              // [OLD]
        this.controller = controller;                                                   // [OLD]

        setStyle("-fx-background-color:#010101;");                                      // [OLD]
        setPadding(new Insets(12));                                                     // [OLD]
        
        // ⭐ Load favourite từ database khi khởi động
        loadFromDatabase();

        // Title
        Text title = new Text("Favourites");                                            // [OLD]
        title.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");     // [OLD]

        // Empty label
        Label empty = new Label("No favourites yet. Tap ♥ to add songs.");              // [OLD]
        empty.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:14px;");                   // [OLD]

        // ===== [CHANGED] Tạo ListView TRƯỚC header để có thể bind width header theo lv =====
        lv = new ListView<>(DATA);                                                      // [CHANGED] (trước kia tạo header trước)
        lv.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;"); // [OLD]
        VBox.setVgrow(lv, Priority.ALWAYS);                                             // [OLD]
        // Chỉ chọn đơn
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // ===== CellFactory (giữ nguyên) =====
        lv.setCellFactory(list -> new ListCell<>() {                                    // [OLD]
            @Override protected void updateItem(Song s, boolean emptyCell) {
                super.updateItem(s, emptyCell);
                if (emptyCell || s == null) { setGraphic(null); setText(null); return; } // [OLD]

                Region row = makeRow(getIndex() + 1, s);                                // [OLD]
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));           // [OLD]
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));            // [OLD]

                setGraphic(row);                                                        // [OLD]
                setText(null);                                                          // [OLD]
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                         // [OLD]
                setPrefHeight(64);                                                      // [OLD]
                setBackground(Background.EMPTY);                                        // [OLD]

                // Style khi được chọn (màu xanh, chữ trắng)
                final Button rowBtn = (Button) row;
                if (rowBtn.getGraphic() instanceof GridPane grid) {
                    Label titleLbl = null;
                    Label artistLbl = null;
                    Label timeLbl = null;
                    for (javafx.scene.Node n : grid.getChildren()) {
                        if (n instanceof Label l) {
                            Integer col = GridPane.getColumnIndex(l);
                            if (col != null) {
                                // Favourite row has: idx (0), cover (1), title (2), artist (3), time (4)
                                if (col == 2) titleLbl = l;
                                if (col == 3) artistLbl = l;
                                if (col == 4) timeLbl = l;
                            }
                        }
                    }
                    final Label fTitle = titleLbl, fArtist = artistLbl, fTime = timeLbl;
                    java.util.function.Consumer<Boolean> applySel = isSel -> {
                        if (isSel) {
                            rowBtn.setStyle("-fx-background-color:#2563EB; -fx-background-radius:12; -fx-padding:10 14 10 14;");
                            if (fTitle != null) fTitle.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
                            if (fArtist != null) fArtist.setStyle("-fx-text-fill:white; -fx-font-size:13px; -fx-font-weight:700;");
                            if (fTime != null) fTime.setStyle("-fx-text-fill:white; -fx-font-size:13px; -fx-font-weight:700;");
                        } else {
                            rowBtn.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");
                            if (fTitle != null) fTitle.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
                            if (fArtist != null) fArtist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
                            if (fTime != null) fTime.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");
                        }
                    };
                    rowBtn.setOnMouseEntered(eh -> { if (!isSelected()) rowBtn.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;"); });
                    rowBtn.setOnMouseExited(eh -> applySel.accept(isSelected()));
                    selectedProperty().addListener((o, was, isSel) -> applySel.accept(isSel));
                    applySel.accept(isSelected());
                }
            }
        });

        // ===== Header cột (bind width theo lv vì lv đã có) =====
        Node header = makeHeader();                                                     // [CHANGED] giờ gọi sau khi tạo lv

        // Ẩn/hiện empty label theo DATA
        empty.visibleProperty().bind(Bindings.isEmpty(DATA));                           // [OLD]
        lv.visibleProperty().bind(empty.visibleProperty().not());                       // [OLD]

        // ===== [ADD] CSS thanh cuộn y hệt HomeUI + re-apply an toàn =====
        restyleScrollBars();                                                            // [ADD] gọi lần đầu
        lv.skinProperty().addListener((o, os, ns) -> scheduleRestyleScrollBars());      // [ADD] skin tái tạo → áp lại
        lv.sceneProperty().addListener((o, os, ns) -> scheduleRestyleScrollBars());     // [ADD] attach/detach scene
        lv.widthProperty().addListener((o, ow, nw) -> scheduleRestyleScrollBars());     // [ADD] resize
        lv.focusedProperty().addListener((o, ov, nv) -> scheduleRestyleScrollBars());   // [ADD] focus
        lv.hoverProperty().addListener((o, ov, nv) -> scheduleRestyleScrollBars());     // [ADD] hover
        lv.addEventFilter(MouseEvent.ANY,  e -> scheduleRestyleScrollBars());           // [ADD] mọi sự kiện chuột
        lv.addEventFilter(KeyEvent.ANY,    e -> scheduleRestyleScrollBars());           // [ADD] mọi sự kiện phím
        lv.addEventFilter(ScrollEvent.ANY, e -> scheduleRestyleScrollBars());           // [ADD] mọi sự kiện cuộn
        lv.getItems().addListener((javafx.collections.ListChangeListener<? super Song>) // [ADD] dữ liệu đổi
                c -> scheduleRestyleScrollBars());

        // Layout tổng
        VBox root = new VBox(12, title, header, empty, lv);                             // [OLD]
        root.setFillWidth(true);                                                        // [OLD]
        getChildren().setAll(root);                                                     // [OLD]
    }

    // ===== Header cột =====
    private Node makeHeader() {                                                         // [OLD]
        GridPane grid = new GridPane();                                                 // [OLD]
        grid.setHgap(W_GAP);                                                            // [OLD]
        grid.setAlignment(Pos.CENTER_LEFT);                                             // [OLD]

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);   c0.setHalignment(javafx.geometry.HPos.CENTER); // [OLD]
        ColumnConstraints c1 = new ColumnConstraints(W_COVER);                                                 // [OLD]
        ColumnConstraints c2 = new ColumnConstraints();         c2.setHgrow(Priority.ALWAYS);                  // [OLD]
        ColumnConstraints c3 = new ColumnConstraints(W_ART);                                                   // [OLD]
        ColumnConstraints c4 = new ColumnConstraints(W_TIME);   c4.setHalignment(javafx.geometry.HPos.RIGHT);  // [OLD]
        grid.getColumnConstraints().addAll(c0, c1, c2, c3, c4);                                                // [OLD]

        Label h0 = head("#");                                                                               // [OLD]
        Label h1 = head("Title");                                                                           // [OLD]
        Label h2 = head("Artist");                                                                          // [OLD]
        Label h3 = head("Time");                                                                            // [OLD]

        grid.add(h0, 0, 0);                                                                                 // [OLD]
        grid.add(h1, 2, 0);                                                                                 // [OLD]
        grid.add(h2, 3, 0);                                                                                 // [OLD]
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);                                             // [OLD]
        grid.add(h3, 4, 0);                                                                                 // [OLD]

        StackPane wrap = new StackPane(grid);                                                               // [OLD]
        wrap.setPadding(new Insets(6, 10, 6, 10));                                                          // [OLD]

        // bám theo list width để không phát sinh scroll ngang
        wrap.prefWidthProperty().bind(lv.widthProperty());                                                  // [CHANGED] bind trực tiếp vì lv đã tạo
        wrap.maxWidthProperty().bind(lv.widthProperty());                                                   // [CHANGED]
        grid.prefWidthProperty().bind(lv.widthProperty().subtract(20));                                     // [CHANGED]

        return wrap;                                                                                        // [OLD]
    }

    private Label head(String s) {                                                       // [OLD]
        Label l = new Label(s);                                                          // [OLD]
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;");  // [OLD]
        return l;                                                                         // [OLD]
    }

    // ===== Row giống HomeUI (không có menu remove — remove bằng ♥ ở PlayerBar) =====
    private Region makeRow(int index, Song song) {                                       // [OLD]
        // idx / ▶
        Label idx = new Label(String.valueOf(index));                                     // [OLD]
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; -fx-font-weight:700;");  // [OLD]
        Button play = new Button("▶");                                                    // [OLD]
        play.setBackground(Background.EMPTY); play.setBorder(Border.EMPTY);               // [OLD]
        play.setStyle("-fx-text-fill:white; -fx-font-size:14px; -fx-cursor:hand;");       // [OLD]
        play.setVisible(false);                                                           // [OLD]
        play.setOnAction(e -> { Utils.PlaybackQueue.setQueue(FavouriteUI.items()); controller.play(song); }); // set queue → play
        StackPane idxCell = new StackPane(idx, play);                                     // [OLD]
        StackPane.setAlignment(idx, Pos.CENTER); StackPane.setAlignment(play, Pos.CENTER);// [OLD]

        // cover
        ImageView cover = new ImageView();                                                // [OLD]
        try { cover.setImage(new Image(getClass().getResource(
                "/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm())); } // [OLD]
        catch (Exception ignore) {}                                                       // [OLD]
        cover.setFitWidth(W_COVER); cover.setFitHeight(W_COVER);                          // [OLD]
        Rectangle clip = new Rectangle(W_COVER, W_COVER);                                 // [OLD]
        clip.setArcWidth(8); clip.setArcHeight(8); cover.setClip(clip);                   // [OLD]

        // meta
        Label title = new Label(song.getName());                                          // [OLD]
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");  // [OLD]
        title.setTextOverrun(OverrunStyle.ELLIPSIS);                                      // [OLD]

        Label artist = new Label(song.getArtist());                                       // [OLD]
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");// [OLD]
        artist.setMaxWidth(W_ART);                                                        // [OLD]
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);                                     // [OLD]

        Label time = new Label(song.getFormattedDuration());                              // [OLD]
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;"); // [OLD]

        // Grid 5 cột
        GridPane grid = new GridPane();                                                   // [OLD]
        grid.setHgap(W_GAP);                                                              // [OLD]
        grid.setAlignment(Pos.CENTER_LEFT);                                               // [OLD]
        ColumnConstraints c0 = new ColumnConstraints(W_IDX);   c0.setHalignment(javafx.geometry.HPos.CENTER); // [OLD]
        ColumnConstraints c1 = new ColumnConstraints(W_COVER);                                               // [OLD]
        ColumnConstraints c2 = new ColumnConstraints();         c2.setHgrow(Priority.ALWAYS);                // [OLD]
        ColumnConstraints c3 = new ColumnConstraints(W_ART);                                                 // [OLD]
        ColumnConstraints c4 = new ColumnConstraints(W_TIME);   c4.setHalignment(javafx.geometry.HPos.RIGHT);// [OLD]
        grid.getColumnConstraints().addAll(c0, c1, c2, c3, c4);                                            // [OLD]

        grid.add(idxCell, 0, 0);                                                                            // [OLD]
        grid.add(cover,   1, 0);                                                                            // [OLD]
        grid.add(title,   2, 0);                                                                            // [OLD]
        grid.add(artist,  3, 0);                                                                            // [OLD]
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);                                           // [OLD]
        grid.add(time,    4, 0);                                                                            // [OLD]

        grid.widthProperty().addListener((o, ov, nv) -> {                                                   // [OLD]
            double totalFixed = W_IDX + W_COVER + W_ART + W_TIME + W_GAP*4 + PAD_LR;                        // [OLD]
            double wTitle = Math.max(80, nv.doubleValue() - totalFixed);                                    // [OLD]
            title.setMaxWidth(wTitle);                                                                       // [OLD]
            title.setPrefWidth(wTitle);                                                                      // [OLD]
        });

        Button row = new Button();                                                                           // [OLD]
        row.setGraphic(grid);                                                                                // [OLD]
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                                                  // [OLD]
        row.setAlignment(Pos.CENTER_LEFT);                                                                   // [OLD]
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");    // [OLD]
        grid.prefWidthProperty().bind(row.widthProperty().subtract(28));                                     // [OLD]
        row.setOnAction(e -> { Utils.PlaybackQueue.setQueue(FavouriteUI.items()); controller.play(song); }); // set queue → play

        row.setOnMouseEntered(e -> {                                                                         // [OLD]
            row.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;");// [OLD]
            idx.setVisible(false); play.setVisible(true);                                                    // [OLD]
        });
        row.setOnMouseExited(e -> {                                                                          // [OLD]
            row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");// [OLD]
            play.setVisible(false); idx.setVisible(true);                                                    // [OLD]
        });

        return row;                                                                                          // [OLD]
    }

    // ===================== [ADD] Debounce helper (giống HomeUI) =====================
    private void scheduleRestyleScrollBars() {                                   // [ADD]
        sbDebounce.setOnFinished(e -> restyleScrollBars());                      // [ADD]
        sbDebounce.playFromStart();                                              // [ADD]
    }

    // ===================== [ADD] CSS Scrollbar — y hệt HomeUI =====================
    private void restyleScrollBars() {                                           // [ADD]
        if (lv == null) return;                                                  // [ADD]
        Platform.runLater(() -> {                                                // [ADD] chờ skin/render xong
            // 1) ẨN HOÀN TOÀN THANH NGANG
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {             // [ADD]
                sb.setStyle("-fx-opacity: 0;" +                                  // [ADD]
                            "-fx-pref-height: 0;" +                              // [ADD]
                            "-fx-max-height: 0;" +                               // [ADD]
                            "-fx-padding: 0;");                                  // [ADD]
                sb.setDisable(true);                                             // [ADD]
                sb.setManaged(false);                                            // [ADD]
                sb.setVisible(false);                                            // [ADD]
                sb.setPickOnBounds(false);                                       // [ADD]
            }

            // 2) DỌC: mỏng, track trong suốt, thumb xanh lá, bo viên thuốc
            for (Node sb : lv.lookupAll(".scroll-bar:vertical")) {               // [ADD]
                sb.setStyle("-fx-pref-width: 8;" +                               // [ADD] mỏng
                            "-fx-max-width: 8;" +                                // [ADD]
                            "-fx-background-color: transparent;" +               // [ADD]
                            "-fx-background-insets: 0;" +                        // [ADD]
                            "-fx-padding: 4 2 4 2;");                            // [ADD]

                if (sb instanceof Region vbar) {                                 // [ADD]
                    // Ẩn 2 nút mũi tên
                    Node inc = vbar.lookup(".increment-button");                  // [ADD]
                    if (inc instanceof Region r) {
                        r.setStyle("-fx-opacity:0;-fx-padding:0;" +
                                   "-fx-pref-width:0;-fx-pref-height:0;" +
                                   "-fx-max-width:0;-fx-max-height:0;");
                        r.setManaged(false);                                     // [ADD]
                    }
                    Node dec = vbar.lookup(".decrement-button");                  // [ADD]
                    if (dec instanceof Region r) {
                        r.setStyle("-fx-opacity:0;-fx-padding:0;" +
                                   "-fx-pref-width:0;-fx-pref-height:0;" +
                                   "-fx-max-width:0;-fx-max-height:0;");
                        r.setManaged(false);                                     // [ADD]
                    }

                    // Track (đường ray) trong suốt + bo tròn
                    Node track = vbar.lookup(".track");                           // [ADD]
                    if (track instanceof Region r) {
                        r.setStyle("-fx-background-color:transparent;" +
                                   "-fx-background-insets:0;" +
                                   "-fx-background-radius:100;");
                    }

                    // Thumb (tay nắm) xanh lá + bo tròn
                    Node thumb = vbar.lookup(".thumb");                           // [ADD]
                    if (thumb instanceof Region r) {
                        r.setStyle("-fx-background-color:#22C55E;" +              // [ADD]
                                   "-fx-background-insets:0;" +
                                   "-fx-background-radius:100;" +
                                   "-fx-padding:0;");
                    }
                }
            }

            // 3) Ẩn ô vuông góc dưới (corner) nếu có
            for (Node corner : lv.lookupAll(".corner")) {                          // [ADD]
                if (corner instanceof Region r) {
                    r.setStyle("-fx-background-color: transparent;");
                }
            }
        });
    }
}