package Sidebar_Options;

import Default.Song;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HomeUI extends StackPane {

    private final Song.PlayerController controller;

    public HomeUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color:#010101;");
        root.setTop(header());

        // phần center: header cột + danh sách track
        VBox center = new VBox(8, columnHeader(), trackList());
        center.setFillWidth(true);
        root.setCenter(center);

        getChildren().setAll(root);
    }

    // ---- Title "Home"
    private Node header() {
        Text title = new Text("Home");
        title.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        HBox box = new HBox(title);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 0, 12, 0));
        return box;
    }

    // ---- Hàng tiêu đề cột: # | Title | Artist | Duration (canh theo GridPane)
    private Node columnHeader() {
        GridPane grid = makeGrid();
        grid.setStyle("-fx-background-color: transparent; -fx-background-radius: 10;");

        // # (để trống phần cover)
        Label colIdx = labelHeader("#");
        grid.add(colIdx, 0, 0);

        // chừa 1 cột ảnh bìa
        Label colTitle = labelHeader("Title");
        grid.add(colTitle, 2, 0);

        Label colArtist = labelHeader("Artist");
        grid.add(colArtist, 3, 0);

        Label colDuration = labelHeader("Duration");
        GridPane.setHalignment(colDuration, javafx.geometry.HPos.RIGHT);
        grid.add(colDuration, 4, 0);

        // bọc vào Pane để bo góc/đệm
        StackPane wrap = new StackPane(grid);
        wrap.setPadding(new Insets(6, 10, 6, 10));
        return wrap;
    }

    private Label labelHeader(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #9aa9b8; -fx-font-size: 12px; -fx-font-weight: 700;");
        return l;
    }

    // ---- Danh sách bài hát (full width, cuộn được)
    private Node trackList() {
        VBox list = new VBox(8);
        list.setFillWidth(true);

        // demo data
        Song[] demo = new Song[] {
            new Song("Zikir La Ilaha Illallah", "Hud", 563, "Single", "Other"),
            new Song("ĐÀ LẠT CÒN MƯA KHÔNG EM", "TRO-Music", 291, "Single", "VPOP"),
            new Song("Dance (Vibez & Tunes Freestyle)", "Maestro Dj Brown", 172, "Single", "EDM"),
            new Song("Anomimus", "CinBawi", 197, "Single", "Other"),
            new Song("oềồ||bụưng", "CinBawi", 167, "Single", "Other"),
            new Song("Nhật ký tình đầu (No Rap Version)", "TimelessRemixes", 245, "Single", "VPOP")
        };

        for (int i = 0; i < demo.length; i++) {
            list.getChildren().add(makeTrackRow(i + 1, demo[i]));
        }

        ScrollPane sp = new ScrollPane(list);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPannable(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        return sp;
    }

    // ---- Một hàng: #/play | cover | Title | Artist | Duration (GridPane để canh cột)
    private Region makeTrackRow(int index, Song song) {
        // # / play
        Label idx = new Label(String.valueOf(index));
        idx.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 14px; -fx-font-weight: 700;");

        Button play = new Button("▶");
        play.setFocusTraversable(false);
        play.setBackground(Background.EMPTY);
        play.setBorder(Border.EMPTY);
        play.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        play.setVisible(false);
        play.setOnAction(e -> controller.play(song));

        StackPane indexCell = new StackPane(idx, play);

        // cover
        ImageView cover = new ImageView();
        try {
            cover.setImage(new Image(getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm()));
        } catch (Exception ignore) {}
        cover.setFitWidth(40);
        cover.setFitHeight(40);
        Rectangle clip = new Rectangle(40, 40);
        clip.setArcWidth(8); clip.setArcHeight(8);
        cover.setClip(clip);

        // title, artist, duration
        Label title = new Label(song.getName());
        title.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: 800;");

        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill: #B3B3B3; -fx-font-size: 13px; -fx-font-weight: 600;");

        Label duration = new Label(song.getFormattedDuration());
        duration.setStyle("-fx-text-fill: #C9D1D9; -fx-font-size: 13px; -fx-font-weight: 700;");

        // Grid canh cột
        GridPane grid = makeGrid();
        grid.add(indexCell, 0, 0);
        grid.add(cover,     1, 0);
        grid.add(title,     2, 0);
        grid.add(artist,    3, 0);
        GridPane.setHalignment(duration, javafx.geometry.HPos.RIGHT);
        grid.add(duration,  4, 0);

        // Cả hàng là 1 Button phẳng để hover/click toàn hàng
        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setMaxWidth(Double.MAX_VALUE);
        row.setPrefHeight(64);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1F242B; -fx-background-radius: 12; -fx-padding: 10 14 10 14;");
        row.setOnAction(e -> controller.play(song));

        // Hover: đổi nền + hiện ▶ thay số
        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color: #2A3038; -fx-background-radius: 12; -fx-padding: 10 14 10 14;");
            idx.setVisible(false);
            play.setVisible(true);
        });
        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color: #1F242B; -fx-background-radius: 12; -fx-padding: 10 14 10 14;");
            play.setVisible(false);
            idx.setVisible(true);
        });

        // bọc để fill ngang theo MainDisplay
        HBox wrapper = new HBox(row);
        row.maxWidthProperty().bind(wrapper.widthProperty());
        return wrapper;
    }

    // ---- Grid mẫu cho cả header & row: 5 cột
    // col0: idx (36px) | col1: cover (40px) | col2: Title (55%) | col3: Artist (35%) | col4: Duration (80px)
    private GridPane makeGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(0);
        grid.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(36);  // #
        c0.setHalignment(javafx.geometry.HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints(40);  // cover

        ColumnConstraints c2 = new ColumnConstraints();    // Title 55%
        c2.setPercentWidth(55);
        c2.setHgrow(Priority.ALWAYS);

        ColumnConstraints c3 = new ColumnConstraints();    // Artist 35%
        c3.setPercentWidth(35);
        c3.setHgrow(Priority.ALWAYS);

        ColumnConstraints c4 = new ColumnConstraints(80);  // Duration
        c4.setHalignment(javafx.geometry.HPos.RIGHT);

        grid.getColumnConstraints().addAll(c0, c1, c2, c3, c4);
        return grid;
    }
}
