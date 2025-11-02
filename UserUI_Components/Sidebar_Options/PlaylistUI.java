package UserUI_Components.Sidebar_Options;                                              // [OLD] package chứa UI trong sidebar

import Default.Song;                                                 // [OLD] model bài hát

// ===== [ADD] imports để làm y hệt HomeUI (debounce + re-apply CSS) =====
import javafx.animation.PauseTransition;                             // [ADD] dùng để debounce việc áp CSS
import javafx.application.Platform;                                  // [ADD] hoãn styling tới khi skin/children đã sẵn sàng
import javafx.beans.binding.Bindings;                                // [OLD] binding hiển thị/ẩn
import javafx.collections.FXCollections;                             // [OLD] tạo ObservableList
import javafx.collections.ObservableList;                            // [OLD] danh sách quan sát được
import javafx.geometry.Insets;                                       // [OLD] padding
import javafx.geometry.Pos;                                          // [OLD] căn lề
import javafx.scene.Node;                                            // [OLD] nút cơ sở trong scene graph
import javafx.scene.control.*;                                       // [OLD] ListView, Label, Button, ContextMenu...
import javafx.scene.control.OverrunStyle;                            // [OLD] ellipsis cho text dài
import javafx.scene.input.KeyEvent;                                  // [ADD] lắng nghe phím để re-apply CSS
import javafx.scene.input.MouseEvent;                                // [ADD] lắng nghe chuột để re-apply CSS
import javafx.scene.input.ScrollEvent;                               // [ADD] lắng nghe cuộn để re-apply CSS
import javafx.scene.layout.*;                                        // [OLD] VBox, GridPane, StackPane, ...
import javafx.scene.text.Text;                                       // [OLD] tiêu đề
import javafx.util.Duration;                                         // [ADD] thời lượng cho PauseTransition

/**
 * PlaylistUI — scroll bar Y HỆT HomeUI:
 *  - Ẩn scrollbar ngang hoàn toàn
 *  - Scrollbar dọc mỏng, track trong suốt, thumb xanh lá, bo viên thuốc
 *  - Tự re-apply CSS khi skin/scene/resize/interaction thay đổi (tránh "mất CSS" sau khi click/scroll)
 */
public class PlaylistUI extends StackPane {                           // [OLD] root node là StackPane

    // ======= "Kho" playlist dùng chung (dùng lại giữa các nơi) =======
    private static final ObservableList<Song> DATA =                  // [OLD] danh sách bài hát dùng chung
            FXCollections.observableArrayList();                      // [OLD] tạo list rỗng
    public static void add(Song s) {                                  // [OLD] thêm bài nếu chưa có
        if (s != null && !DATA.contains(s)) DATA.add(s);              // [OLD] tránh null/duplicate
    }
    public static void remove(Song s) { DATA.remove(s); }             // [OLD] xóa bài khỏi playlist
    public static ObservableList<Song> items() { return DATA; }       // [OLD] expose list (read-only view bên ngoài)

    // ======= UI state =======
    private final Song.PlayerController controller;                   // [OLD] controller phát nhạc (play/toggle...)
    private ListView<Song> lv;                                        // [OLD] list hiển thị bài hát

    // ======= layout constants (đồng bộ HomeUI không cover) =======
    private static final double W_IDX  = 36;                          // [OLD] cột chỉ số #
    private static final double W_GAP  = 8;                           // [OLD] khoảng cách cột
    private static final double W_TIME = 64;                          // [OLD] độ rộng cột thời lượng
    private static final double W_ART  = 280;                         // [OLD] độ rộng cột nghệ sĩ
    private static final double PAD_LR = 28;                          // [OLD] padding trái+phải của 1 row (14+14)

    // ===== [ADD] Debounce: gom nhiều sự kiện → áp CSS 1 lần, mượt hơn =====
    private final PauseTransition sbDebounce =                        // [ADD] tạo PauseTransition
            new PauseTransition(Duration.millis(50));                 // [ADD] 50ms là đủ mượt, tránh áp quá dày

