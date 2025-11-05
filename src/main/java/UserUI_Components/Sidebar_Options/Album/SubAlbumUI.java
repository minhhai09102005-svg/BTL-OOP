package UserUI_Components.Sidebar_Options.Album;

import Default.Song;
import UserUI_Components.Sidebar_Options.HomeUI;
import UserUI_Components.MainDisplay;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubAlbumUI extends StackPane {

    // ====== Controller play lấy từ MainDisplay ======
    private final Song.PlayerController controller;

    // ====== UI ======
    private ListView<Song> lv;
    private final SimpleStringProperty selectedAlbum = new SimpleStringProperty("");

    // ====== constants giống HomeUI (bản bỏ cover) ======
    private static final double W_IDX   = 36;
    private static final double W_GAP   = 8;
    private static final double W_TIME  = 64;
    private static final double W_ART   = 260;
    private static final double PAD_ROW = 28;

    // ====== dữ liệu: filtered theo album đang chọn ======
    private final FilteredList<Song> filtered =
            new FilteredList<>(HomeUI.SONGS, s -> false); // predicate set sau

    // ====== danh sách album hiện có (distinct, giữ thứ tự gặp đầu) ======
    private final ObservableList<String> albumList = FXCollections.observableArrayList();
    
    // trong SubAlbumUI
    public void selectAlbum(String album) { selectedAlbum.set(album); }


    // ====== Constructor: nhận MainDisplay, tự lấy controller, KHÔNG cần album sẵn ======
    public SubAlbumUI(MainDisplay mainDisplay) {
        this.controller = mainDisplay.getPlayerController();

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ---- khung tổng giống HomeUI ----
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");

        // tạo list trước để header bám width theo viewport
        Node listNode   = makeListView();
        Node headerNode = makeHeader();
        Node titleNode  = makeTitleBar(); // có ComboBox chọn album

        // khoá chiều ngang header & list tránh H-scroll
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
        root.setTop(titleNode);
        root.setCenter(center);

        getChildren().setAll(root);

        // ---- nạp danh sách album & set album mặc định ----
        refreshAlbumList();                 // lấy distinct từ SONGS
        if (!albumList.isEmpty()) {
            selectedAlbum.set(albumList.get(0));
        }

        // predicate lọc theo selectedAlbum (auto update)
        selectedAlbum.addListener((obs, ov, nv) ->
                filtered.setPredicate(s -> eqAlbum(s.getAlbum(), nv)));

        // khi SONGS thay đổi -> làm mới album list & đảm bảo album đang chọn vẫn hợp lệ
        HomeUI.SONGS.addListener((ListChangeListener<Song>) c -> {
            String oldSel = selectedAlbum.get();
            refreshAlbumList();
            if (albumList.isEmpty()) {
                selectedAlbum.set("");
                filtered.setPredicate(s -> false);
            } else if (!albumList.contains(oldSel)) {
                selectedAlbum.set(albumList.get(0));
            } else {
                // giữ nguyên album đang chọn
                selectedAlbum.set(oldSel);
            }
        });
    }

        // ===== thanh tiêu đề: "Albums" + tag album (không còn ComboBox) + count =====
        private Node makeTitleBar() {
            Text title = new Text("Albums");
            title.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");

            // Tag tên album (label tĩnh, không phải ComboBox)
            Label albumTag = new Label();
            albumTag.textProperty().bind(Bindings.createStringBinding(
                    () -> {
                        String a = selectedAlbum.get();
                        return (a == null || a.isBlank()) ? "Unknown" : a;
                    }, selectedAlbum
            ));
            albumTag.setStyle(
                "-fx-background-color:#1F242B;" +
                "-fx-text-fill:#FFFFFF;" +
                "-fx-font-size:14px;" +
                "-fx-font-weight:800;" +
                "-fx-background-radius:12;" +
                "-fx-padding:6 14 6 14;"
            );

            // Đếm số bài trong album đã chọn
            Label count = new Label();
            count.textProperty().bind(Bindings.createStringBinding(
                    () -> {
                        if (selectedAlbum.get() == null || selectedAlbum.get().isBlank()) return "";
                        long n = HomeUI.SONGS.stream()
                                .filter(s -> eqAlbum(s.getAlbum(), selectedAlbum.get()))
                                .count();
                        return " (" + n + (n == 1 ? " song)" : " songs)");
                    },
                    HomeUI.SONGS, selectedAlbum
            ));
            count.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:13px; -fx-font-weight:700;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox bar = new HBox(10, title, albumTag, count, spacer);
            bar.setAlignment(Pos.CENTER_LEFT);
            bar.setPadding(new Insets(0, 0, 12, 0));
            return bar;
        }


    // ===== header: # | Title | Artist | Time =====
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

    // ===== listview: dùng filtered (theo album đang chọn) =====
    private Node makeListView() {
        // predicate ban đầu
        filtered.setPredicate(s -> eqAlbum(s.getAlbum(), selectedAlbum.get()));

        lv = new ListView<>(filtered);
        lv.setStyle("-fx-background-color:#000000; -fx-control-inner-background: transparent;");
        lv.setPadding(Insets.EMPTY);
        VBox.setVgrow(lv, Priority.ALWAYS);

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
            }
        });

        // style scrollbar như HomeUI
        restyleScrollBars();
        lv.skinProperty().addListener((obs, o, n) -> restyleScrollBars());
        lv.sceneProperty().addListener((obs, o, n) -> restyleScrollBars());
        lv.widthProperty().addListener((obs, o, n) -> restyleScrollBars());

        return lv;
    }

    // ===== một hàng giống HomeUI (# | Title | Artist | Time) =====
    private Region makeRow(int index, Song song) {
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

        // title chiếm phần còn lại
        grid.widthProperty().addListener((o, ov, nv) -> {
            double fixed = W_IDX + W_ART + W_TIME + W_GAP * 3 + PAD_ROW;
            double wTitle = Math.max(80, nv.doubleValue() - fixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        // hover: đổi nền, hiện ▶
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

    // ===== Grid 4 cột giống HomeUI =====
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

    // ===== ScrollBar styling giống HomeUI =====
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

    // ===== lấy danh sách album distinct từ SONGS (giữ thứ tự gặp đầu) =====
    private void refreshAlbumList() {
        Set<String> set = new LinkedHashSet<>();
        for (Song s : HomeUI.SONGS) {
            if (s.getAlbum() != null && !s.getAlbum().trim().isEmpty()) {
                set.add(s.getAlbum().trim());
            }
        }
        List<String> albums = set.stream().collect(Collectors.toList());
        albumList.setAll(albums);
    }

    // ===== so sánh album an toàn =====
    private static boolean eqAlbum(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";
        return a.trim().equalsIgnoreCase(b.trim());
    }
}
