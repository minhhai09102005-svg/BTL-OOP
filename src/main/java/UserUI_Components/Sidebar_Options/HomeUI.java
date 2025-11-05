package UserUI_Components.Sidebar_Options;

import Default.Song;
import Utils.FileChooserUtil;
import Utils.MetadataExtractor;
import Backend.Database.SongDAO;
import Backend.Controller.SessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.stage.Window;
import javafx.scene.media.Media;
import java.io.File;
import java.util.List;
import java.util.Map;

public class HomeUI extends StackPane {

    private final Song.PlayerController controller;
    private ListView<Song> lv;

    // layout constants
    private static final double W_IDX   = 36;
    private static final double W_GAP   = 8;
    private static final double W_TIME  = 64;
    private static final double W_ART   = 260;
    private static final double PAD_ROW = 28;

    // ===== DATA DÙNG CHUNG CHO CÁC UI KHÁC =====
    // ⚠️ ĐÃ COMMENT: Danh sách bài hát mặc định (hardcoded)
    // Bây giờ chỉ hiển thị các bài hát được thêm từ FileChooser
    public static final ObservableList<Song> SONGS = FXCollections.observableArrayList(
        // ===== VPOP =====
        // new Song("Đà Lạt Còn Mưa Không Em", "TRO-Music", 291, "Single", "VPOP"),
        // new Song("Em Có Nghe", "Hương Tràm", 248, "Sunset", "VPOP"),
        // new Song("Chuyện Đôi Ta", "Emcee L, Muộii", 232, "Midnight Stories", "VPOP"),
        // new Song("Thích Em Hơi Nhiều", "Wren Evans", 198, "Single", "VPOP"),
        // new Song("Nếu Lúc Đó", "Tlinh", 205, "Single", "VPOP"),
        // new Song("Dưới Những Cơn Mưa", "Mr. Siro", 312, "Single", "VPOP"),
        // new Song("Cơn Mưa Ngang Qua", "Sơn Tùng MTP", 265, "Single", "VPOP"),
        // new Song("Không Sao Mà Em Đây Rồi", "Suni Hạ Linh", 216, "Bloom", "VPOP"),
        // new Song("Một Nhà", "Da LAB", 230, "Single", "VPOP"),
        // new Song("Muộn Rồi Mà Sao Còn", "Sơn Tùng MTP", 245, "Single", "VPOP"),
        // new Song("Ngày Khác Lạ", "Đen, Giang Phạm, Triple D", 237, "Single", "VPOP"),
        // new Song("Mơ", "Vũ Cát Tường", 222, "Yours", "VPOP"),

        // ===== US-UK =====
        // new Song("Blinding Lights", "The Weeknd", 200, "After Hours", "US-UK"),
        // new Song("Levitating", "Dua Lipa", 203, "Future Nostalgia", "US-UK"),
        // new Song("As It Was", "Harry Styles", 167, "Harry's House", "US-UK"),
        // new Song("Shape of You", "Ed Sheeran", 233, "Divide", "US-UK"),
        // new Song("Ghost", "Justin Bieber", 154, "Justice", "US-UK"),
        // new Song("Anti-Hero", "Taylor Swift", 201, "Midnights", "US-UK"),
        // new Song("Bad Habits", "Ed Sheeran", 231, "=", "US-UK"),
        // new Song("Stay", "The Kid LAROI, Justin Bieber", 141, "F*ck Love 3", "US-UK"),
        // new Song("Unholy", "Sam Smith, Kim Petras", 156, "Gloria", "US-UK"),
        // new Song("Dance Monkey", "Tones and I", 209, "The Kids Are Coming", "US-UK"),
        // new Song("Sunflower", "Post Malone, Swae Lee", 158, "Spider-Verse", "US-UK"),
        // new Song("Heat Waves", "Glass Animals", 238, "Dreamland", "US-UK"),

        // ===== Rap =====
        // new Song("Lối Nhỏ", "Đen, Phương Anh Đào", 254, "Show của Đen", "Rap"),
        // new Song("Mang Tiền Về Cho Mẹ", "Đen, Nguyên Thảo", 270, "Single", "Rap"),
        // new Song("Ngày Khác Lạ (Rap ver.)", "Đen", 240, "Single", "Rap"),
        // new Song("Đi Về Nhà", "Đen, JustaTee", 214, "Single", "Rap"),
        // new Song("Ai Muốn Nghe Không", "Đen", 192, "Single", "Rap"),
        // new Song("1 Phút", "Andiez (Rap mix)", 210, "Single", "Rap"),
        // new Song("Simple Love (Remix)", "Obito, Seachains", 189, "Single", "Rap"),
        // new Song("Gu", "Freaky, Seachains", 175, "Single", "Rap"),

        // ===== Rock (Nirvana - Nevermind) =====
        // new Song("Smells Like Teen Spirit", "Nirvana", 301, "Nevermind", "Rock"),
        // new Song("In Bloom", "Nirvana", 254, "Nevermind", "Rock"),
        // new Song("Come as You Are", "Nirvana", 219, "Nevermind", "Rock"),
        // new Song("Breed", "Nirvana", 183, "Nevermind", "Rock"),
        // new Song("Lithium", "Nirvana", 257, "Nevermind", "Rock"),
        // new Song("Polly", "Nirvana", 176, "Nevermind", "Rock"),
        // new Song("Territorial Pissings", "Nirvana", 142, "Nevermind", "Rock"),
        // new Song("Drain You", "Nirvana", 223, "Nevermind", "Rock"),
        // new Song("Lounge Act", "Nirvana", 156, "Nevermind", "Rock"),
        // new Song("Stay Away", "Nirvana", 212, "Nevermind", "Rock"),
        // new Song("On a Plain", "Nirvana", 196, "Nevermind", "Rock"),
        // new Song("Something in the Way", "Nirvana", 230, "Nevermind", "Rock"),

        // ===== EDM =====
        // new Song("Faded", "Alan Walker", 212, "Different World", "EDM"),
        // new Song("The Nights", "Avicii", 176, "Stories", "EDM"),
        // new Song("Closer", "The Chainsmokers, Halsey", 244, "Collage", "EDM"),
        // new Song("Waiting For Love", "Avicii", 230, "Stories", "EDM"),
        // new Song("Silence", "Marshmello, Khalid", 181, "Single", "EDM"),
        // new Song("There For You", "Martin Garrix, Troye Sivan", 213, "Single", "EDM"),
        // new Song("Something Just Like This", "The Chainsmokers, Coldplay", 247, "Memories...Do Not Open", "EDM"),
        // new Song("Spectre", "Alan Walker", 229, "Single", "EDM"),

        // ===== Other =====
        // new Song("Zikir La Ilaha Illallah", "Hud", 563, "Single", "Other"),
        // new Song("Weightless", "Marconi Union", 505, "Ambient Transmissions", "Other"),
        // new Song("Comptine d'un autre été", "Yann Tiersen", 151, "Amélie OST", "Other"),
        // new Song("River Flows In You", "Yiruma", 195, "First Love", "Other"),
        // new Song("Tuần Trăng Mật Ở Bản", "Saigon Soul", 206, "Indochine", "Other"),
        // new Song("Kaze no Toorimichi", "Joe Hisaishi", 256, "Ghibli Works", "Other"),

        // ===== thêm =====
        // new Song("Anomimus", "CinBawi", 197, "Single", "Other"),
        // new Song("Dance (Vibez & Tunes Freestyle)", "Maestro Dj Brown", 172, "Single", "EDM"),
        // new Song("Yellow", "Coldplay", 269, "Parachutes", "US-UK"),
        // new Song("Viva La Vida", "Coldplay", 242, "Viva La Vida", "US-UK"),
        // new Song("Photograph", "Ed Sheeran", 258, "Multiply", "US-UK"),
        // new Song("Lạc Trôi", "Sơn Tùng MTP", 230, "Single", "VPOP"),
        // new Song("Bước Qua Nhau", "Vũ", 250, "Một Vạn Năm", "VPOP"),
        // new Song("Hơn Cả Yêu", "Đức Phúc", 248, "Single", "VPOP")
    );