    // ===== Constructor =====
    public PlaylistUI(Song.PlayerController controller) {             // [OLD] truyền controller vào UI
        this.controller = controller;                                 // [OLD] lưu lại để dùng khi click play

        setStyle("-fx-background-color:#010101;");                    // [OLD] nền tổng thể (đen)
        setPadding(new Insets(12));                                   // [OLD] padding xung quanh

        // ===== Title =====
        Text title = new Text("My Playlists");                        // [OLD] tiêu đề trang
        title.setStyle("-fx-fill:white; -fx-font-size:22px; " +       // [OLD] màu + cỡ
                       "-fx-font-weight:800;");                       // [OLD] đậm

        // ===== Empty label =====
        Label empty = new Label("Your playlist is empty. Please add some songs"); // [OLD] thông báo rỗng
        empty.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:14px;");            // [OLD] style nhạt hơn

        // ===== ListView bind trực tiếp vào DATA =====
        lv = new ListView<>(DATA);                                    // [OLD] hiển thị danh sách chung
        lv.setStyle("-fx-background-color: transparent; " +           // [OLD] nền trong suốt
                    "-fx-control-inner-background: transparent;");    // [OLD] vùng trong ListView cũng trong
        VBox.setVgrow(lv, Priority.ALWAYS);                           // [OLD] ListView chiếm phần còn lại theo chiều dọc

        // ===== CellFactory — layout 4 cột (#, Title grow, Artist fixed, Time fixed) =====
        lv.setCellFactory(list -> new ListCell<>() {                  // [OLD] render mỗi hàng
            @Override
            protected void updateItem(Song s, boolean emptyCell) {    // [OLD] lifecycle cell
                super.updateItem(s, emptyCell);                       // [OLD] gọi super
                if (emptyCell || s == null) {                         // [OLD] nếu rỗng
                    setGraphic(null);                                 // [OLD] xóa graphic
                    setText(null);                                    // [OLD] xóa text
                    return;                                           // [OLD] kết thúc
                }

                Region row = makeRow(getIndex() + 1, s);              // [OLD] build 1 hàng UI

                row.prefWidthProperty()
                   .bind(lv.widthProperty().subtract(2));             // [OLD] khóa bề ngang theo viewport ListView
                row.maxWidthProperty()
                   .bind(lv.widthProperty().subtract(2));             // [OLD] tránh phát sinh scroll ngang

                setGraphic(row);                                      // [OLD] gán graphic
                setText(null);                                        // [OLD] không dùng text
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);       // [OLD] chỉ hiển thị graphic
                setPrefHeight(64);                                    // [OLD] chiều cao hàng
                setBackground(Background.EMPTY);                      // [OLD] bỏ nền mặc định
            }
        });

        // ===== Header cột =====
        Node header = makeHeader();                                   // [OLD] tiêu đề cột

        // ===== Empty vs List toggle =====
        empty.visibleProperty().bind(Bindings.isEmpty(DATA));         // [OLD] label rỗng hiện khi DATA rỗng
        lv.visibleProperty().bind(empty.visibleProperty().not());     // [OLD] ListView hiện khi có dữ liệu

        // ===== Scrollbar CSS y hệt HomeUI + re-apply an toàn =====
        restyleScrollBars();                                          // [ADD] áp CSS ngay lần đầu
        lv.skinProperty().addListener((o, os, ns) ->                  // [ADD] skin tái tạo → áp lại CSS
                scheduleRestyleScrollBars());
        lv.sceneProperty().addListener((o, os, ns) ->                 // [ADD] gắn/bỏ scene → áp lại CSS
                scheduleRestyleScrollBars());
        lv.widthProperty().addListener((o, oldW, newW) ->             // [ADD] đổi kích thước → áp lại CSS
                scheduleRestyleScrollBars());
        lv.focusedProperty().addListener((o, ov, nv) ->               // [ADD] focus thay đổi → áp lại CSS
                scheduleRestyleScrollBars());
        lv.hoverProperty().addListener((o, ov, nv) ->                 // [ADD] hover thay đổi → áp lại CSS
                scheduleRestyleScrollBars());
        lv.addEventFilter(MouseEvent.ANY,  e ->                       // [ADD] mọi sự kiện chuột → áp lại CSS
                scheduleRestyleScrollBars());
        lv.addEventFilter(KeyEvent.ANY,    e ->                       // [ADD] mọi sự kiện phím → áp lại CSS
                scheduleRestyleScrollBars());
        lv.addEventFilter(ScrollEvent.ANY, e ->                       // [ADD] mọi sự kiện cuộn → áp lại CSS
                scheduleRestyleScrollBars());
        lv.getItems().addListener((javafx.collections.                // [ADD] dữ liệu thay đổi → áp lại CSS
                ListChangeListener<? super Song>) c ->
                scheduleRestyleScrollBars());

