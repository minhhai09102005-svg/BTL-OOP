package UserUI_Components.Sidebar_Options.Album;

import Default.Song;
import UserUI_Components.Sidebar_Options.HomeUI;
import UserUI_Components.MainDisplay;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AlbumUI (layout giống HomeUI)
 * Bảng: # | Album | Artist | Songs
 * - Tự quét album từ HomeUI.SONGS (không tạo dữ liệu mới)
 * - Click 1 hàng => điều hướng sang SubAlbumUI và chọn đúng album
 */
public class AlbumUI extends StackPane {

    private final MainDisplay mainDisplay;
    private ListView<AlbumInfo> lv;

    // --- layout constants (giống HomeUI, bỏ cover) ---
    private static final double W_IDX   = 36;
    private static final double W_GAP   = 8;
    private static final double W_SONGS = 64;    // thay cho W_TIME
    private static final double W_ART   = 260;   // độ rộng cột Artist
    private static final double PAD_ROW = 28;    // padding L+R mỗi row (14 + 14)

    // Nguồn dữ liệu: danh sách album đã gom sẵn
    private final ObservableList<AlbumInfo> albums = FXCollections.observableArrayList();

    public AlbumUI(MainDisplay mainDisplay) {
        this.mainDisplay = mainDisplay;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(makeTitle("Albums"));

        Node listNode   = makeListView();
        Node headerNode = makeHeader();

        // Khóa chiều ngang header & list để tránh H-scroll
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

        // Nạp dữ liệu ban đầu + lắng nghe thay đổi
        rebuildAlbums();
        HomeUI.SONGS.addListener((ListChangeListener<Song>) c -> rebuildAlbums());
    }

