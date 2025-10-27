package Sidebar_Options;

import Default.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/** Home list: # | Title | Artist | Time (không H-scroll, hover số -> ▶, click phát) */
public class HomeUI extends StackPane {

    private final Song.PlayerController controller;
    private ListView<Song> lv;

    // --- layout constants (px) ---
    private static final double W_IDX   = 36;   // #
    private static final double W_COVER = 40;   // cover
    private static final double W_GAP   = 8;    // GridPane hgap
    private static final double W_TIME  = 64;   // mm:ss
    private static final double W_ART   = 260;  // artist column fixed width
    private static final double PAD_ROW = 28;   // row left+right padding (14 + 14)

    public HomeUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(makeTitle("Home"));

        // create list first, then header so header binds to lv's viewport width
        Node listNode   = makeListView();
        Node headerNode = makeHeader();

        // stick both header & list to the width of this view (also prevents H-scroll)
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

    // ===== header/title =====
    private Node makeTitle(String txt) {
        Text t = new Text(txt);
        t.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");
        HBox box = new HBox(t);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    /** table header row: # | Title | Artist | Time (bind theo lv.width) */
    private Node makeHeader() {
        GridPane grid = baseGrid();

        Label h0 = head("#");
        Label h1 = head("Title");
        Label h2 = head("Artist");
        Label h3 = head("Time");

        grid.add(h0, 0, 0);
        grid.add(h1, 2, 0);
        grid.add(h2, 3, 0);
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);
        grid.add(h3, 4, 0);

        StackPane wrap = new StackPane(grid);
        wrap.setPadding(new Insets(6, 10, 6, 10));

        // bám theo viewport ListView để chắc chắn không tràn ngang
        wrap.prefWidthProperty().bind(lv.widthProperty());
        wrap.maxWidthProperty().bind(lv.widthProperty());
        grid.prefWidthProperty().bind(wrap.widthProperty().subtract(20)); // trừ padding L/R

        return wrap;
    }

    private Label head(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;");
        return l;
    }

    // ===== listview =====
    private Node makeListView() {
        ObservableList<Song> items = FXCollections.observableArrayList(
            new Song("ĐÀ LẠT CÒN MƯA KHÔNG EM", "TRO-Music", 291, "Single", "VPOP"),
            new Song("Zikir La Ilaha Illallah", "Hud", 563, "Single", "Other"),
            new Song("Dance (Vibez & Tunes Freestyle)", "Maestro Dj Brown", 172, "Single", "EDM"),
            new Song("Anomimus", "CinBawi", 197, "Single", "Other"),
            new Song("oềồ||bụưng", "CinBawi", 167, "Single", "Other"),
            new Song("ĐÀ LẠT CÒN MƯA KHÔNG EM", "TRO-Music", 291, "Single", "VPOP"),
            new Song("Zikir La Ilaha Illallah", "Hud", 563, "Single", "Other"),
            new Song("Dance (Vibez & Tunes Freestyle)", "Maestro Dj Brown", 172, "Single", "EDM"),
            new Song("Anomimus", "CinBawi", 197, "Single", "Other"),
            new Song("oềồ||bụưng", "CinBawi", 167, "Single", "Other")
        );

        lv = new ListView<>(items);
        lv.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
        lv.setPadding(Insets.EMPTY); // tránh dư px gây H-scroll
        VBox.setVgrow(lv, Priority.ALWAYS);

        lv.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(Song s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setGraphic(null); setText(null); return; }

                Region row = makeRow(getIndex() + 1, s);

                // KHÓA bề ngang row theo viewport ListView để diệt H-scroll
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
            }
        });

        return lv;
    }

    // ===== một hàng =====
    private Region makeRow(int index, Song song) {
        // index / play
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

        // cover
        ImageView cover = new ImageView();
        try {
            cover.setImage(new Image(getClass().getResource(
                "/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg"
            ).toExternalForm()));
        } catch (Exception ignore) {}
        cover.setFitWidth(W_COVER);
        cover.setFitHeight(W_COVER);
        Rectangle clip = new Rectangle(W_COVER, W_COVER);
        clip.setArcWidth(8); clip.setArcHeight(8);
        cover.setClip(clip);

        // title (co giãn, ellipsis)
        Label title = new Label(song.getName());
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        title.setTextOverrun(OverrunStyle.ELLIPSIS);

        // artist (cố định W_ART, ellipsis)
        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        // time (cố định W_TIME)
        Label time = new Label(song.getFormattedDuration());
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        // grid columns
        GridPane grid = baseGrid();
        grid.add(idxCell, 0, 0);
        grid.add(cover,   1, 0);
        grid.add(title,   2, 0);
        grid.add(artist,  3, 0);
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
        grid.add(time,    4, 0);

        // row button wrapper
        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");

        // grid width = row width - row paddings
        grid.prefWidthProperty().bind(row.widthProperty().subtract(PAD_ROW));

        // title width = phần còn lại sau khi trừ các cột cố định + gap + padding
        grid.widthProperty().addListener((o, ov, nv) -> {
            double fixed = W_IDX + W_COVER + W_ART + W_TIME + W_GAP * 4 + PAD_ROW;
            double wTitle = Math.max(80, nv.doubleValue() - fixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        // hover
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

    /** 5 columns: # | cover | title (grow) | artist fixed | time fixed */
    private GridPane baseGrid() {
        GridPane g = new GridPane();
        g.setHgap(W_GAP);
        g.setVgap(0);
        g.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints(W_COVER);

        ColumnConstraints c2 = new ColumnConstraints();      // title grows
        c2.setHgrow(Priority.ALWAYS);

        ColumnConstraints c3 = new ColumnConstraints(W_ART); // artist fixed

        ColumnConstraints c4 = new ColumnConstraints(W_TIME);
        c4.setHalignment(javafx.geometry.HPos.RIGHT);

        g.getColumnConstraints().addAll(c0, c1, c2, c3, c4);
        return g;
    }
}
