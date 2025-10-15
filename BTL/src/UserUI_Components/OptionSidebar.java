package UserUI_Components;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.effect.*;
import javafx.scene.transform.*;
import javafx.scene.canvas.*;
import javafx.scene.chart.*;
import javafx.css.*;
import javafx.scene.media.*;
import javafx.animation.*;

import Sidebar_Options.PlaylistUI;
import Sidebar_Options.AlbumUI;
import Sidebar_Options.TrendingUI;
import Sidebar_Options.GenreUI;
import Sidebar_Options.HomeUI;
import Sidebar_Options.FavouriteUI;
import Default.LoginUI;
import javafx.stage.Stage;

///** Thanh options bên trái (sidebar) */
public class OptionSidebar extends VBox {

    private final MainDisplay mainDisplay;

    public OptionSidebar(MainDisplay mainDisplay) {
        this.mainDisplay = mainDisplay;

        setPrefSize(200, 400);
        setStyle("-fx-background-color: #4A4A4A; -fx-background-radius: 10;");
        setSpacing(8);
        setPadding(new Insets(12));

        // Tiêu đề
        Label label_1 = new Label("MusicPlayer");
        label_1.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        label_1.setTranslateX(30);
        getChildren().add(label_1);

        // ===== Profile (avatar tròn + nút user123) =====
        HBox profileRow = new HBox(10);
        profileRow.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView();
        try {
            Image img = new Image(getClass().getResource("/image/quỷ_sếch.jpg").toExternalForm());
            avatar.setImage(img);
            // cover: crop giữa ảnh thành vuông
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
        Button btnHome = mkPrimary("Home");

        // MỞ HomeUI vào mainDisplay
        btnHome.setOnAction(e -> {
            HomeUI home = new HomeUI();          
            mainDisplay.bindInto(home);           // bind size với mainDisplay
            mainDisplay.show(home);               // hiển thị
        });

        getChildren().add(btnHome);


        // ===== My Playlists =====
        Button btnPlaylist = mkPrimary("My Playlists");
        btnPlaylist.setOnAction(e -> {
            PlaylistUI view = new PlaylistUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnPlaylist);

        // ===== My Album =====
        Button btnAlbum = mkPrimary("My Album");
        btnAlbum.setOnAction(e -> {
            AlbumUI view = new AlbumUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
        getChildren().add(btnAlbum);

        // ===== Trending =====
        Button btnTrending = mkPrimary("Trending");
        btnTrending.setOnAction(e -> {
            TrendingUI view = new TrendingUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });

        // ===== Genres =====
        Button btnGenres = mkPrimary("Genres");
        btnGenres.setOnAction(e -> {
            GenreUI view = new GenreUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });

        getChildren().addAll(btnTrending, btnGenres);
        
        // ===== Favourites =====
        Button btnFavourite = mkPrimary("Favourites");
        btnFavourite.setOnAction(e -> {
            FavouriteUI view = new FavouriteUI();
            mainDisplay.bindInto(view);
            mainDisplay.show(view);
        });
         getChildren().add(btnFavourite);
         
        // ===== Log out =====
        Button btnLogout = mkPrimary("Log out");
        
        // Sự kiện: hỏi xác nhận → Yes: quay về LoginUI, No: đóng popup
        btnLogout.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText(null);
            confirm.setContentText("Bạn có chắc chắn muốn đăng xuất?");

            // hiển thị và lấy lựa chọn
            confirm.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK || result == ButtonType.YES) {
                    // Lấy Stage hiện tại từ OptionSidebar (không cần truyền primaryStage)
                    Stage stage = (Stage) getScene().getWindow();

                    // Điều hướng sang LoginUI
                    LoginUI loginUI = new LoginUI();
                    stage.setScene(loginUI.getScene(stage));
                }// Nếu NO/CANCEL: không làm gì, popup tự đóng
            });
        });
        getChildren().add(btnLogout);
    }

//    /** Tạo style hover đổi màu */
    private Button mkPrimary(String text) {
        Button b = new Button(text);
        b.setPrefSize(160, 50);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setAlignment(Pos.CENTER_LEFT);
        b.setStyle(
            "-fx-background-color: #2F3945;" +
            "-fx-background-radius: 6px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;"
        );
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color: #394656; -fx-background-radius: 6px;" +
            "-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        b.setOnMouseExited(e -> b.setStyle(
            "-fx-background-color: #2F3945; -fx-background-radius: 6px;" +
            "-fx-text-fill: #FEFEFE; -fx-font-size: 16px; -fx-font-weight: 700; -fx-cursor: hand;"
        ));
        return b;
    }
}
