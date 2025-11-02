/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package UserUI_Components.Sidebar_Options.Genre;

import Default.Song;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import UserUI_Components.Sidebar_Options.HomeUI;

/**
 * SubGenreUI: Hiển thị các bài hát theo 1 thể loại, lấy dữ liệu từ HomeUI.SONGS
 * Layout giống HomeUI (# | Title | Artist | Time). Gọi setGenre(...) để đổi thể loại.
 */
public class SubGenreUI extends StackPane {

    private final Song.PlayerController controller;
    private ListView<Song> lv;

    // Data được lọc từ HomeUI.SONGS
    private final FilteredList<Song> filtered = new FilteredList<>(HomeUI.SONGS, s -> true);

    // Genre hiện tại (định dạng chuẩn: VPOP, US-UK, RAP, ROCK, EDM, OTHER, ALL)
    private String currentGenre = "ALL";

    // Layout constants (giữ đồng bộ với HomeUI)
    private static final double W_IDX   = 36;
    private static final double W_GAP   = 8;
    private static final double W_TIME  = 64;
    private static final double W_ART   = 260;
    private static final double PAD_ROW = 28;

    // Title control để thay đổi runtime
    private final Text titleText = new Text();

    public SubGenreUI(Song.PlayerController controller, String initialGenre) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(makeTitle());           // tiêu đề động

        Node listNode   = makeListView();   // phải tạo trước để header bind width
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

        // áp dụng genre ban đầu
        setGenre(initialGenre);
    }

    // =================== Title (động theo genre) ===================
    private Node makeTitle() {
        titleText.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");
        // không cần nút + ở đây, giữ title đơn giản
        HBox box = new HBox(8, titleText);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    // =================== Header: # | Title | Artist | Time ===================
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

        // bám theo ListView width để tránh H-scroll
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

    // =================== ListView (dùng filtered list) ===================
    private Node makeListView() {
        lv = new ListView<>(filtered);
        lv.setStyle("-fx-background-color: #000000; -fx-control-inner-background: transparent;");
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

        // CSS scrollbar đồng nhất với HomeUI
        restyleScrollBars();
        lv.skinProperty().addListener((obs, oldSkin, newSkin) -> restyleScrollBars());
        lv.sceneProperty().addListener((obs, oldScene, newScene) -> restyleScrollBars());
        lv.widthProperty().addListener((obs, oldW, newW) -> restyleScrollBars());

        return lv;
    }

    // =================== Row giống HomeUI ===================
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

    // =================== Public API ===================
    public void setGenre(String genre) {
        String normalized = normalizeGenre(genre);
        this.currentGenre = normalized;
        // cập nhật title
        titleText.setText("Genre · " + (normalized.equals("ALL") ? "All" : normalized));

        // cập nhật predicate lọc
        filtered.setPredicate(s -> {
            if (normalized.equals("ALL")) return true;
            String g = s.getGenre();
            return g != null && normalizeGenre(g).equals(normalized);
        });
    }

    public String getGenre() {
        return currentGenre;
    }

    // Chấp nhận nhiều dạng nhập: "usuk", "US-UK", "Us_Uk", ...
    private String normalizeGenre(String g) {
        if (g == null || g.isBlank()) return "ALL";
        String t = g.trim().replace('_', '-');
        if (t.equalsIgnoreCase("USUK") || t.equalsIgnoreCase("US-UK") || t.equalsIgnoreCase("US UK")) return "US-UK";
        if (t.equalsIgnoreCase("VPOP") || t.equalsIgnoreCase("V-POP") || t.equalsIgnoreCase("V POP")) return "VPOP";
        if (t.equalsIgnoreCase("RAP")) return "RAP";
        if (t.equalsIgnoreCase("ROCK")) return "ROCK";
        if (t.equalsIgnoreCase("EDM")) return "EDM";
        if (t.equalsIgnoreCase("OTHER") || t.equalsIgnoreCase("INSTRUMENTAL") || t.equalsIgnoreCase("WORLD")) return "OTHER";
        if (t.equalsIgnoreCase("ALL")) return "ALL";
        // fallback: viết hoa hết
        return t.toUpperCase();
    }

    // =================== Scrollbar CSS (giống HomeUI) ===================
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
