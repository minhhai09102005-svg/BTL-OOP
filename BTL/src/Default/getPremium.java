package Default;

import Artist_options.artist_song_managment;
import Artist_options.artist_album_managment;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class getPremium {

    private String artistName;

    public getPremium() {
        this.artistName = "Artist";
    }

    public getPremium(String artistName) {
        this.artistName = (artistName == null || artistName.isBlank()) ? "Artist" : artistName;
    }

    public Scene getScene(Stage stage) {
        // === VBox gốc (2 hàng: topRow & music_play) ===
        VBox root = new VBox(10);
        root.setPadding(new Insets(6));
        root.setStyle("""
            -fx-background-color: #010101;
        """);

        // === Hàng 1: HBox chứa option (trái) + main (phải) ===
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.TOP_LEFT);

        // ----- option: sidebar -----
        VBox option = new VBox(10);
        option.setPrefWidth(270);
        option.setPadding(new Insets(12));
        option.setStyle("""
            -fx-background-color: #000000;
            -fx-background-radius: 12;
        """);

        Label opTitle = new Label("MusicPlayer");
        opTitle.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 22px;
            -fx-font-weight: 800;
        """);

        // ================== NÚT SIDEBAR (y hệt album) ==================

        // Home
        Button btnHome = new Button("Home");
        btnHome.setMaxWidth(Double.MAX_VALUE);
        btnHome.setAlignment(Pos.CENTER_LEFT);
        btnHome.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """);
        btnHome.setOnMouseEntered(e -> btnHome.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 800;
            -fx-background-radius: 10;
            -fx-background-insets: 0;
            -fx-padding: 12 0 12 0;
            -fx-border-color: #12ce4a transparent transparent transparent;
            -fx-border-width: 0 0 0 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 16, 0.2, 0, 4);
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #19A34B 0%, #000000 100%);
        """));
        btnHome.setOnMouseExited(e -> btnHome.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """));

        // song managment
        Button btnSongManagement  = new Button("song managment");
        btnSongManagement.setMaxWidth(Double.MAX_VALUE);
        btnSongManagement.setAlignment(Pos.CENTER_LEFT);
        btnSongManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """);
        btnSongManagement.setOnMouseEntered(e -> btnSongManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 800;
            -fx-background-radius: 10;
            -fx-background-insets: 0;
            -fx-padding: 12 0 12 0;
            -fx-border-color: #12ce4a transparent transparent transparent;
            -fx-border-width: 0 0 0 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 16, 0.2, 0, 4);
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #19A34B 0%, #000000 100%);
        """));
        btnSongManagement.setOnMouseExited(e -> btnSongManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """));

        // album managment
        Button btnAlbumManagement = new Button("album managment");
        btnAlbumManagement.setMaxWidth(Double.MAX_VALUE);
        btnAlbumManagement.setAlignment(Pos.CENTER_LEFT);
        btnAlbumManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """);
        btnAlbumManagement.setOnMouseEntered(e -> btnAlbumManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 800;
            -fx-background-radius: 10;
            -fx-background-insets: 0;
            -fx-padding: 12 0 12 0;
            -fx-border-color: #12ce4a transparent transparent transparent;
            -fx-border-width: 0 0 0 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 16, 0.2, 0, 4);
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #19A34B 0%, #000000 100%);
        """));
        btnAlbumManagement.setOnMouseExited(e -> btnAlbumManagement.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """));

        // Log out
        Button btnLogout = new Button("Log out");
        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.setAlignment(Pos.CENTER_LEFT);
        btnLogout.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """);
        btnLogout.setOnMouseEntered(e -> btnLogout.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 800;
            -fx-background-radius: 10;
            -fx-background-insets: 0;
            -fx-padding: 12 0 12 0;
            -fx-border-color: #12ce4a transparent transparent transparent;
            -fx-border-width: 0 0 0 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 16, 0.2, 0, 4);
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #19A34B 0%, #000000 100%);
        """));
        btnLogout.setOnMouseExited(e -> btnLogout.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: 700;
            -fx-background-color: transparent;
            -fx-background-radius: 10;
            -fx-padding: 12 0 12 0;
            -fx-border-color: transparent;
            -fx-border-width: 0;
            -fx-cursor: hand;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
        """));

        // ----- main content -----
        VBox main = new VBox(10);
        main.setStyle("""
            -fx-background-color: #000000;
            -fx-background-radius: 12;
        """);
        main.setPadding(new Insets(12));
        HBox.setHgrow(main, Priority.ALWAYS);

        HBox searchBar = new HBox(8);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(4, 4, 8, 4));

        TextField txtSearch = new TextField();
        txtSearch.setPromptText("Search song, artist, album...");
        txtSearch.setStyle("""
            -fx-background-color: #1B1B1B;
            -fx-text-fill: #EEEEEE;
            -fx-prompt-text-fill: #8E8E8E;
            -fx-background-radius: 10;
            -fx-border-color: #333333;
            -fx-border-radius: 10;
            -fx-padding: 10 12 10 12;
            -fx-font-size: 14px;
        """);
        HBox.setHgrow(txtSearch, Priority.ALWAYS);

        Button btnSearch = new Button("🔎 Search");
        btnSearch.setStyle("""
            -fx-background-color: #2E7D32;
            -fx-background-radius: 10;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 700;
            -fx-cursor: hand;
            -fx-padding: 10 14 10 14;
        """);
        btnSearch.setOnMouseEntered(e -> btnSearch.setStyle("""
            -fx-background-color: #388E3C;
            -fx-background-radius: 10;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 700;
            -fx-cursor: hand;
            -fx-padding: 10 14 10 14;
        """));
        btnSearch.setOnMouseExited(e -> btnSearch.setStyle("""
            -fx-background-color: #2E7D32;
            -fx-background-radius: 10;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 700;
            -fx-cursor: hand;
            -fx-padding: 10 14 10 14;
        """));

        Button btnClear = new Button("✕");
        btnClear.setTooltip(new Tooltip("Clear"));
        btnClear.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #DDDDDD;
            -fx-font-size: 16px;
            -fx-font-weight: 800;
            -fx-cursor: hand;
            -fx-padding: 6 10 6 10;
        """);
        btnClear.setOnAction(e -> txtSearch.clear());
        btnSearch.setOnAction(e -> System.out.println("Searching: " + txtSearch.getText().trim()));

        searchBar.getChildren().addAll(txtSearch, btnClear, btnSearch);

        Label welcome = new Label("Album Management");
        welcome.setStyle("""
            -fx-text-fill: #BDBDBD;
            -fx-font-size: 14px;
            -fx-font-weight: 700;
        """);
        StackPane mainContent = new StackPane(welcome);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        main.getChildren().addAll(searchBar, mainContent);

        // ===== HÀNH VI =====
        // Home -> quay về ArtistUI
        btnHome.setOnAction(e -> {
            ArtistUI ui = new ArtistUI(artistName);
            stage.setScene(ui.getScene(stage));
        });

        // song managment -> chuyển sang màn song
        btnSongManagement.setOnAction(e -> {
            artist_song_managment view = new artist_song_managment(artistName);
            stage.setScene(view.getScene(stage));
        });

        // album managment -> chuyển sang màn album (giống album đang làm)
        btnAlbumManagement.setOnAction(e -> {
            artist_album_managment view = new artist_album_managment(artistName);
            stage.setScene(view.getScene(stage));
        });

        // Logout -> Login
        btnLogout.setOnAction(e -> {
            LoginUI loginUI = new LoginUI();
            stage.setScene(loginUI.getScene(stage));
        });

        option.getChildren().addAll(opTitle, btnHome, btnSongManagement, btnAlbumManagement, btnLogout);
        topRow.getChildren().addAll(option, main);

        // === Hàng 2: music_play (y hệt album) ===
        HBox music_play = new HBox();
        music_play.setAlignment(Pos.CENTER_LEFT);
        music_play.setSpacing(0);
        music_play.setPadding(new Insets(10));
        music_play.setStyle("""
            -fx-background-color: #1E1E1E;
            -fx-background-radius: 12;
        """);

        // meta
        VBox meta = new VBox(4);
        meta.setPadding(new Insets(0, 16, 0, 12));
        Label song   = new Label("Be Cool");
        Label artist = new Label("Ngọt");
        song.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 800;
        """);
        artist.setStyle("""
            -fx-text-fill: #B3B3B3;
            -fx-font-size: 12px;
            -fx-font-weight: 600;
        """);
        meta.getChildren().addAll(song, artist);

        // controls
        VBox music_control = new VBox(6);
        music_control.setAlignment(Pos.CENTER);
        music_control.setPadding(new Insets(0, 16, 0, 16));

        HBox ctrlRow = new HBox(24);
        ctrlRow.setAlignment(Pos.CENTER);
        Button btnPrev   = new Button("⏮");
        Button btnToggle = new Button("⏸");
        btnToggle.setTooltip(new Tooltip("Pause"));
        Button btnNext   = new Button("⏭");

        for (Button b : new Button[]{btnPrev, btnToggle, btnNext}) {
            b.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #EDEDED;
                -fx-font-size: 16px;
                -fx-font-weight: 800;
                -fx-cursor: hand;
                -fx-padding: 6 10 6 10;
            """);
        }

        btnToggle.setOnAction(e -> {
            boolean isPause = "⏸".equals(btnToggle.getText());
            if (isPause) { btnToggle.setText("▶"); btnToggle.getTooltip().setText("Play"); }
            else { btnToggle.setText("⏸"); btnToggle.getTooltip().setText("Pause"); }
        });

        ctrlRow.getChildren().addAll(btnPrev, btnToggle, btnNext);

        HBox seekRow = new HBox(12);
        seekRow.setAlignment(Pos.CENTER);
        Label cur = new Label("0:00");
        Label total = new Label("3:25");
        Slider seek = new Slider(0, 205, 0);
        seek.setMinWidth(520);
        seek.setPrefWidth(520);
        seekRow.getChildren().addAll(cur, seek, total);

        music_control.getChildren().addAll(ctrlRow, seekRow);

        // volume
        HBox volumeBox = new HBox(12);
        volumeBox.setAlignment(Pos.CENTER_RIGHT);
        volumeBox.setPadding(new Insets(0, 12, 0, 12));
        Slider vol  = new Slider(0, 100, 60);
        vol.setPrefWidth(140);
        Label volIcon = new Label();
        volIcon.setStyle("""
            -fx-text-fill: #EDEDED;
            -fx-font-size: 14px;
            -fx-cursor: hand;
        """);
        volIcon.textProperty().bind(
            Bindings.when(vol.valueProperty().lessThanOrEqualTo(0.0))
                    .then("🔇").otherwise("🔊")
        );
        volIcon.setOnMouseClicked(e -> {
            if (vol.getValue() <= 0.0) vol.setValue(50.0);
            else vol.setValue(0.0);
        });
        volumeBox.getChildren().addAll(volIcon, vol);

        Region leftSpacer  = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer,  Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        HBox.setHgrow(music_control, Priority.NEVER);
        music_control.setMaxWidth(Region.USE_COMPUTED_SIZE);

        music_play.getChildren().setAll(
            meta,
            leftSpacer,
            music_control,
            rightSpacer,
            volumeBox
        );

        // === Gắn 2 hàng vào root ===
        root.getChildren().addAll(topRow, music_play);
        VBox.setVgrow(topRow, Priority.ALWAYS);

        // === Scene ===
        return new Scene(root, 900, 700);
    }
}
