package UserUI_Components.SearchBar;

import Default.Song;
import UserUI_Components.Sidebar_Options.HomeUI;
import UserUI_Components.Sidebar_Options.Album.SubAlbumUI;
import UserUI_Components.MainDisplay;

import javafx.application.Platform;
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
 * SearchBarUI
 * - Nhận query, lọc HomeUI.SONGS theo name/artist/album (case-insensitive).
 * - Trên: bảng Songs (# | Title | Artist | Time) — click để play như HomeUI.
 * - Dưới: bảng Albums (# | Album | Artist | Songs) — click mở SubAlbumUI chọn đúng album.
 * - Scrollbar & style bám theo cách bạn đã dùng ở Home/Album.
 */
public class SearchBarUI extends StackPane {

    // ====== DEPENDENCIES ======
    private final MainDisplay mainDisplay;
    private final Song.PlayerController controller;
    private final String query;

    // ====== LISTVIEWS ======
    private ListView<Song> lvSongs;
    private ListView<AlbumHit> lvAlbums;

    // ====== LAYOUT CONSTANTS (đồng bộ HomeUI/AlbumUI) ======
    private static final double W_IDX   = 36;   // cột #
    private static final double W_GAP   = 8;    // khoảng cách cột
    private static final double W_TIME  = 64;   // cột Time/Songs
    private static final double W_ART   = 260;  // cột Artist cố định
    private static final double PAD_ROW = 28;   // padding L+R của mỗi row Button

    // ====== CTOR ======
    public SearchBarUI(MainDisplay mainDisplay, String query) {
        this(mainDisplay, mainDisplay.getPlayerController(), query);
    }

    // (giữ bản có controller để tương thích các chỗ gọi cũ)
    public SearchBarUI(MainDisplay mainDisplay, Song.PlayerController controller, String query) {
        this.mainDisplay = mainDisplay;
        this.controller  = controller != null ? controller : mainDisplay.getPlayerController();
        this.query       = query == null ? "" : query.trim();
        buildUI();
    }

    // ====== UI BUILD ======
    private void buildUI() {
        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(makeTitle("Results for: \"" + query + "\""));

        Node songsSec  = makeSongsSection();
        Node albumsSec = makeAlbumsSection();

        VBox center = new VBox(18, songsSec, albumsSec);
        center.setFillWidth(true);
        VBox.setVgrow(songsSec,  Priority.ALWAYS);
        VBox.setVgrow(albumsSec, Priority.SOMETIMES);

        root.setCenter(center);
        getChildren().setAll(root);
    }

    // ====== TITLE ======
    private Node makeTitle(String txt) {
        Text t = new Text(txt);
        t.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");
        HBox box = new HBox(t);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    // =====================================================================================
    // SONGS SECTION (# | Title | Artist | Time) — hành vi & style y như HomeUI
    // =====================================================================================
    private Node makeSongsSection() {
        Label secTitle = new Label("Songs");
        secTitle.setStyle("-fx-text-fill:#FFFFFF; -fx-font-size:16px; -fx-font-weight:800;");

        // Lọc bài theo query (name/artist/album)
        var songs = HomeUI.SONGS.stream()
                .filter(this::songMatches)
                .collect(Collectors.toCollection(javafx.collections.FXCollections::observableArrayList));

        lvSongs = new ListView<>(songs);
        lvSongs.setStyle("-fx-background-color:#000000; -fx-control-inner-background: transparent;");
        lvSongs.setPadding(Insets.EMPTY);
        VBox.setVgrow(lvSongs, Priority.ALWAYS);

        Node header = makeHeader("Title", "Artist", "Time");
        bindHeaderWidth(header, lvSongs);

        lvSongs.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(Song s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setGraphic(null); setText(null); return; }

                Region row = makeSongRow(getIndex() + 1, s);
                row.prefWidthProperty().bind(lvSongs.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lvSongs.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
            }
        });

        restyleScrollBars(lvSongs);
        lvSongs.skinProperty().addListener((o, os, ns) -> restyleScrollBars(lvSongs));
        lvSongs.sceneProperty().addListener((o, os, ns) -> restyleScrollBars(lvSongs));
        lvSongs.widthProperty().addListener((o, ow, nw) -> restyleScrollBars(lvSongs));

        return new VBox(8, secTitle, header, lvSongs);
    }

    // =====================================================================================
    // ALBUMS SECTION (# | Album | Artist | Songs) — layout kiểu AlbumUI-Home
    // =====================================================================================
    private Node makeAlbumsSection() {
        Label secTitle = new Label("Albums");
        secTitle.setStyle("-fx-text-fill:#FFFFFF; -fx-font-size:16px; -fx-font-weight:800;");

        // Lấy danh sách album phù hợp:
        // - tên album chứa query, HOẶC
        // - có ít nhất 1 bài match query
        Set<String> byAlbumName = HomeUI.SONGS.stream()
                .map(Song::getAlbum)
                .filter(a -> a != null && !a.isBlank())
                .filter(a -> containsIgnoreCase(a, query))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> bySongHit = HomeUI.SONGS.stream()
                .filter(this::songMatches)
                .map(Song::getAlbum)
                .filter(a -> a != null && !a.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        byAlbumName.addAll(bySongHit); // hợp nhất, giữ thứ tự gặp đầu

        // Chuyển thành AlbumHit (album, artistLabel, count)
        var hitList = byAlbumName.stream()
                .map(a -> new AlbumHit(
                        a,
                        artistLabelForAlbum(a),
                        (int) HomeUI.SONGS.stream().filter(s -> eq(a, s.getAlbum())).count()
                ))
                .sorted(Comparator.comparing(AlbumHit::album, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toCollection(javafx.collections.FXCollections::observableArrayList));

        lvAlbums = new ListView<>(hitList);
        lvAlbums.setStyle("-fx-background-color:#000000; -fx-control-inner-background: transparent;");
        lvAlbums.setPadding(Insets.EMPTY);

        Node header = makeHeader("Album", "Artist", "Songs");
        bindHeaderWidth(header, lvAlbums);

        lvAlbums.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(AlbumHit ah, boolean empty) {
                super.updateItem(ah, empty);
                if (empty || ah == null) { setGraphic(null); setText(null); return; }

                Region row = makeAlbumRow(getIndex() + 1, ah);
                row.prefWidthProperty().bind(lvAlbums.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lvAlbums.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
            }
        });

        restyleScrollBars(lvAlbums);
        lvAlbums.skinProperty().addListener((o, os, ns) -> restyleScrollBars(lvAlbums));
        lvAlbums.sceneProperty().addListener((o, os, ns) -> restyleScrollBars(lvAlbums));
        lvAlbums.widthProperty().addListener((o, ow, nw) -> restyleScrollBars(lvAlbums));

        return new VBox(8, secTitle, header, lvAlbums);
    }

    // ====== SONG ROW (# | Title | Artist | Time) ======
    private Region makeSongRow(int index, Song song) {
        Label idx = new Label(String.valueOf(index));
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; -fx-font-weight:700;");

        Button play = new Button("▶");
        play.setBackground(Background.EMPTY);
        play.setBorder(Border.EMPTY);
        play.setStyle("-fx-text-fill:white; -fx-font-size:14px; -fx-cursor:hand;");
        play.setVisible(false);
        play.setOnAction(e -> controller.play(song));

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

        row.setOnAction(e -> controller.play(song));
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

    // ====== ALBUM ROW (# | Album | Artist | Songs) ======
    private Region makeAlbumRow(int index, AlbumHit ah) {
        Label idx = new Label(String.valueOf(index));
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; -fx-font-weight:700;");

        StackPane idxCell = new StackPane(idx);
        StackPane.setAlignment(idx, Pos.CENTER);

        Label album = new Label(ah.album());
        album.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        album.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label artist = new Label(ah.artistLabel());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label songs = new Label(String.valueOf(ah.count()));
        songs.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        GridPane grid = baseGrid();
        grid.add(idxCell, 0, 0);
        grid.add(album,  1, 0);
        grid.add(artist, 2, 0);
        GridPane.setHalignment(songs, javafx.geometry.HPos.RIGHT);
        grid.add(songs,  3, 0);

        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");
        grid.prefWidthProperty().bind(row.widthProperty().subtract(PAD_ROW));

        grid.widthProperty().addListener((o, ov, nv) -> {
            double fixed = W_IDX + W_ART + W_TIME + W_GAP * 3 + PAD_ROW;
            double wTitle = Math.max(120, nv.doubleValue() - fixed);
            album.setMaxWidth(wTitle);
            album.setPrefWidth(wTitle);
        });

        row.setOnAction(e -> {
            SubAlbumUI v = new SubAlbumUI(mainDisplay);
            v.selectAlbum(ah.album());
            mainDisplay.bindInto(v);
            mainDisplay.show(v);
        });

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color:#2A3038; -fx-background-radius:12; -fx-padding:10 14 10 14;"));
        row.setOnMouseExited (e -> row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;"));

        return row;
    }

    // ====== HEADER (dùng chung) ======
    private Node makeHeader(String colTitle, String colArtist, String colLast) {
        GridPane grid = baseGrid();

        Label h0 = head("#");
        Label h1 = head(colTitle);
        Label h2 = head(colArtist);
        Label h3 = head(colLast);

        grid.add(h0, 0, 0);
        grid.add(h1, 1, 0);
        grid.add(h2, 2, 0);
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);
        grid.add(h3, 3, 0);

        StackPane wrap = new StackPane(grid);
        wrap.setPadding(new Insets(6, 10, 6, 10));
        return wrap;
    }

    // Bind header theo chiều ngang của ListView
    private void bindHeaderWidth(Node headerWrap, ListView<?> lv) {
        if (headerWrap instanceof Region wrap) {
            wrap.prefWidthProperty().bind(lv.widthProperty());
            wrap.maxWidthProperty().bind(lv.widthProperty());
            if (!wrap.getChildrenUnmodifiable().isEmpty()
                    && wrap.getChildrenUnmodifiable().get(0) instanceof Region grid) {
                grid.prefWidthProperty().bind(wrap.widthProperty().subtract(20)); // trừ padding L/R
            }
        }
    }

    private Label head(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;");
        return l;
    }

    // 4 cột: # | grow | fixed(artist) | fixed(time/songs)
    private GridPane baseGrid() {
        GridPane g = new GridPane();
        g.setHgap(W_GAP);
        g.setVgap(0);
        g.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints(); // grow (title/album)
        c1.setHgrow(Priority.ALWAYS);

        ColumnConstraints c2 = new ColumnConstraints(W_ART); // artist fixed

        ColumnConstraints c3 = new ColumnConstraints(W_TIME); // time/songs fixed
        c3.setHalignment(javafx.geometry.HPos.RIGHT);

        g.getColumnConstraints().addAll(c0, c1, c2, c3);
        return g;
    }

    // ====== SCROLLBAR STYLE (như Home/Album) ======
    private void restyleScrollBars(ListView<?> lv) {
        if (lv == null) return;
        Platform.runLater(() -> {
            // Ẩn ngang
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {
                sb.setStyle("-fx-opacity:0; -fx-pref-height:0; -fx-max-height:0; -fx-padding:0;");
                sb.setDisable(true);
                sb.setManaged(false);
                sb.setVisible(false);
                sb.setPickOnBounds(false);
            }
            // Dọc mỏng, thumb xanh
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

    // ====== MATCHING UTILS ======
    private boolean songMatches(Song s) {
        if (query.isEmpty()) return false;
        return containsIgnoreCase(s.getName(),   query)
            || containsIgnoreCase(s.getArtist(), query)
            || containsIgnoreCase(s.getAlbum(),  query);
    }

    private static boolean containsIgnoreCase(String text, String q) {
        if (text == null || q == null) return false;
        return text.toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT));
    }

    private static boolean eq(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";
        return a.trim().equalsIgnoreCase(b.trim());
    }

    // ====== MODEL phụ cho bảng Albums ======
    private record AlbumHit(String album, String artistLabel, int count) {}

    private static String artistLabelForAlbum(String albumName) {
        if (albumName == null || albumName.isBlank()) return "Unknown";
        Set<String> artists = HomeUI.SONGS.stream()
                .filter(s -> albumName.trim().equalsIgnoreCase(
                        s.getAlbum() == null ? "" : s.getAlbum().trim()))
                .map(Song::getArtist)
                .filter(a -> a != null && !a.isBlank())
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (artists.isEmpty()) return "Unknown";
        if (artists.size() == 1) return artists.iterator().next();
        return "Various Artists";
    }
}
