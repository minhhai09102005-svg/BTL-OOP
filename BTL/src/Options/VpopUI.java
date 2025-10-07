/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Options;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VpopUI extends VBox {
    private ListView<String> listView;

    public VpopUI() {
        // ✅ Set kích thước khớp với Scene (1250x700)
        this.setPrefSize(1250, 700);

        listView = new ListView<>();
        listView.getItems().addAll(
            "Bài hát 1 - Ca sĩ A",
            "Bài hát 2 - Ca sĩ B",
            "Bài hát 3 - Ca sĩ C",
            "Bài hát 4 - Ca sĩ D",
            "Bài hát 5 - Ca sĩ E",
            "bài hát 6",
            "bài hát 7",
            "bài hát 8",
            "bài hát 9",
            "bài hát 10",
            "bài hát 11",
            "bài hát 12",
            "bài hát 13",
            "bài hát 14",
            "bài hát 15",
            "bài hát 16",
            "bài hát 17",
            "bài hát 18 ",
            "bài hát 19"
        );

        // 🎨 Set màu , cỡ chữ cho listView
        listView.setStyle(
            "-fx-control-inner-background: #010101;" +  // nền bên trong
            "-fx-background-color: #010101;" +         // nền ngoài
            "-fx-text-fill: white;" +                  // màu chữ
            "-fx-font-size: 18px;" +                   // cỡ chữ
            "-fx-font-weight: 800;"                    // in đậm
        );

        //xử lý khi click nhiều lần 
        listView.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Platform.runLater(() -> {
                    // Ẩn thanh ngang
                    listView.lookupAll(".scroll-bar:horizontal").forEach(scroll -> {
                        scroll.setStyle(
                            "-fx-opacity: 0;" +
                            "-fx-max-height: 0;"
                        );
                    });

                    // Thanh cuộn dọc
                    listView.lookupAll(".scroll-bar:vertical").forEach(scroll -> {
                        scroll.setStyle(
                            "-fx-pref-width: 10;" +             // độ rộng
                            "-fx-background-color: transparent;"
                        );
                    });

                    // Track (nền của thanh cuộn)
                    listView.lookupAll(".track").forEach(track -> {
                        track.setStyle(
                            "-fx-background-color: #2b2b2b;" +
                            "-fx-background-radius: 10;"
                        );
                    });

                    // Thumb (nút kéo)
                    listView.lookupAll(".thumb").forEach(thumb -> {
                        thumb.setStyle(
                            "-fx-background-color: #00bfff;" +  // xanh dương
                            "-fx-background-radius: 10;"        // bo góc => viên thuốc
                        );
                    });
                });
            }
        });

        // 🏷️ Tiêu đề
        Label title = new Label("🎵 Danh sách nhạc VPop");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        this.setPadding(new Insets(10));
        this.setSpacing(10);

        // ✅ Cho listView tự động giãn hết phần còn lại
        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // chiếm hết VBox

        this.getChildren().addAll(title, listView);

        // ✅ Nền khớp UI
        this.setStyle("-fx-background-color: #010101; -fx-background-radius: 10;");
    }
}