        // ===== Layout tổng =====
        VBox root = new VBox(12, title, header, empty, lv);           // [OLD] sắp xếp theo cột
        root.setFillWidth(true);                                      // [OLD] con giãn hết bề ngang
        getChildren().setAll(root);                                   // [OLD] đặt vào StackPane
    }

    // ===== Header cột: # | Title | Artist | Time =====
    private Node makeHeader() {                                       // [OLD] xây header
        GridPane grid = new GridPane();                               // [OLD] lưới cột
        grid.setHgap(W_GAP);                                          // [OLD] khoảng cách cột
        grid.setAlignment(Pos.CENTER_LEFT);                           // [OLD] căn trái

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);          // [OLD] cột #
        c0.setHalignment(javafx.geometry.HPos.CENTER);                // [OLD] giữa
        ColumnConstraints c1 = new ColumnConstraints();               // [OLD] cột Title (grow)
        c1.setHgrow(Priority.ALWAYS);                                 // [OLD] cho phép giãn
        ColumnConstraints c2 = new ColumnConstraints(W_ART);          // [OLD] cột Artist (fixed)
        ColumnConstraints c3 = new ColumnConstraints(W_TIME);         // [OLD] cột Time (fixed)
        c3.setHalignment(javafx.geometry.HPos.RIGHT);                 // [OLD] canh phải
        grid.getColumnConstraints().addAll(c0, c1, c2, c3);           // [OLD] áp constraints

        Label h0 = head("#");                                         // [OLD] label cột
        Label h1 = head("Title");                                     // [OLD]
        Label h2 = head("Artist");                                    // [OLD]
        Label h3 = head("Time");                                      // [OLD]

        grid.add(h0, 0, 0);                                           // [OLD] thêm vào lưới
        grid.add(h1, 1, 0);                                           // [OLD]
        grid.add(h2, 2, 0);                                           // [OLD]
        GridPane.setHalignment(h3, javafx.geometry.HPos.RIGHT);       // [OLD] canh phải cho Time
        grid.add(h3, 3, 0);                                           // [OLD]

        StackPane wrap = new StackPane(grid);                         // [OLD] thêm padding ngoài
        wrap.setPadding(new Insets(6, 10, 6, 10));                    // [OLD] padding L/R = 10

        wrap.prefWidthProperty().bind(lv.widthProperty());            // [OLD] bám theo bề ngang lv
        wrap.maxWidthProperty().bind(lv.widthProperty());             // [OLD] tránh tràn
        grid.prefWidthProperty().bind(lv.widthProperty()              // [OLD] trừ padding 2 bên
                .subtract(20));

        return wrap;                                                  // [OLD]
    }

    private Label head(String s) {                                    // [OLD] tạo label header
        Label l = new Label(s);                                       // [OLD]
        l.setStyle("-fx-text-fill:#9aa9b8; -fx-font-size:12px; " +    // [OLD] style chữ
                   "-fx-font-weight:700;");                           // [OLD] đậm
        return l;                                                     // [OLD]
    }

    // ===== Tạo 1 hàng: # | Title (grow) | Artist (fixed) | Time (fixed) =====
    private Region makeRow(int index, Song song) {                    // [OLD] xây UI cho 1 bài
        Label idx = new Label(String.valueOf(index));                 // [OLD] số thứ tự
        idx.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:14px; " +  // [OLD] màu chữ
                     "-fx-font-weight:700;");                         // [OLD] đậm

        Button play = new Button("▶");                                // [OLD] nút play overlay
        play.setBackground(Background.EMPTY);                         // [OLD] bỏ nền
        play.setBorder(Border.EMPTY);                                 // [OLD] bỏ viền
        play.setStyle("-fx-text-fill:white; -fx-font-size:14px; " +   // [OLD] màu/trộn
                      "-fx-cursor:hand;");                            // [OLD] cursor tay
        play.setVisible(false);                                       // [OLD] mặc định ẩn
        play.setOnAction(e -> controller.play(song));                 // [OLD] click → play bài

        StackPane idxCell = new StackPane(idx, play);                 // [OLD] chồng idx và nút play
        StackPane.setAlignment(idx, Pos.CENTER);                      // [OLD] căn giữa
        StackPane.setAlignment(play, Pos.CENTER);                     // [OLD] căn giữa

        Label title = new Label(song.getName());                      // [OLD] tên bài
        title.setStyle("-fx-text-fill:white; -fx-font-size:15px; " +  // [OLD] style
                       "-fx-font-weight:800;");                       // [OLD] đậm
        title.setTextOverrun(OverrunStyle.ELLIPSIS);                  // [OLD] ... nếu dài

        Label artist = new Label(song.getArtist());                   // [OLD] nghệ sĩ
        artist.setStyle("-fx-text-fill:#B3B3B3; -fx-font-size:13px; " +// [OLD] style nhạt
                        "-fx-font-weight:600;");                      // [OLD]
        artist.setMaxWidth(W_ART);                                    // [OLD] khóa độ rộng
        artist.setTextOverrun(OverrunStyle.ELLIPSIS);                 // [OLD] ... nếu dài

        Label time = new Label(song.getFormattedDuration());          // [OLD] thời lượng mm:ss
        time.setStyle("-fx-text-fill:#C9D1D9; -fx-font-size:13px; " + // [OLD]
                      "-fx-font-weight:700;");                        // [OLD]

        GridPane grid = new GridPane();                               // [OLD] layout 4 cột
        grid.setHgap(W_GAP);                                          // [OLD] gap cột
        grid.setAlignment(Pos.CENTER_LEFT);                           // [OLD] căn trái

        ColumnConstraints c0 = new ColumnConstraints(W_IDX);          // [OLD] cột #
        c0.setHalignment(javafx.geometry.HPos.CENTER);                // [OLD] giữa
        ColumnConstraints c1 = new ColumnConstraints();               // [OLD] cột title (grow)
        c1.setHgrow(Priority.ALWAYS);                                 // [OLD] giãn
        ColumnConstraints c2 = new ColumnConstraints(W_ART);          // [OLD] cột artist
        ColumnConstraints c3 = new ColumnConstraints(W_TIME);         // [OLD] cột time
        c3.setHalignment(javafx.geometry.HPos.RIGHT);                 // [OLD] canh phải
        grid.getColumnConstraints().addAll(c0, c1, c2, c3);           // [OLD] áp constraints

        grid.add(idxCell, 0, 0);                                      // [OLD] add ô #
        grid.add(title,   1, 0);                                      // [OLD] add title
        grid.add(artist,  2, 0);                                      // [OLD] add artist
        GridPane.setHalignment(time, javafx.geometry.HPos.RIGHT);     // [OLD] canh phải time
        grid.add(time,    3, 0);                                      // [OLD] add time

        grid.widthProperty().addListener((o, ov, nv) -> {             // [OLD] khi grid đổi rộng
            double totalFixed = W_IDX + W_ART + W_TIME +
                                 W_GAP * 3 + PAD_LR;                  // [OLD] tổng phần cố định
            double wTitle = Math.max(80, nv.doubleValue() - totalFixed); // [OLD] phần còn lại cho title
            title.setMaxWidth(wTitle);                                // [OLD]
            title.setPrefWidth(wTitle);                               // [OLD]
        });

        Button row = new Button();                                    // [OLD] bọc 1 hàng trong Button
        row.setGraphic(grid);                                         // [OLD]
        row.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);           // [OLD]
        row.setAlignment(Pos.CENTER_LEFT);                            // [OLD]
        row.setStyle("-fx-background-color:#1F242B; " +               // [OLD]
                     "-fx-background-radius:12; " +
                     "-fx-padding:10 14 10 14;");

        grid.prefWidthProperty().bind(row.widthProperty()             // [OLD] tránh tràn ngang
                .subtract(PAD_LR));

        row.setOnAction(e -> controller.play(song));                  // [OLD] click row → play bài

        row.setOnMouseEntered(e -> {                                  // [OLD] hover vào
            row.setStyle("-fx-background-color:#2A3038; " +           // [OLD]
                         "-fx-background-radius:12; " +
                         "-fx-padding:10 14 10 14;");
            idx.setVisible(false);                                    // [OLD]
            play.setVisible(true);                                    // [OLD]
        });
        row.setOnMouseExited(e -> {                                   // [OLD] rời hover
            row.setStyle("-fx-background-color:#1F242B; " +           // [OLD]
                         "-fx-background-radius:12; " +
                         "-fx-padding:10 14 10 14;");
            play.setVisible(false);                                   // [OLD]
            idx.setVisible(true);                                     // [OLD]
        });

        // ===== [CHANGED] Context menu: item bo góc, nền xanh đậm, chữ trắng, dày, KHÔNG viền xanh hover =====
        row.setOnContextMenuRequested(ev -> {
            ContextMenu cm = new ContextMenu();

            Button btnRemove = new Button("🗑 Remove from playlist"); // [ADD] dùng Button để style chủ động
            btnRemove.setFocusTraversable(false);
            btnRemove.setStyle(
                "-fx-background-color:#0B5D3C;" +                    // nền xanh đậm
                "-fx-background-radius:12;" +                        // bo góc
                "-fx-text-fill:white;" +                             // chữ trắng
                "-fx-font-weight:800;" +                             // “dày”
                "-fx-font-size:14px;" +                              // cỡ chữ
                "-fx-padding:10 16;" +                               // padding dày
                "-fx-cursor:hand;" +
                // tắt viền focus ring mặc định của Button
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-background-insets:0;"
            );
            // Hover sáng hơn nhẹ để có feedback (vẫn tắt viền)
            btnRemove.setOnMouseEntered(eh -> btnRemove.setStyle(
                "-fx-background-color:#0E6F49;" +
                "-fx-background-radius:12;" +
                "-fx-text-fill:white;" +
                "-fx-font-weight:800;" +
                "-fx-font-size:14px;" +
                "-fx-padding:10 16;" +
                "-fx-cursor:hand;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-background-insets:0;"
            ));
            btnRemove.setOnMouseExited(eh -> btnRemove.setStyle(
                "-fx-background-color:#0B5D3C;" +
                "-fx-background-radius:12;" +
                "-fx-text-fill:white;" +
                "-fx-font-weight:800;" +
                "-fx-font-size:14px;" +
                "-fx-padding:10 16;" +
                "-fx-cursor:hand;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-background-insets:0;"
            ));
            btnRemove.setOnAction(ae -> { remove(song); cm.hide(); });

            CustomMenuItem styledItem = new CustomMenuItem(btnRemove, false); // [ADD] nhét Button đã style
            cm.getItems().add(styledItem);

            // Làm trong suốt popup; padding nhẹ
            cm.setStyle("-fx-background-color: transparent; -fx-padding:6; -fx-background-insets:0;");

            // [FIX] KHÔNG setStyle lên root Content (tránh RuntimeException: bound value)
            //       Chỉ style các .menu-item con để xóa highlight viền xanh khi hover/focus
            cm.setOnShown(e -> {
                if (cm.getSkin() != null && cm.getSkin().getNode() instanceof Region root) {
                    for (Node n : root.lookupAll(".menu-item")) {
                        if (n instanceof Region r) {
                            r.setStyle(
                                "-fx-background-color: transparent;" +  // bỏ nền highlight
                                "-fx-background-insets: 0;" +
                                "-fx-background-radius: 0;" +
                                "-fx-padding: 0;" +
                                "-fx-border-color: transparent;"        // bỏ viền xanh xung quanh
                            );
                        }
                    }
                    // (tuỳ chọn) nếu label bên trong vẫn có nền ở một số theme:
                    for (Node n : root.lookupAll(".menu-item .label")) {
                        if (n instanceof Labeled l) {
                            l.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                        }
                    }
                }
            });

            cm.setAutoHide(true);
            cm.show(row, ev.getScreenX(), ev.getScreenY());
        });

        return row;                                                   // [OLD] trả về 1 hàng hoàn chỉnh
    }

    // ===================== Debounce helper — y hệt HomeUI =====================
    private void scheduleRestyleScrollBars() {                        // [ADD] gom nhiều sự kiện → 1 lần restyle
        sbDebounce.setOnFinished(e -> restyleScrollBars());           // [ADD] khi hết 50ms → gọi restyle
        sbDebounce.playFromStart();                                   // [ADD] reset bộ đếm 50ms mỗi lần gọi
    }

    // ===================== CSS Scrollbar — y hệt HomeUI =====================
    private void restyleScrollBars() {                                // [ADD] áp CSS cho scrollbar
        if (lv == null) return;                                       // [ADD] an toàn: lv chưa khởi tạo thì thoát

        Platform.runLater(() -> {                                     // [ADD] đợi skin/render xong rồi mới lookup
            // --- 1) ẨN HOÀN TOÀN THANH NGANG ---
            for (Node sb : lv.lookupAll(".scroll-bar:horizontal")) {  // [ADD] duyệt tất cả scrollbar ngang
                sb.setStyle("-fx-opacity: 0;" +                       // [ADD] ẩn hẳn
                            "-fx-pref-height: 0;" +                   // [ADD] chiều cao 0
                            "-fx-max-height: 0;" +                    // [ADD] max 0
                            "-fx-padding: 0;");                       // [ADD] bỏ padding
                sb.setDisable(true);                                  // [ADD] vô hiệu tương tác
                sb.setManaged(false);                                 // [ADD] loại khỏi layout pass
                sb.setVisible(false);                                 // [ADD] không hiển thị
                sb.setPickOnBounds(false);                            // [ADD] không bắt sự kiện
            }

            // --- 2) DỌC: mỏng, track trong suốt, thumb xanh lá, bo viên thuốc ---
            for (Node sb : lv.lookupAll(".scroll-bar:vertical")) {    // [ADD] duyệt scrollbar dọc
                sb.setStyle("-fx-pref-width: 8;" +                    // [ADD] mỏng 8px
                            "-fx-max-width: 8;" +                     // [ADD] khóa max = 8
                            "-fx-background-color: transparent;" +    // [ADD] khung trong suốt
                            "-fx-background-insets: 0;" +             // [ADD] không insets
                            "-fx-padding: 4 2 4 2;");                 // [ADD] tạo rãnh trong khung

                if (sb instanceof Region vbar) {                      // [ADD] chỉ Region mới style sâu được
                    Node inc = vbar.lookup(".increment-button");      // [ADD] nút mũi tên dưới
                    if (inc instanceof Region r) {
                        r.setStyle("-fx-opacity:0;-fx-padding:0;" +   // [ADD] ẩn + bỏ kích thước
                                   "-fx-pref-width:0;-fx-pref-height:0;" +
                                   "-fx-max-width:0;-fx-max-height:0;");
                        r.setManaged(false);                          // [ADD] loại khỏi layout
                    }
                    Node dec = vbar.lookup(".decrement-button");      // [ADD] nút mũi tên trên
                    if (dec instanceof Region r) {
                        r.setStyle("-fx-opacity:0;-fx-padding:0;" +
                                   "-fx-pref-width:0;-fx-pref-height:0;" +
                                   "-fx-max-width:0;-fx-max-height:0;");
                        r.setManaged(false);
                    }

                    Node track = vbar.lookup(".track");               // [ADD] đường ray đứng yên
                    if (track instanceof Region r) {
                        r.setStyle("-fx-background-color:transparent;" + // [ADD] trong suốt
                                   "-fx-background-insets:0;" +          // [ADD]
                                   "-fx-background-radius:100;");         // [ADD] bo tròn (viên thuốc)
                    }

                    Node thumb = vbar.lookup(".thumb");               // [ADD] tay nắm trượt
                    if (thumb instanceof Region r) {
                        r.setStyle("-fx-background-color:#22C55E;" +  // [ADD] xanh lá tươi
                                   "-fx-background-insets:0;" +       // [ADD]
                                   "-fx-background-radius:100;" +      // [ADD] viên thuốc
                                   "-fx-padding:0;");                  // [ADD] không padding
                    }
                }
            }

            // --- 3) Ẩn ô vuông góc dưới (corner) nếu skin có tạo ---
            for (Node corner : lv.lookupAll(".corner")) {             // [ADD] góc giao H/V
                if (corner instanceof Region r) {
                    r.setStyle("-fx-background-color: transparent;"); // [ADD] trong suốt để “mất vệt”
                }
            }
        });
    }
}