    public HomeUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // ⭐ Load bài hát từ database khi khởi động
        loadSongsFromDatabase();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(makeTitle("Home"));

        Node listNode   = makeListView();
        Node headerNode = makeHeader();

        if (listNode instanceof Region r1) {
            r1.prefWidthProperty().bind(widthProperty());
            r1.maxWidthProperty().bind(widthProperty());
        }
        if (headerNode instanceof Region r2) {
            r2.prefWidthProperty().bind(widthProperty());
            r2.maxWidthProperty().bind(widthProperty());
        }

        VBox center = new VBox(8, headerNode, listNode);
        center.setFillWidth(true);
        VBox.setVgrow(listNode, Priority.ALWAYS);
        root.setCenter(center);

        getChildren().setAll(root);
    }

    private Node makeTitle(String txt) {
        Text t = new Text(txt);
        t.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");

        Button addBtn = new Button("+");
        addBtn.setFocusTraversable(false);
        addBtn.setStyle(
            "-fx-background-color:#22C55E;" +
            "-fx-text-fill:#03120A;" +
            "-fx-font-size:20px;" +
            "-fx-font-weight:900;" +
            "-fx-background-radius:10;" +
            "-fx-padding:2 16 2 16;" +
            "-fx-cursor: hand;"
        );
        
        // ⭐ XỬ LÝ KHI NHẤN NÚT +: MỞ FILE EXPLORER ĐỂ CHỌN NHẠC
        addBtn.setOnAction(e -> {
            Window ownerWindow = addBtn.getScene() != null ? addBtn.getScene().getWindow() : null;
            List<File> selectedFiles = FileChooserUtil.chooseAudioFiles(ownerWindow);
            
            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                // Thêm các file đã chọn vào danh sách
                for (File file : selectedFiles) {
                    addSongFromFile(file);
                }
                
                // Refresh list view
                Platform.runLater(() -> {
                    lv.refresh();
                });
                
                System.out.println("✅ Đã thêm " + selectedFiles.size() + " file nhạc vào danh sách");
            }
        });

        // Nút xóa hình chữ nhật — cùng màu nền với nút +
        Button delBtn = new Button("Delete");
        delBtn.setFocusTraversable(false);
        delBtn.setStyle(
            "-fx-background-color:#22C55E;" +
            "-fx-text-fill:#03120A;" +
            "-fx-font-size:14px;" +
            "-fx-font-weight:800;" +
            "-fx-background-radius:10;" +
            "-fx-padding:4 14 4 14;" +
            "-fx-cursor: hand;"
        );
        delBtn.setOnAction(e -> {
            // Xóa TẤT CẢ bài hát khỏi database và UI, không hỏi lại
            try {
                SongDAO songDAO = new SongDAO();
                Backend.Service.SongService songService = new Backend.Service.SongService();

                // Sao chép danh sách để tránh ConcurrentModification
                java.util.List<Song> snapshot = new java.util.ArrayList<>(SONGS);
                int deletedCount = 0;

                for (Song uiSong : snapshot) {
                    if (uiSong == null || uiSong.getFilePath() == null) continue;
                    Backend.Model.Song dbSong = songDAO.getSongByFilePath(uiSong.getFilePath());
                    if (dbSong != null && dbSong.getId() > 0) {
                        boolean ok = songService.deleteSong(dbSong.getId());
                        if (ok) deletedCount++;
                    }
                }

                SONGS.clear();

                // Nếu đang phát, reset PlayerBar
                if (controller instanceof UserUI_Components.PlayerBar pb) {
                    pb.stopAndReset();
                }

                System.out.println("✅ Đã xóa toàn bộ bài hát trong database (" + deletedCount + ") và làm trống Home");

                // Làm mới Playlist/Favourite
                try { UserUI_Components.Sidebar_Options.PlaylistUI.loadFromDatabase(); } catch (Exception ignore) {}
                try { UserUI_Components.Sidebar_Options.FavouriteUI.loadFromDatabase(); } catch (Exception ignore) {}
            } catch (Exception ex) {
                System.err.println("❌ Lỗi xóa tất cả bài: " + ex.getMessage());
            }
        });

        HBox box = new HBox(8, t, addBtn, delBtn);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    private Node makeHeader() {
        GridPane grid = baseGrid();

        Label h0 = head("#");
        Label h1 = head("Title");
        Label h2 = head("Artist");
        Label h3 = head("Time");

        grid.add(h0, 0, 0);
        grid.add(h1, 1, 0);
        grid.add(h2, 2, 0);
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);
        grid.add(h3, 3, 0);

        StackPane wrap = new StackPane(grid);
        wrap.setPadding(new Insets(6, 10, 6, 10));

        wrap.prefWidthProperty().bind(lv.widthProperty());
        wrap.maxWidthProperty().bind(lv.widthProperty());
        grid.prefWidthProperty().bind(wrap.widthProperty().subtract(20));

        return wrap;
    }

    private Label head(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;");
        return l;
    }

    // ===== listview =====
    private Node makeListView() {
        lv = new ListView<>(SONGS);
        lv.setStyle("-fx-background-color: #000000; -fx-control-inner-background: transparent;");
        lv.setPadding(Insets.EMPTY);
        VBox.setVgrow(lv, Priority.ALWAYS);
        // Chỉ chọn đơn
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lv.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(Song s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setGraphic(null); setText(null); return; }

                Region row = makeRow(getIndex() + 1, s);
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
                
                // Style khi được chọn (màu xanh, chữ trắng)
                final Button rowBtn = (Button) row; // makeRow trả về Button
                // Labels bên trong để đổi màu chữ
                // tìm qua grid children đã tạo ở makeRow bằng cách dựa trên Button.graphic
                if (rowBtn.getGraphic() instanceof GridPane grid) {
                    Label titleLbl = null;
                    Label artistLbl = null;
                    Label timeLbl = null;
                    // các vị trí đã add: title cột 1, artist cột 2, time cột 3
                    for (javafx.scene.Node n : grid.getChildren()) {
                        if (n instanceof Label l) {
                            Integer col = GridPane.getColumnIndex(l);
                            if (col != null) {
                                if (col == 1) titleLbl = l;
                                if (col == 2) artistLbl = l;
                                if (col == 3) timeLbl = l;
                            }
                        }
                    }
                    final Label fTitle = titleLbl, fArtist = artistLbl, fTime = timeLbl;
                    // Helper to apply style
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
                    // Replace hover handlers to respect selection
                    rowBtn.setOnMouseEntered(eh -> { if (!isSelected()) rowBtn.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;"); });
                    rowBtn.setOnMouseExited(eh -> applySel.accept(isSelected()));
                    // Listen selection
                    selectedProperty().addListener((o, was, isSel) -> applySel.accept(isSel));
                    // Apply initial
                    applySel.accept(isSelected());
                }
                
                // ⭐ Context menu để xóa bài hát
                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteItem = new MenuItem("🗑️ Xóa khỏi danh sách");
                deleteItem.setOnAction(e -> {
                    if (s != null) {
                        Platform.runLater(() -> {
                            SONGS.remove(s);
                            System.out.println("✅ Đã xóa: " + s.getName());

                            // Nếu đang phát bài này → dừng và reset PlayerBar
                            if (controller instanceof UserUI_Components.PlayerBar pb) {
                                Default.Song cur = pb.getCurrentSong();
                                if (cur != null && cur.getFilePath() != null && cur.getFilePath().equals(s.getFilePath())) {
                                    pb.stopAndReset();
                                }
                            }
                        });
                    }
                });
                contextMenu.getItems().add(deleteItem);
                setContextMenu(contextMenu);
            }
        });

        // Re-apply CSS scrollbar
        restyleScrollBars();
        lv.skinProperty().addListener((obs, oldSkin, newSkin) -> restyleScrollBars());
        lv.sceneProperty().addListener((obs, oldScene, newScene) -> restyleScrollBars());
        lv.widthProperty().addListener((obs, oldW, newW) -> restyleScrollBars());

        return lv;
    }
    
    /**
     * Load bài hát từ database vào danh sách
     */
    private void loadSongsFromDatabase() {
        try {
            SongDAO songDAO = new SongDAO();
            List<Backend.Model.Song> dbSongs = songDAO.getAllSongs();
            
            Platform.runLater(() -> {
                java.util.Set<String> existingPaths = new java.util.HashSet<>();
                for (Default.Song s : SONGS) {
                    if (s.getFilePath() != null) existingPaths.add(s.getFilePath());
                }
                int added = 0;
                for (Backend.Model.Song dbSong : dbSongs) {
                    Default.Song uiSong = convertToUISong(dbSong);
                    if (uiSong != null) {
                        String p = uiSong.getFilePath();
                        if (p != null && !existingPaths.contains(p)) {
                            SONGS.add(uiSong);
                            existingPaths.add(p);
                            added++;
                        }
                    }
                }
                System.out.println("✅ Đã load từ database, thêm mới: " + added + " bài");
            });
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi load bài hát từ database: " + e.getMessage());
            // Không block nếu không kết nối được database
        }
    }
    
    /**
     * Convert Backend.Model.Song sang Default.Song
     */
    private Default.Song convertToUISong(Backend.Model.Song dbSong) {
        if (dbSong == null || dbSong.getFilePath() == null) {
            return null; // Bỏ qua bài hát không có file path
        }
        
        // Kiểm tra file có tồn tại không
        File file = new File(dbSong.getFilePath());
        if (!file.exists()) {
            System.out.println("⚠️ File không tồn tại: " + dbSong.getFilePath());
            return null;
        }
        
        return new Default.Song(
            dbSong.getSongTitle() != null ? dbSong.getSongTitle() : "Unknown",
            dbSong.getArtists() != null ? dbSong.getArtists() : "Unknown Artist",
            dbSong.getDuration(),
            "Single", // Album - có thể load từ database nếu cần
            "Other",  // Genre - có thể load từ database nếu cần
            dbSong.getFilePath()
        );
    }

    // ===== một hàng =====
    private Region makeRow(int index, Song song) {
        Label idx = new Label(String.valueOf(index));
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; -fx-font-weight:700;");

        Button play = new Button("▶");
        play.setBackground(Background.EMPTY);
        play.setBorder(Border.EMPTY);
        play.setStyle("-fx-text-fill:white; -fx-font-size:14px; -fx-cursor:hand;");
        play.setVisible(false);
        play.setOnAction(e -> { Utils.PlaybackQueue.setQueue(SONGS); controller.play(song); });

        StackPane idxCell = new StackPane(idx, play);
        StackPane.setAlignment(idx, Pos.CENTER);
        StackPane.setAlignment(play, Pos.CENTER);

        Label title = new Label(song.getName());
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        title.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label time = new Label(song.getFormattedDuration());
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        GridPane grid = baseGrid();
        grid.add(idxCell, 0, 0);
        grid.add(title,   1, 0);
        grid.add(artist,  2, 0);
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
        grid.add(time,    3, 0);

        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");

        grid.prefWidthProperty().bind(row.widthProperty().subtract(PAD_ROW));

        grid.widthProperty().addListener((o, ov, nv) -> {
            double fixed = W_IDX + W_ART + W_TIME + W_GAP * 3 + PAD_ROW;
            double wTitle = Math.max(80, nv.doubleValue() - fixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        row.setOnAction(e -> { Utils.PlaybackQueue.setQueue(SONGS); controller.play(song); });
        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;");
            idx.setVisible(false);
            play.setVisible(true);
        });
        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");
            play.setVisible(false);
            idx.setVisible(true);
        });

        return row;
    }

    /** 4 columns: # | title (grow) | artist fixed | time fixed */
    private GridPane baseGrid() {
        GridPane g = new GridPane();
        g.setHgap(W_GAP);
        g.setVgap(0);
        g.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        ColumnConstraints c2 = new ColumnConstraints(W_ART);

        ColumnConstraints c3 = new ColumnConstraints(W_TIME);
        c3.setHalignment(javafx.geometry.HPos.RIGHT);

        g.getColumnConstraints().addAll(c0, c1, c2, c3);
        return g;
    }

    /**
     * Thêm bài hát từ file vào danh sách
     * Đọc metadata từ file MP3 bằng JAudioTagger
     */
    private void addSongFromFile(File file) {
        String fileName = file.getName();
        String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.') > 0 ? fileName.lastIndexOf('.') : fileName.length());
        String filePath = file.getAbsolutePath(); // ⭐ Lưu đường dẫn file
        
        // ⭐ ĐỌC METADATA TỪ FILE MP3
        MetadataExtractor extractor = new MetadataExtractor();
        Map<String, String> metadata = extractor.extract(filePath);
        
        // Lấy thông tin từ metadata, fallback về tên file nếu không có
        String title = metadata.getOrDefault("title", nameWithoutExt);
        String artist = metadata.getOrDefault("artist", "Unknown Artist");
        String album = metadata.getOrDefault("album", "Single");
        String genre = metadata.getOrDefault("genre", "Other");
        
        // Nếu không có metadata, thử parse từ tên file
        if (title.equals(nameWithoutExt) && nameWithoutExt.contains(" - ")) {
            int dashIndex = nameWithoutExt.indexOf(" - ");
            artist = nameWithoutExt.substring(0, dashIndex).trim();
            title = nameWithoutExt.substring(dashIndex + 3).trim();
        } else if (title.equals(nameWithoutExt) && nameWithoutExt.contains("-")) {
            int dashIndex = nameWithoutExt.indexOf("-");
            artist = nameWithoutExt.substring(0, dashIndex).trim();
            title = nameWithoutExt.substring(dashIndex + 1).trim();
        }
        
        // ⭐ LẤY DURATION THỰC TẾ TỪ FILE
        int durationSeconds = 180; // Default
        if (metadata.containsKey("duration")) {
            try {
                durationSeconds = Integer.parseInt(metadata.get("duration"));
            } catch (NumberFormatException e) {
                durationSeconds = getRealDuration(filePath);
            }
        } else {
            durationSeconds = getRealDuration(filePath);
        }
        
        // ⭐ Tạo Song object với metadata thực tế từ file MP3
        Song newSong = new Song(title, artist, durationSeconds, album, genre, filePath);
        
        // ⭐ Lưu vào database (chỉ lưu metadata và file_path, không lưu file)
        try {
            SongDAO songDAO = new SongDAO();
            
            // Kiểm tra file đã có trong database chưa (tránh duplicate)
            Backend.Model.Song existingSong = songDAO.getSongByFilePath(filePath);
            
            if (existingSong == null) {
                // Chưa có, thêm mới
                Backend.Model.Song dbSong = new Backend.Model.Song();
                dbSong.setSongTitle(title);
                dbSong.setArtists(artist);
                dbSong.setDuration(durationSeconds);
                dbSong.setFilePath(filePath);
                // Album và Genre có thể thêm sau nếu cần
                
                int songId = songDAO.addSong(dbSong);
                if (songId > 0) {
                    System.out.println("✅ Đã lưu vào database (ID: " + songId + ")");
                }
            } else {
                System.out.println("ℹ️ File đã có trong database (ID: " + existingSong.getId() + ")");
            }
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi lưu vào database: " + e.getMessage());
            // Vẫn thêm vào UI nếu không lưu được database
        }
        
        // Thêm vào UI
        Platform.runLater(() -> {
            // Kiểm tra tránh duplicate trong UI
            boolean exists = SONGS.stream().anyMatch(s -> s.getFilePath() != null && s.getFilePath().equals(filePath));
            if (!exists) {
                SONGS.add(newSong);
            }
        });
        
        System.out.println("✅ Đã thêm bài hát:");
        System.out.println("   Title: " + title);
        System.out.println("   Artist: " + artist);
        System.out.println("   Album: " + album);
        System.out.println("   Genre: " + genre);
        System.out.println("   Duration: " + newSong.getFormattedDuration());
        System.out.println("   File: " + filePath);
    }
    
    /**
     * Lấy duration thực tế từ file audio (giây)
     * Sử dụng JavaFX MediaPlayer để đọc duration
     */
    private int getRealDuration(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return 180; // Default
            }
            
            Media media = new Media(file.toURI().toString());
            javafx.scene.media.MediaPlayer tempPlayer = new javafx.scene.media.MediaPlayer(media);
            final int[] duration = {180}; // Default 180 giây
            
            // Đợi MediaPlayer ready để lấy duration
            tempPlayer.setOnReady(() -> {
                if (media.getDuration() != null && !media.getDuration().isUnknown()) {
                    duration[0] = (int) Math.round(media.getDuration().toSeconds());
                    System.out.println("✅ Duration: " + duration[0] + " giây từ " + file.getName());
                }
                tempPlayer.dispose();
            });
            
            // Đợi tối đa 2 giây để MediaPlayer load
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 2000 && duration[0] == 180) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            // Nếu vẫn không lấy được, thử kiểm tra trực tiếp
            if (duration[0] == 180 && media.getDuration() != null && !media.getDuration().isUnknown()) {
                duration[0] = (int) Math.round(media.getDuration().toSeconds());
            }
            
            return duration[0] > 0 ? duration[0] : 180;
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi lấy duration từ file: " + e.getMessage());
            e.printStackTrace();
            return 180; // Default 3 phút
        }
    }
    
    // Apply CSS cho ScrollBar
    private void restyleScrollBars() {
        if (lv == null) return;

        Platform.runLater(() -> {
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {
                sb.setStyle("-fx-opacity:0; -fx-pref-height:0; -fx-max-height:0; -fx-padding:0;");
                sb.setDisable(true);
                sb.setManaged(false);
                sb.setVisible(false);
                sb.setPickOnBounds(false);
            }

            for (Node sb : lv.lookupAll(".scroll-bar:vertical")) {
                sb.setStyle("-fx-pref-width:8; -fx-max-width:8; -fx-background-color:transparent; -fx-background-insets:0; -fx-padding:4 2 4 2;");

                Node inc = ((Region) sb).lookup(".increment-button");
                if (inc != null) { inc.setStyle("-fx-opacity:0; -fx-padding:0; -fx-pref-width:0; -fx-pref-height:0; -fx-max-width:0; -fx-max-height:0;"); inc.setManaged(false); }
                Node dec = ((Region) sb).lookup(".decrement-button");
                if (dec != null) { dec.setStyle("-fx-opacity:0; -fx-padding:0; -fx-pref-width:0; -fx-pref-height:0; -fx-max-width:0; -fx-max-height:0;"); dec.setManaged(false); }

                Node track = ((Region) sb).lookup(".track");
                if (track != null) {
                    track.setStyle("-fx-background-color:transparent; -fx-background-insets:0; -fx-background-radius:100;");
                }
                Node thumb = ((Region) sb).lookup(".thumb");
                if (thumb != null) {
                    thumb.setStyle("-fx-background-color:#22C55E; -fx-background-insets:0; -fx-background-radius:100; -fx-padding:0;");
                }
            }
        });
    }
}
