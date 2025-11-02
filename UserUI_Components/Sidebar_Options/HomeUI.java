package UserUI_Components.Sidebar_Options;

import Default.Song;
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
    public static final ObservableList<Song> SONGS = FXCollections.observableArrayList(
        // ===== VPOP =====
        new Song("Đà Lạt Còn Mưa Không Em", "TRO-Music", 291, "Single", "VPOP"),
        new Song("Em Có Nghe", "Hương Tràm", 248, "Sunset", "VPOP"),
        new Song("Chuyện Đôi Ta", "Emcee L, Muộii", 232, "Midnight Stories", "VPOP"),
        new Song("Thích Em Hơi Nhiều", "Wren Evans", 198, "Single", "VPOP"),
        new Song("Nếu Lúc Đó", "Tlinh", 205, "Single", "VPOP"),
        new Song("Dưới Những Cơn Mưa", "Mr. Siro", 312, "Single", "VPOP"),
        new Song("Cơn Mưa Ngang Qua", "Sơn Tùng MTP", 265, "Single", "VPOP"),
        new Song("Không Sao Mà Em Đây Rồi", "Suni Hạ Linh", 216, "Bloom", "VPOP"),
        new Song("Một Nhà", "Da LAB", 230, "Single", "VPOP"),
        new Song("Muộn Rồi Mà Sao Còn", "Sơn Tùng MTP", 245, "Single", "VPOP"),
        new Song("Ngày Khác Lạ", "Đen, Giang Phạm, Triple D", 237, "Single", "VPOP"),
        new Song("Mơ", "Vũ Cát Tường", 222, "Yours", "VPOP"),

        // ===== US-UK =====
        new Song("Blinding Lights", "The Weeknd", 200, "After Hours", "US-UK"),
        new Song("Levitating", "Dua Lipa", 203, "Future Nostalgia", "US-UK"),
        new Song("As It Was", "Harry Styles", 167, "Harry’s House", "US-UK"),
        new Song("Shape of You", "Ed Sheeran", 233, "Divide", "US-UK"),
        new Song("Ghost", "Justin Bieber", 154, "Justice", "US-UK"),
        new Song("Anti-Hero", "Taylor Swift", 201, "Midnights", "US-UK"),
        new Song("Bad Habits", "Ed Sheeran", 231, "=", "US-UK"),
        new Song("Stay", "The Kid LAROI, Justin Bieber", 141, "F*ck Love 3", "US-UK"),
        new Song("Unholy", "Sam Smith, Kim Petras", 156, "Gloria", "US-UK"),
        new Song("Dance Monkey", "Tones and I", 209, "The Kids Are Coming", "US-UK"),
        new Song("Sunflower", "Post Malone, Swae Lee", 158, "Spider-Verse", "US-UK"),
        new Song("Heat Waves", "Glass Animals", 238, "Dreamland", "US-UK"),

        // ===== Rap =====
        new Song("Lối Nhỏ", "Đen, Phương Anh Đào", 254, "Show của Đen", "Rap"),
        new Song("Mang Tiền Về Cho Mẹ", "Đen, Nguyên Thảo", 270, "Single", "Rap"),
        new Song("Ngày Khác Lạ (Rap ver.)", "Đen", 240, "Single", "Rap"),
        new Song("Đi Về Nhà", "Đen, JustaTee", 214, "Single", "Rap"),
        new Song("Ai Muốn Nghe Không", "Đen", 192, "Single", "Rap"),
        new Song("1 Phút", "Andiez (Rap mix)", 210, "Single", "Rap"),
        new Song("Simple Love (Remix)", "Obito, Seachains", 189, "Single", "Rap"),
        new Song("Gu", "Freaky, Seachains", 175, "Single", "Rap"),

        // ===== Rock (Nirvana - Nevermind) =====
        new Song("Smells Like Teen Spirit", "Nirvana", 301, "Nevermind", "Rock"),
        new Song("In Bloom", "Nirvana", 254, "Nevermind", "Rock"),
        new Song("Come as You Are", "Nirvana", 219, "Nevermind", "Rock"),
        new Song("Breed", "Nirvana", 183, "Nevermind", "Rock"),
        new Song("Lithium", "Nirvana", 257, "Nevermind", "Rock"),
        new Song("Polly", "Nirvana", 176, "Nevermind", "Rock"),
        new Song("Territorial Pissings", "Nirvana", 142, "Nevermind", "Rock"),
        new Song("Drain You", "Nirvana", 223, "Nevermind", "Rock"),
        new Song("Lounge Act", "Nirvana", 156, "Nevermind", "Rock"),
        new Song("Stay Away", "Nirvana", 212, "Nevermind", "Rock"),
        new Song("On a Plain", "Nirvana", 196, "Nevermind", "Rock"),
        new Song("Something in the Way", "Nirvana", 230, "Nevermind", "Rock"),

        // ===== EDM =====
        new Song("Faded", "Alan Walker", 212, "Different World", "EDM"),
        new Song("The Nights", "Avicii", 176, "Stories", "EDM"),
        new Song("Closer", "The Chainsmokers, Halsey", 244, "Collage", "EDM"),
        new Song("Waiting For Love", "Avicii", 230, "Stories", "EDM"),
        new Song("Silence", "Marshmello, Khalid", 181, "Single", "EDM"),
        new Song("There For You", "Martin Garrix, Troye Sivan", 213, "Single", "EDM"),
        new Song("Something Just Like This", "The Chainsmokers, Coldplay", 247, "Memories...Do Not Open", "EDM"),
        new Song("Spectre", "Alan Walker", 229, "Single", "EDM"),

        // ===== Other =====
        new Song("Zikir La Ilaha Illallah", "Hud", 563, "Single", "Other"),
        new Song("Weightless", "Marconi Union", 505, "Ambient Transmissions", "Other"),
        new Song("Comptine d’un autre été", "Yann Tiersen", 151, "Amélie OST", "Other"),
        new Song("River Flows In You", "Yiruma", 195, "First Love", "Other"),
        new Song("Tuần Trăng Mật Ở Bản", "Saigon Soul", 206, "Indochine", "Other"),
        new Song("Kaze no Toorimichi", "Joe Hisaishi", 256, "Ghibli Works", "Other"),

        // ===== thêm =====
        new Song("Anomimus", "CinBawi", 197, "Single", "Other"),
        new Song("Dance (Vibez & Tunes Freestyle)", "Maestro Dj Brown", 172, "Single", "EDM"),
        new Song("Yellow", "Coldplay", 269, "Parachutes", "US-UK"),
        new Song("Viva La Vida", "Coldplay", 242, "Viva La Vida", "US-UK"),
        new Song("Photograph", "Ed Sheeran", 258, "Multiply", "US-UK"),
        new Song("Lạc Trôi", "Sơn Tùng MTP", 230, "Single", "VPOP"),
        new Song("Bước Qua Nhau", "Vũ", 250, "Một Vạn Năm", "VPOP"),
        new Song("Hơn Cả Yêu", "Đức Phúc", 248, "Single", "VPOP")
    );

    public HomeUI(Song.PlayerController controller) {
        this.controller = controller;

        setStyle("-fx-background-color:#010101;");
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

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

        HBox box = new HBox(8, t, addBtn);
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

        // Re-apply CSS scrollbar
        restyleScrollBars();
        lv.skinProperty().addListener((obs, oldSkin, newSkin) -> restyleScrollBars());
        lv.sceneProperty().addListener((obs, oldScene, newScene) -> restyleScrollBars());
        lv.widthProperty().addListener((obs, oldW, newW) -> restyleScrollBars());

        return lv;
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
