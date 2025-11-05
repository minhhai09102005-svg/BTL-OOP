package UserUI_Components.Sidebar_Options.Genre;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import UserUI_Components.MainDisplay;
import UserUI_Components.Sidebar_Options.Genre.SubGenreUI;
import Default.Song;
import UserUI_Components.MainDisplay;

public class GenreUI extends StackPane {

    private final MainDisplay mainDisplay;

    public GenreUI(MainDisplay mainDisplay) {
        this.mainDisplay = mainDisplay;

        setStyle( // nền ảnh thay cho màu #000000
            "-fx-background-image: url('" + getClass().getResource("/image/photo2.png").toExternalForm() + "');" +
            "-fx-background-size: cover;" +
            "-fx-background-position: center center;" +
            "-fx-background-repeat: no-repeat;"
        );

        // ===================== OTHER =====================
       
Button other = new Button("Other...");
other.setPrefSize(450, 190);
other.setAlignment(Pos.CENTER); // ✅ Luôn căn giữa chữ trong nút
other.setStyle(
    "-fx-background-color: transparent;" +
    "-fx-background-insets: 0;" +
    "-fx-background-radius: 20px;" +
    "-fx-border-color: rgba(255,255,255,0.35);" +
    "-fx-border-width: 1.5;" +
    "-fx-border-radius: 20px;" +
    "-fx-text-fill: #F2F2F2;" +
    "-fx-font-size: 20px;" +
    "-fx-font-weight: 700;" +
    "-fx-cursor: hand;" +
    "-fx-padding: 8 12 8 12;"
);

// ✅ Hover KHÔNG thay đổi radius, font-size, padding → không lệch
other.setOnMouseEntered(e -> other.setStyle(
    "-fx-background-color: rgba(255,255,255,0.1);" + // chỉ đổi màu nền
    "-fx-background-insets: 0;" +
    "-fx-background-radius: 20px;" +
    "-fx-border-color: rgba(255,255,255,0.6);" +
    "-fx-border-width: 1.5;" +
    "-fx-border-radius: 20px;" +
    "-fx-text-fill: #FFFFFF;" +                     // đổi màu chữ sáng hơn
    "-fx-font-size: 20px;" +                        // ❗GIỮ NGUYÊN
    "-fx-font-weight: 700;" +
    "-fx-cursor: hand;" +
    "-fx-padding: 8 12 8 12;"                      // ❗GIỮ NGUYÊN
));

other.setOnMouseExited(e -> other.setStyle(
    "-fx-background-color: transparent;" +
    "-fx-background-insets: 0;" +
    "-fx-background-radius: 20px;" +
    "-fx-border-color: rgba(255,255,255,0.35);" +
    "-fx-border-width: 1.5;" +
    "-fx-border-radius: 20px;" +
    "-fx-text-fill: #F2F2F2;" +
    "-fx-font-size: 20px;" +                       // ❗KHÔNG ĐỔI
    "-fx-font-weight: 700;" +
    "-fx-cursor: hand;" +
    "-fx-padding: 8 12 8 12;"                     // ❗KHÔNG ĐỔI
));

other.setOnAction(e -> {
    Song.PlayerController pc = mainDisplay.getPlayerController();
    SubGenreUI v = new SubGenreUI(pc, "Other");
    mainDisplay.bindInto(v);
    mainDisplay.show(v);
});
        // ===================== VPOP =====================
        Button vpop = new Button(); // [NEW] dùng graphic để chứa title + intro
        vpop.setPrefSize(450, 190);
        vpop.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 20px;" +
            "-fx-border-color: rgba(255,255,255,0.35);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 20px;" +
            "-fx-text-fill: #F2F2F2;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
        );
        Label vpopTitle = new Label(" 🎶 VPop");
        vpopTitle.setStyle("-fx-text-fill:#F2F2F2; -fx-font-size:20px; -fx-font-weight:700;");
        vpopTitle.setMaxWidth(Double.MAX_VALUE);
        vpopTitle.setAlignment(Pos.CENTER);
        vpopTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label vpopIntro = new Label("Giai điệu bắt tai, ca từ gần gũi — sắc màu đặc trưng của âm nhạc Việt.");
        vpopIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        vpopIntro.setWrapText(true);
        vpopIntro.setMaxWidth(Double.MAX_VALUE);
        vpopIntro.setAlignment(Pos.CENTER);
        vpopIntro.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox vpopBox = new VBox(6, vpopTitle, vpopIntro);
        vpopBox.setAlignment(Pos.CENTER);
        vpopBox.setFillWidth(true);
        vpop.setGraphic(vpopBox);
        vpop.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        vpop.setAlignment(Pos.CENTER);
        vpop.setOnMouseEntered(e -> {
            vpop.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: rgba(255,255,255,0.6);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 22px;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            vpopIntro.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        vpop.setOnMouseExited(e -> {
            vpop.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: rgba(255,255,255,0.35);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 20px;" +
                "-fx-text-fill: #F2F2F2;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            vpopIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        vpop.setOnAction(e -> {
            Song.PlayerController pc = mainDisplay.getPlayerController();
            SubGenreUI v = new SubGenreUI(pc, "VPOP");
            mainDisplay.bindInto(v);
            mainDisplay.show(v);
        });


        // ===================== RAP =====================
        Button rap = new Button(); // [NEW]
        rap.setPrefSize(450, 190);
        rap.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 20px;" +
            "-fx-border-color: rgba(255,255,255,0.35);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 20px;" +
            "-fx-text-fill: #F2F2F2;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
        );
        Label rapTitle = new Label(" 🎤 Rap");
        rapTitle.setStyle("-fx-text-fill:#F2F2F2; -fx-font-size:20px; -fx-font-weight:700;");
        rapTitle.setMaxWidth(Double.MAX_VALUE);
        rapTitle.setAlignment(Pos.CENTER);
        rapTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label rapIntro = new Label("Tự do thể hiện cá tính qua flow, punchline và nhịp beat đầy cá tính.");
        rapIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        rapIntro.setWrapText(true);
        rapIntro.setMaxWidth(Double.MAX_VALUE);
        rapIntro.setAlignment(Pos.CENTER);
        rapIntro.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox rapBox = new VBox(6, rapTitle, rapIntro);
        rapBox.setAlignment(Pos.CENTER);
        rapBox.setFillWidth(true);
        rap.setGraphic(rapBox);
        rap.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        rap.setAlignment(Pos.CENTER);
        rap.setOnMouseEntered(e -> {
            rap.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: rgba(255,255,255,0.6);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 22px;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            rapIntro.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        rap.setOnMouseExited(e -> {
            rap.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: rgba(255,255,255,0.35);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 20px;" +
                "-fx-text-fill: #F2F2F2;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            rapIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        rap.setOnAction(e -> {
            Song.PlayerController pc = mainDisplay.getPlayerController();
            SubGenreUI v = new SubGenreUI(pc, "Rap");
            mainDisplay.bindInto(v);
            mainDisplay.show(v);
        });


        // ===================== ROCK =====================
        Button rock = new Button(); // [NEW]
        rock.setPrefSize(450, 190);
        rock.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 20px;" +
            "-fx-border-color: rgba(255,255,255,0.35);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 20px;" +
            "-fx-text-fill: #F2F2F2;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
        );
        Label rockTitle = new Label(" 🎸 Rock");
        rockTitle.setStyle("-fx-text-fill:#F2F2F2; -fx-font-size:20px; -fx-font-weight:700;");
        rockTitle.setMaxWidth(Double.MAX_VALUE);
        rockTitle.setAlignment(Pos.CENTER);
        rockTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label rockIntro = new Label("Âm thanh mạnh mẽ, guitar điện và trống dồn dập khơi dậy năng lượng bùng nổ.");
        rockIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        rockIntro.setWrapText(true);
        rockIntro.setMaxWidth(Double.MAX_VALUE);
        rockIntro.setAlignment(Pos.CENTER);
        rockIntro.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox rockBox = new VBox(6, rockTitle, rockIntro);
        rockBox.setAlignment(Pos.CENTER);
        rockBox.setFillWidth(true);
        rock.setGraphic(rockBox);
        rock.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        rock.setAlignment(Pos.CENTER);
        rock.setOnMouseEntered(e -> {
            rock.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: rgba(255,255,255,0.6);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 22px;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            rockIntro.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        rock.setOnMouseExited(e -> {
            rock.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: rgba(255,255,255,0.35);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 20px;" +
                "-fx-text-fill: #F2F2F2;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            rockIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        rock.setOnAction(e -> {
            Song.PlayerController pc = mainDisplay.getPlayerController();
            SubGenreUI v = new SubGenreUI(pc, "Rock");
            mainDisplay.bindInto(v);
            mainDisplay.show(v);
        });


        // ===================== US-UK =====================
        Button usuk = new Button(); // [NEW]
        usuk.setPrefSize(450, 190);
        usuk.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 20px;" +
            "-fx-border-color: rgba(255,255,255,0.35);" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 20px;" +
            "-fx-text-fill: #F2F2F2;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 700;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 12 8 12;"
        );
        Label usukTitle = new Label(" 🌎 US-UK");
        usukTitle.setStyle("-fx-text-fill:#F2F2F2; -fx-font-size:20px; -fx-font-weight:700;");
        usukTitle.setMaxWidth(Double.MAX_VALUE);
        usukTitle.setAlignment(Pos.CENTER);
        usukTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label usukIntro = new Label("Những bản hit quốc tế chinh phục hàng triệu trái tim khắp thế giới.");
        usukIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        usukIntro.setWrapText(true);
        usukIntro.setMaxWidth(Double.MAX_VALUE);
        usukIntro.setAlignment(Pos.CENTER);
        usukIntro.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox usukBox = new VBox(6, usukTitle, usukIntro);
        usukBox.setAlignment(Pos.CENTER);
        usukBox.setFillWidth(true);
        usuk.setGraphic(usukBox);
        usuk.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        usuk.setAlignment(Pos.CENTER);
        usuk.setOnMouseEntered(e -> {
            usuk.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: rgba(255,255,255,0.6);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 22px;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            usukIntro.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        usuk.setOnMouseExited(e -> {
            usuk.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: rgba(255,255,255,0.35);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 20px;" +
                "-fx-text-fill: #F2F2F2;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 12 8 12;"
            );
            usukIntro.setStyle("-fx-text-fill: transparent; -fx-font-size: 14px; -fx-font-weight: 800;");
        });
        usuk.setOnAction(e -> {
            Song.PlayerController pc = mainDisplay.getPlayerController();
            SubGenreUI v = new SubGenreUI(pc, "US-UK");
            mainDisplay.bindInto(v);
            mainDisplay.show(v);
        });


        // ===================== TIÊU ĐỀ "Genre" BÊN TRÊN =====================
        Label title = new Label("  💿 Genre");
        title.setStyle(
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 28px;" +
            "-fx-font-weight: 800;"
        );
        HBox titleRow = new HBox(title);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        titleRow.setPadding(new Insets(4, 0, 0, 10));

        // ===================== LƯỚI 3 HÀNG =====================
        HBox row1 = new HBox(20, usuk, vpop);   // button tự chứa intro bên trong
        row1.setMaxWidth(Double.MAX_VALUE);

        HBox row2 = new HBox(20, rap, rock);
        row2.setMaxWidth(Double.MAX_VALUE);

        HBox row3 = new HBox(other); // hàng 3 chỉ 1 phần tử
        row3.setMaxWidth(Double.MAX_VALUE);

        VBox grid = new VBox(15, row1, row2, row3);
        grid.setAlignment(Pos.CENTER);
        grid.setFillWidth(true);
        grid.setPadding(new Insets(24, 24, 24, 24));

        VBox root = new VBox(10, titleRow, grid);
        root.setAlignment(Pos.TOP_LEFT);
        root.setFillWidth(true);
        root.setPadding(new Insets(12, 12, 12, 12));

        VBox.setVgrow(grid, Priority.ALWAYS);
        VBox.setVgrow(row1, Priority.ALWAYS);
        VBox.setVgrow(row2, Priority.ALWAYS);
        VBox.setVgrow(row3, Priority.ALWAYS);

        getChildren().setAll(root);
    }
}