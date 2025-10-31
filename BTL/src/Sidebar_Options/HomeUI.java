package Sidebar_Options;

import Default.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.OverrunStyle;
// [REMOVED IMG] bỏ import Image/ImageView/Rectangle vì không dùng ảnh nữa
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
// [NEW] Dùng để hoãn styling scrollbar tới khi skin đã sẵn sàng
import javafx.application.Platform;

/** [OLD] Home list: # | Title | Artist | Time (không H-scroll, hover số -> ▶, click phát) */
public class HomeUI extends StackPane {

    private final Song.PlayerController controller;
    private ListView<Song> lv;

    // --- layout constants (px) ---
    private static final double W_IDX   = 36;   // [OLD] cột số thứ tự (#)
    // private static final double W_COVER = 40;   // [OLD] kích thước ảnh cover
    private static final double W_GAP   = 8;    // [OLD] khoảng cách cột trong GridPane
    private static final double W_TIME  = 64;   // [OLD] độ rộng cột thời lượng (mm:ss)
    private static final double W_ART   = 260;  // [OLD] độ rộng cố định cột Artist
    private static final double PAD_ROW = 28;   // [OLD] padding trái + phải của mỗi row (14 + 14)

    public HomeUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;"); // [OLD] nền tổng thể
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // [OLD] chiếm toàn bộ

        BorderPane root = new BorderPane(); // [OLD]
        root.setPadding(new Insets(12));    // [OLD]
        root.setStyle("-fx-background-color:#010101;"); // [OLD]
        root.setTop(makeTitle("Home"));     // [OLD] tiêu đề (+ nút thêm nhạc) [NEW]

        // [OLD] tạo list trước để header bind theo viewport của lv
        Node listNode   = makeListView();   // [OLD]
        Node headerNode = makeHeader();     // [OLD]

        // [OLD] khóa chiều ngang header & list để tránh H-scroll
        if (listNode instanceof Region r1) {
            r1.prefWidthProperty().bind(widthProperty());
            r1.maxWidthProperty().bind(widthProperty());
        }
        if (headerNode instanceof Region r2) {
            r2.prefWidthProperty().bind(widthProperty());
            r2.maxWidthProperty().bind(widthProperty());
        }

        VBox center = new VBox(8, headerNode, listNode); // [OLD]
        center.setFillWidth(true);                       // [OLD]
        VBox.setVgrow(listNode, Priority.ALWAYS);        // [OLD]
        root.setCenter(center);                          // [OLD]