    // ===== title =====
    private Node makeTitle(String txt) {
        Text t = new Text(txt);
        t.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");
        HBox box = new HBox(8, t);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    // ===== header: # | Album | Artist | Songs =====
    private Node makeHeader() {
        GridPane grid = baseGrid();

        Label h0 = head("#");
        Label h1 = head("Album");
        Label h2 = head("Artist");
        Label h3 = head("Songs");

        grid.add(h0, 0, 0);
        grid.add(h1, 1, 0);
        grid.add(h2, 2, 0);
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);
        grid.add(h3, 3, 0);

        StackPane wrap = new StackPane(grid);
        wrap.setPadding(new Insets(6, 10, 6, 10));

        // bám theo ListView để không phát sinh H-scroll
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
        lv = new ListView<>(albums);
        lv.setStyle("-fx-background-color: #000000; -fx-control-inner-background: transparent;");
        lv.setPadding(Insets.EMPTY);
        VBox.setVgrow(lv, Priority.ALWAYS);

        lv.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(AlbumInfo ai, boolean empty) {
                super.updateItem(ai, empty);
                if (empty || ai == null) { setGraphic(null); setText(null); return; }

                Region row = makeRow(getIndex() + 1, ai);
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
            }
        });

        // CSS cho scrollbar (giống HomeUI)
        restyleScrollBars();
        lv.skinProperty().addListener((obs, oldSkin, newSkin) -> restyleScrollBars());
        lv.sceneProperty().addListener((obs, oldScene, newScene) -> restyleScrollBars());
        lv.widthProperty().addListener((obs, oldW, newW) -> restyleScrollBars());

        return lv;
    }

    // ===== 1 hàng album: # | album (grow) | artist (fixed) | songs (right) =====
    private Region makeRow(int index, AlbumInfo ai) {
        // Ô số thứ tự + hover chuyển thành mũi tên →
        Label idx = new Label(String.valueOf(index));
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; -fx-font-weight:700;");

        Button go = new Button("→");
        go.setBackground(Background.EMPTY);
        go.setBorder(Border.EMPTY);
        go.setStyle("-fx-text-fill:white; -fx-font-size:14px; -fx-cursor:hand;");
        go.setVisible(false);
        go.setOnAction(e -> navigateToAlbum(ai.album()));

        StackPane idxCell = new StackPane(idx, go);
        StackPane.setAlignment(idx, Pos.CENTER);
        StackPane.setAlignment(go, Pos.CENTER);

        // Album name (co giãn + ellipsis)
        Label title = new Label(ai.album());
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        title.setTextOverrun(OverrunStyle.ELLIPSIS);

        // Artist (cố định W_ART + ellipsis)
        Label artist = new Label(ai.artistLabel());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        // Songs (cố định W_SONGS, căn phải)
        Label songs = new Label(String.valueOf(ai.count()));
        songs.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        // Lưới 4 cột
        GridPane grid = baseGrid();
        grid.add(idxCell, 0, 0);
        grid.add(title,   1, 0);
        grid.add(artist,  2, 0);
        GridPane.setHalignment(songs, javafx.geometry.HPos.RIGHT);
        grid.add(songs,   3, 0);

        // Button bọc row (hover đổi nền + hiện mũi tên)
        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");
        grid.prefWidthProperty().bind(row.widthProperty().subtract(PAD_ROW));

        // Tính bề rộng cột Album theo tổng width
        grid.widthProperty().addListener((o, ov, nv) -> {
            double fixed = W_IDX + W_ART + W_SONGS + W_GAP * 3 + PAD_ROW;
            double wTitle = Math.max(120, nv.doubleValue() - fixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        row.setOnAction(e -> navigateToAlbum(ai.album()));
        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;");
            idx.setVisible(false);
            go.setVisible(true);
        });
        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");
            go.setVisible(false);
            idx.setVisible(true);
        });

        return row;
    }

    // ===== Grid 4 cột (giống HomeUI, thay Time -> Songs) =====
    private GridPane baseGrid() {
        GridPane g = new GridPane();
        g.setHgap(W_GAP);
        g.setVgap(0);
        g.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints(); // Album grows
        c1.setHgrow(Priority.ALWAYS);

        ColumnConstraints c2 = new ColumnConstraints(W_ART); // Artist fixed

        ColumnConstraints c3 = new ColumnConstraints(W_SONGS); // Songs fixed
        c3.setHalignment(javafx.geometry.HPos.RIGHT);

        g.getColumnConstraints().addAll(c0, c1, c2, c3);
        return g;
    }

    // ===== ScrollBar styling giống HomeUI =====
    private void restyleScrollBars() {
        if (lv == null) return;
        Platform.runLater(() -> {
            // Ẩn hoàn toàn thanh ngang
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {
                sb.setStyle("-fx-opacity:0; -fx-pref-height:0; -fx-max-height:0; -fx-padding:0;");
                sb.setDisable(true);
                sb.setManaged(false);
                sb.setVisible(false);
                sb.setPickOnBounds(false);
            }
            // Thanh dọc mỏng, thumb xanh
            for (Node sb : lv.lookupAll(".scroll-bar:vertical")) {
                sb.setStyle("-fx-pref-width:8; -fx-max-width:8; -fx-background-color: transparent; -fx-background-insets:0; -fx-padding:4 2 4 2;");
                Node inc = ((Region) sb).lookup(".increment-button");
                if (inc != null) { inc.setStyle("-fx-opacity:0; -fx-padding:0; -fx-pref-width:0; -fx-pref-height:0; -fx-max-width:0; -fx-max-height:0;"); inc.setManaged(false); }
                Node dec = ((Region) sb).lookup(".decrement-button");
                if (dec != null) { dec.setStyle("-fx-opacity:0; -fx-padding:0; -fx-pref-width:0; -fx-pref-height:0; -fx-max-width:0; -fx-max-height:0;"); dec.setManaged(false); }
                Node track = ((Region) sb).lookup(".track");
                if (track != null) { track.setStyle("-fx-background-color: transparent; -fx-background-insets:0; -fx-background-radius:100;"); }
                Node thumb = ((Region) sb).lookup(".thumb");
                if (thumb != null) { thumb.setStyle("-fx-background-color:#22C55E; -fx-background-insets:0; -fx-background-radius:100; -fx-padding:0;"); }
            }
        });
    }

    // ===== gom album từ SONGS -> albums (AlbumInfo) =====
    private void rebuildAlbums() {
        // Gom theo album (bỏ rỗng), giữ thứ tự xuất hiện
        Map<String, List<Song>> byAlbum = HomeUI.SONGS.stream()
                .filter(s -> s.getAlbum() != null && !s.getAlbum().isBlank())
                .collect(Collectors.groupingBy(
                        s -> s.getAlbum().trim(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<AlbumInfo> list = new ArrayList<>();
        for (Map.Entry<String, List<Song>> e : byAlbum.entrySet()) {
            String album = e.getKey();
            List<Song> songs = e.getValue();
            int count = songs.size();

            // Nếu 1 artist duy nhất -> ghi tên đó, còn lại -> Various Artists
            String artistLabel;
            Set<String> artists = songs.stream()
                    .map(Song::getArtist)
                    .filter(a -> a != null && !a.isBlank())
                    .map(String::trim)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            if (artists.size() == 1) {
                artistLabel = artists.iterator().next();
            } else if (artists.isEmpty()) {
                artistLabel = "Unknown";
            } else {
                artistLabel = "Various Artists";
            }

            list.add(new AlbumInfo(album, artistLabel, count));
        }

        // (tuỳ chọn) sort theo tên album
        list.sort(Comparator.comparing(AlbumInfo::album, String.CASE_INSENSITIVE_ORDER));
        albums.setAll(list);
    }

    // ===== điều hướng sang SubAlbumUI và chọn album =====
    private void navigateToAlbum(String albumName) {
        SubAlbumUI view = new SubAlbumUI(mainDisplay); // SubAlbumUI lấy PlayerController từ mainDisplay
        view.selectAlbum(albumName);                   // preselect album
        mainDisplay.bindInto(view);
        mainDisplay.show(view);
    }

    // ===== record nhỏ giữ thông tin 1 album =====
    private record AlbumInfo(String album, String artistLabel, int count) {}
}
