package Sidebar_Options;

import Default.Song;
import javafx.beans.binding.Bindings;
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

public class PlaylistUI extends StackPane {

    // ======= "Kho" playlist dùng chung (KHÔNG cần class mới) =======
    private static final ObservableList<Song> DATA = FXCollections.observableArrayList();

    // Cho nơi khác thêm / xóa / lấy list
    public static void add(Song s) {
        if (s != null && !DATA.contains(s)) DATA.add(s);
    }

    public static void remove(Song s) {
        DATA.remove(s);
    }

    public static ObservableList<Song> items() {
        return DATA;
    }

    // ======= UI =======
    private final Song.PlayerController controller;
    private ListView<Song> lv;

    // Kích thước cột (đồng bộ với HomeUI)
    private static final double W_IDX   = 36;
    private static final double W_COVER = 40;
    private static final double W_GAP   = 8;
    private static final double W_TIME  = 64;
    private static final double W_ART   = 280;
    private static final double PAD_LR  = 28;

    public PlaylistUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setPadding(new Insets(12));

        // Title
        Text title = new Text("My Playlists");
        title.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;");

        // Empty label
        Label empty = new Label("Your playlist is empty. Please add some songs");
        empty.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:14px;");

        // ListView bind trực tiếp vào DATA
        lv = new ListView<>(DATA);
        lv.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
        VBox.setVgrow(lv, Priority.ALWAYS);

        // Cell giống HomeUI
        lv.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Song s, boolean emptyCell) {
                super.updateItem(s, emptyCell);
                if (emptyCell || s == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                Region row = makeRow(getIndex() + 1, s);

                // khóa bề ngang theo viewport ListView → loại bỏ scroll ngang
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));

                setGraphic(row);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(64);
                setBackground(Background.EMPTY);
            }
        });

        // Header cột: tạo SAU khi có lv để bind width theo lv
        Node header = makeHeader();

        // Hiển thị label rỗng / list
        empty.visibleProperty().bind(Bindings.isEmpty(DATA));
        lv.visibleProperty().bind(empty.visibleProperty().not());

        // Layout
        VBox root = new VBox(12, title, header, empty, lv);
        root.setFillWidth(true);
        getChildren().setAll(root);
    }

    // ===== Header cột: # | Title | Artist | Time (bám theo width của ListView) =====
    private Node makeHeader() {
        GridPane grid = new GridPane();
        grid.setHgap(W_GAP);
        grid.setAlignment(Pos.CENTER_LEFT);

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);
        ColumnConstraints c1 = new ColumnConstraints(W_COVER);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        ColumnConstraints c3 = new ColumnConstraints(W_ART);
        ColumnConstraints c4 = new ColumnConstraints(W_TIME);
        c4.setHalignment(javafx.geometry.HPos.RIGHT);
        grid.getColumnConstraints().addAll(c0, c1, c2, c3, c4);

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

        // bám đúng bề ngang viewport của ListView -> không phát sinh scroll ngang
        wrap.prefWidthProperty().bind(lv.widthProperty());
        wrap.maxWidthProperty().bind(lv.widthProperty());
        grid.prefWidthProperty().bind(lv.widthProperty().subtract(20)); // trừ padding L+R của wrap

        return wrap;
    }

    private Label head(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;");
        return l;
    }

    // ===== Row giống HomeUI + menu chuột phải Remove =====
    private Region makeRow(int index, Song song) {
        // idx / ▶
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
            cover.setImage(new Image(
                    getClass().getResource("/image/9e0f8784ffebf6865c83c5e526274f31_1465465806.jpg").toExternalForm()
            ));
        } catch (Exception ignore) {}
        cover.setFitWidth(W_COVER);
        cover.setFitHeight(W_COVER);
        Rectangle clip = new Rectangle(W_COVER, W_COVER);
        clip.setArcWidth(8);
        clip.setArcHeight(8);
        cover.setClip(clip);

        // meta
        Label title = new Label(song.getName());
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        title.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        Label time = new Label(song.getFormattedDuration());
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        // Grid 5 cột
        GridPane grid = new GridPane();
        grid.setHgap(W_GAP);
        grid.setAlignment(Pos.CENTER_LEFT);
        ColumnConstraints c0 = new ColumnConstraints(W_IDX);
        c0.setHalignment(javafx.geometry.HPos.CENTER);
        ColumnConstraints c1 = new ColumnConstraints(W_COVER);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        ColumnConstraints c3 = new ColumnConstraints(W_ART);
        ColumnConstraints c4 = new ColumnConstraints(W_TIME);
        c4.setHalignment(javafx.geometry.HPos.RIGHT);
        grid.getColumnConstraints().addAll(c0, c1, c2, c3, c4);

        grid.add(idxCell, 0, 0);
        grid.add(cover, 1, 0);
        grid.add(title, 2, 0);
        grid.add(artist, 3, 0);
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
        grid.add(time, 4, 0);

        // Tính bề rộng Title theo grid.width
        grid.widthProperty().addListener((o, ov, nv) -> {
            double totalFixed = W_IDX + W_COVER + W_ART + W_TIME + W_GAP * 4 + PAD_LR;
            double wTitle = Math.max(80, nv.doubleValue() - totalFixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");

        // Bind để không tràn ngang
        grid.prefWidthProperty().bind(row.widthProperty().subtract(28));
        row.setOnAction(e -> controller.play(song));

        // Hover đổi ▶
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

        // (Optional) menu chuột phải: Remove
        row.setOnContextMenuRequested(ev -> {
            ContextMenu cm = new ContextMenu();
            MenuItem rm = new MenuItem("🗑 Remove from playlist");
            rm.setOnAction(ae -> {
                // đánh dấu bài này không còn trong playlist nữa
                song.setPlaylist(false);          // <<< thêm đúng 1 dòng này
                // xóa khỏi danh sách hiển thị
                remove(song);
                cm.hide();
            });
            cm.getItems().add(rm);
            cm.setAutoHide(true);
            cm.show(row, ev.getScreenX(), ev.getScreenY());
        });
        return row;
    }
}