        getChildren().setAll(root);                      // [OLD]
    }

    // ===== header/title =====
    private Node makeTitle(String txt) {
        Text t = new Text(txt); // [OLD]
        t.setStyle("-fx-fill:white; -fx-font-size:22px; -fx-font-weight:800;"); // [OLD]

        // [NEW] Nút "+" (không gán sự kiện)
        Button addBtn = new Button("+");
        addBtn.setFocusTraversable(false);
        addBtn.setStyle(
            "-fx-background-color:#22C55E;" +   // xanh tươi
            "-fx-text-fill:#03120A;" +
            "-fx-font-size:20px;" +
            "-fx-font-weight:900;" +
            "-fx-background-radius:10;" +
            "-fx-padding:2 16 2 16;" +
            "-fx-cursor: hand;"
        );

        HBox box = new HBox(8, t, addBtn); // [NEW] đặt nút cạnh tiêu đề
        box.setAlignment(Pos.CENTER_LEFT); // [OLD]
        box.setPadding(new Insets(0, 0, 12, 0)); // [OLD] khoảng cách dưới tiêu đề
        return box; // [OLD]
    }

    /** [OLD] table header row: # | Title | Artist | Time (bind theo lv.width) */
    private Node makeHeader() {
        GridPane grid = baseGrid(); // [OLD]

        Label h0 = head("#");       // [OLD]
        Label h1 = head("Title");   // [OLD]
        Label h2 = head("Artist");  // [OLD]
        Label h3 = head("Time");    // [OLD]

        grid.add(h0, 0, 0); // [OLD]
        // [REMOVED IMG] bỏ cột cover => Title dời về cột 1 (trước đây là 2)
        grid.add(h1, 1, 0); // [NEW]
        grid.add(h2, 2, 0); // [OLD] (dịch sang trái 1 cột)
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT); // [OLD]
        grid.add(h3, 3, 0); // [OLD] (dịch sang trái 1 cột)

        StackPane wrap = new StackPane(grid); // [OLD]
        wrap.setPadding(new Insets(6, 10, 6, 10)); // [OLD]

        // [OLD] wrap bám theo lv để chắc chắn không tràn ngang
        wrap.prefWidthProperty().bind(lv.widthProperty());
        wrap.maxWidthProperty().bind(lv.widthProperty());
        grid.prefWidthProperty().bind(wrap.widthProperty().subtract(20)); // [OLD] trừ padding L/R

        return wrap; // [OLD]
    }

    private Label head(String s) {
        Label l = new Label(s); // [OLD]
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; -fx-font-weight:700;"); // [OLD]
        return l; // [OLD]
    }

    // ===== listview =====
    private Node makeListView() {
        // [OLD] dữ liệu demo
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

        lv = new ListView<>(items); // [OLD] khởi tạo ListView
        lv.setStyle("-fx-background-color: #000000; -fx-control-inner-background: transparent;"); // [OLD] nền
        lv.setPadding(Insets.EMPTY); // [OLD] tránh dư px gây H-scroll
        VBox.setVgrow(lv, Priority.ALWAYS); // [OLD] cho phép ListView giãn đầy vùng center

        // [OLD] cell factory & layout của từng hàng
        lv.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(Song s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setGraphic(null); setText(null); return; }

                Region row = makeRow(getIndex() + 1, s); // [OLD]

                // [OLD] khóa bề ngang theo viewport ListView để tránh H-scroll
                row.prefWidthProperty().bind(lv.widthProperty().subtract(2));
                row.maxWidthProperty().bind(lv.widthProperty().subtract(2));

                setGraphic(row); // [OLD]
                setText(null);   // [OLD]
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // [OLD]
                setPrefHeight(64); // [OLD]
                setBackground(Background.EMPTY); // [OLD]
            }
        });

        // [FIX] Áp CSS cho ScrollBar và tự re-apply khi skin/scene/width thay đổi
        restyleScrollBars(); // [ADD] gọi lần đầu sau khi ListView có children
        lv.skinProperty().addListener((obs, oldSkin, newSkin) -> restyleScrollBars()); // [ADD] skin có thể tái tạo
        lv.sceneProperty().addListener((obs, oldScene, newScene) -> restyleScrollBars()); // [ADD] khi attach/detach scene
        lv.widthProperty().addListener((obs, oldW, newW) -> restyleScrollBars()); // [ADD] đảm bảo ẩn H-scroll khi đổi kích thước

        return lv; // [OLD]
    }

    // ===== một hàng =====
    private Region makeRow(int index, Song song) {
        // [OLD] ô số thứ tự/Play
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

        // [REMOVED IMG] BỎ ẢNH COVER
        // ImageView cover = new ImageView();
        // ... (đoạn thiết lập ảnh và clip bo góc) ...

        // [OLD] Title co giãn + ellipsis
        Label title = new Label(song.getName());
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; -fx-font-weight:800;");
        title.setTextOverrun(OverrunStyle.ELLIPSIS);

        // [OLD] Artist cố định W_ART + ellipsis
        Label artist = new Label(song.getArtist());
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; -fx-font-weight:600;");
        artist.setMaxWidth(W_ART);
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);

        // [OLD] Time cố định W_TIME
        Label time = new Label(song.getFormattedDuration());
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; -fx-font-weight:700;");

        // [NEW] Lưới 4 cột (đã bỏ cover): # | title (grow) | artist fixed | time fixed
        GridPane grid = baseGrid(); // [OLD] (định nghĩa lại bên dưới đã bỏ cột cover)
        grid.add(idxCell, 0, 0);
        grid.add(title,   1, 0); // [NEW] title dời về cột 1
        grid.add(artist,  2, 0);
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);
        grid.add(time,    3, 0);

        // [OLD] Button bọc row (để hover/click)
        Button row = new Button();
        row.setGraphic(grid);
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#1F242B; -fx-background-radius:12; -fx-padding:10 14 10 14;");

        // [OLD] Grid bám theo chiều rộng trừ padding
        grid.prefWidthProperty().bind(row.widthProperty().subtract(PAD_ROW));

        // [NEW] Title = phần còn lại sau khi trừ các cột cố định + gap + padding (không còn cover)
        grid.widthProperty().addListener((o, ov, nv) -> {
            // [OLD] trước đây tính với W_COVER và 5 cột => W_GAP * 4
            double fixed = W_IDX + /* W_COVER + */ W_ART + W_TIME + W_GAP * 3 + PAD_ROW; // [NEW]
            double wTitle = Math.max(80, nv.doubleValue() - fixed);
            title.setMaxWidth(wTitle);
            title.setPrefWidth(wTitle);
        });

        // [OLD] Hover: đổi nền, hiện nút ▶
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

        return row; // [OLD]
    }

    /** [NEW] 4 columns (đã bỏ cover): # | title (grow) | artist fixed | time fixed */
    private GridPane baseGrid() {
        GridPane g = new GridPane(); // [OLD]
        g.setHgap(W_GAP);            // [OLD]
        g.setVgap(0);                // [OLD]
        g.setAlignment(Pos.CENTER_LEFT); // [OLD]

        ColumnConstraints c0 = new ColumnConstraints(W_IDX); // [OLD] sao khi t click lại thì bị mất css thanh croll 
        c0.setHalignment(javafx.geometry.HPos.CENTER);       // [OLD]

        // [REMOVED IMG] BỎ cột cover: không còn ColumnConstraints cho cover
        // ColumnConstraints c1 = new ColumnConstraints(W_COVER); // [OLD]

        ColumnConstraints c1 = new ColumnConstraints();      // [NEW] title grows (cột 1)
        c1.setHgrow(Priority.ALWAYS);                        // [OLD]

        ColumnConstraints c2 = new ColumnConstraints(W_ART); // [OLD] artist fixed (cột 2)

        ColumnConstraints c3 = new ColumnConstraints(W_TIME); // [OLD] time fixed (cột 3)
        c3.setHalignment(javafx.geometry.HPos.RIGHT);         // [OLD]

        // [NEW] add 4 cột thay vì 5
        g.getColumnConstraints().addAll(c0, c1, c2, c3);  // [NEW]
        return g; // [OLD]
    }

    // ===================== [FIX] Re-apply CSS cho ScrollBar khi skin/scene thay đổi =====================
    private void restyleScrollBars() {
        if (lv == null) return; // [ADD] an toàn khi ListView chưa sẵn sàng

        Platform.runLater(() -> { // [ADD] đợi skin/render xong rồi mới lookup nodes nội bộ
            // 1) ẨN HOÀN TOÀN THANH NGANG
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {
                sb.setStyle(
                    "-fx-opacity: 0;" +
                    "-fx-pref-height: 0;" +
                    "-fx-max-height: 0;" +
                    "-fx-padding: 0;"
                );
                sb.setDisable(true);    // [ADD] vô hiệu tương tác
                sb.setManaged(false);   // [ADD] loại khỏi layout pass
                sb.setVisible(false);   // [ADD] ẩn triệt để
                sb.setPickOnBounds(false); // [ADD] tránh bắt sự kiện
            }

            // 2) THANH DỌC: mỏng, track trong suốt, thumb xanh, bo viên thuốc
            for (Node sb : lv.lookupAll(".scroll-bar:vertical")) {
                // [ADD] khung của ScrollBar dọc
                sb.setStyle(
                    "-fx-pref-width: 8;" +
                    "-fx-max-width: 8;" +
                    "-fx-background-color: transparent;" +
                    "-fx-background-insets: 0;" +
                    "-fx-padding: 4 2 4 2;"
                );

                // 2.1) Ẩn 2 nút mũi tên (nếu có)
                Node inc = ((Region) sb).lookup(".increment-button");
                if (inc != null) {
                    inc.setStyle(
                        "-fx-opacity:0; -fx-padding:0;" +
                        "-fx-pref-width:0; -fx-pref-height:0;" +
                        "-fx-max-width:0; -fx-max-height:0;"
                    );
                    inc.setManaged(false); // [ADD]
                }
                Node dec = ((Region) sb).lookup(".decrement-button");
                if (dec != null) {
                    dec.setStyle(
                        "-fx-opacity:0; -fx-padding:0;" +
                        "-fx-pref-width:0; -fx-pref-height:0;" +
                        "-fx-max-width:0; -fx-max-height:0;"
                    );
                    dec.setManaged(false); // [ADD]
                }

                // 2.2) TRACK (đường ray) trong suốt + bo tròn
                Node track = ((Region) sb).lookup(".track");
                if (track != null) {
                    track.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-background-insets: 0;" +
                        "-fx-background-radius: 100;"
                    );
                }

                // 2.3) THUMB (nút trượt) xanh + bo tròn
                Node thumb = ((Region) sb).lookup(".thumb");
                if (thumb != null) {
                    thumb.setStyle(
                        "-fx-background-color: #22C55E;" + // xanh tươi
                        "-fx-background-insets: 0;" +
                        "-fx-background-radius: 100;" +
                        "-fx-padding: 0;"
                    );
                }
            }
        });
    }
}
