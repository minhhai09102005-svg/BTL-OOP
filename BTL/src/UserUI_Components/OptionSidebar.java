package UserUI_Components;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import Sidebar_Options.PlaylistUI;
import Sidebar_Options.AlbumUI;
import Sidebar_Options.GenreUI;
import Sidebar_Options.HomeUI;
import Sidebar_Options.FavouriteUI;

import Default.LoginUI;
import Default.Song;

public class OptionSidebar extends VBox {

    private final MainDisplay mainDisplay;
    private final Song.PlayerController controller; // giữ controller để mở HomeUI

    // nhận thêm controller (giữ nguyên các tham số còn lại)
    public OptionSidebar(MainDisplay mainDisplay, Song.PlayerController controller) {
        this.mainDisplay = mainDisplay;
        this.controller = controller;

        setPrefSize(200, 400);
        setStyle("-fx-background-color: #000000; -fx-background-radius: 10;");
        setSpacing(8);

        // KHÔNG padding trái/phải
        setPadding(new Insets(12, 0, 12, 0)); // top=12, right=0, bottom=12, left=0
        setFillWidth(true);                   // cho con giãn full width

        // ===== Tiêu đề =====
        Label label_1 = new Label("120 An Liễng");
        label_1.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        getChildren().add(label_1);

        // ===== Profile (avatar tròn + nút user123) =====
        HBox profileRow = new HBox(10);
        profileRow.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView();
        try {
            Image img = new Image(getClass().getResource("/image/user.jpg").toExternalForm());
            avatar.setImage(img);
            double iw = img.getWidth(), ih = img.getHeight();
            double side = Math.min(iw, ih);
            avatar.setViewport(new Rectangle2D((iw - side) / 2, (ih - side) / 2, side, side));
        } catch (Exception ignore) {}
        avatar.setFitWidth(36);
        avatar.setFitHeight(36);
        avatar.setPreserveRatio(false);
        avatar.setClip(new Circle(18, 18, 18));

        Button btnProfile = new Button("user123");
        btnProfile.setMaxWidth(Double.MAX_VALUE);
        btnProfile.setAlignment(Pos.CENTER_LEFT);
        btnProfile.setBackground(Background.EMPTY);
        btnProfile.setBorder(Border.EMPTY);
        btnProfile.setFocusTraversable(false);
        btnProfile.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 6 8 6 8;"
        );
        btnProfile.setOnMouseEntered(e -> btnProfile.setUnderline(true));
        btnProfile.setOnMouseExited(e -> btnProfile.setUnderline(false));

        profileRow.getChildren().addAll(avatar, btnProfile);
        getChildren().add(profileRow);

        // ===== Home =====
        Button btnHome = mkPrimary(" 🏠 Home");
        btnHome.setOnAction(e -> {
            HomeUI home = new HomeUI(controller); // truyền controller
            mainDisplay.bindInto(home);
            mainDisplay.show(home);
        });
        getChildren().add(btnHome);

        // ===== My Playlists =====
        Button btnPlaylist = mkPrimary(" 📚 My Playlists");
        btnPlaylist.setOnAction(e -> {
            PlaylistUI view = new PlaylistUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnPlaylist);

        // ===== My Album =====
        Button btnAlbum = mkPrimary(" 🎵 My Album");
        btnAlbum.setOnAction(e -> {
            AlbumUI view = new AlbumUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnAlbum);

        // ===== Genres =====
        Button btnGenres = mkPrimary(" 💿 Genres");
        btnGenres.setOnAction(e -> {
            GenreUI view = new GenreUI(mainDisplay);
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnGenres);

        // ===== Favourites =====
        Button btnFavourite = mkPrimary(" ♥ Favourites");
        btnFavourite.setOnAction(e -> {
            FavouriteUI view = new FavouriteUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnFavourite);

        // ===== Log out =====
        Button btnLogout = mkPrimary(" ⎋ Log out");
        btnLogout.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText(null);
            confirm.setContentText("Bạn có chắc chắn muốn đăng xuất?");
            confirm.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK || result == ButtonType.YES) {
                    Stage stage = (Stage) getScene().getWindow();
                    LoginUI loginUI = new LoginUI();
                    stage.setScene(loginUI.getScene(stage));
                }
            });
        });
        getChildren().add(btnLogout);
    }

    private Button mkPrimary(String text) {
        Button b = new Button(text);

        // KHÔNG khóa chiều rộng; cho phép full width
        b.setPrefHeight(50);
        b.setMinHeight(50);
        b.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(b, Insets.EMPTY); // đảm bảo không có margin ngoài

        b.setAlignment(Pos.CENTER_LEFT);
       b.setStyle(
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #14532D 0%, #000000 100%);" + // xanh lá đậm tươi -> đen
        "-fx-background-insets: 0;" +
        "-fx-background-radius: 6px;" +
        "-fx-text-fill: #F2F2F2;" +
        "-fx-font-size: 16px;" +
        "-fx-font-weight: 700;" +
        "-fx-cursor: hand;" +
        "-fx-padding: 8 12 8 12;"
    );

    // --- hover: xanh tươi -> đen tuyệt đối (trái → phải) ---
    b.setOnMouseEntered(e -> b.setStyle(
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #22C55E 0%, #000000 100%);" + // xanh tươi -> đen
        "-fx-background-insets: 0;" +
        "-fx-background-radius: 6px;" +
        "-fx-text-fill: #FFFFFF;" +
        "-fx-font-size: 18px;" +
        "-fx-font-weight: 700;" +
        "-fx-cursor: hand;" +
        "-fx-padding: 8 12 8 12;"
    ));

    // --- rời chuột: quay lại như setStyle ---
    b.setOnMouseExited(e -> b.setStyle(
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #14532D 0%, #000000 100%);" +
        "-fx-background-insets: 0;" +
        "-fx-background-radius: 6px;" +
        "-fx-text-fill: #F2F2F2;" +
        "-fx-font-size: 16px;" +
        "-fx-font-weight: 700;" +
        "-fx-cursor: hand;" +
        "-fx-padding: 8 12 8 12;"
    ));

    return b;
}
}
