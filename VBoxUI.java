package javafxapplication3;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VBoxUI extends VBox {
    private ListView<String> listView;

    public VBoxUI() {
        // ✅ Set kích thước khớp với Scene (1250x700)
        this.setPrefSize(1250, 700);

        listView = new ListView<>();
        listView.getItems().addAll(
            "Bài hát 1 - Ca sĩ A",
            "Bài hát 2 - Ca sĩ B",
            "Bài hát 3 - Ca sĩ C",
            "Bài hát 4 - Ca sĩ D",
            "Bài hát 5 - Ca sĩ E"
        );

        Label title = new Label("🎵 Danh sách nhạc VPop");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        this.setPadding(new Insets(10));
        this.setSpacing(10);

        // ✅ Cho listView tự động giãn hết phần còn lại
        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // chiếm hết VBox

        this.getChildren().addAll(title, listView);

        // ✅ Nền khớp UI
        this.setStyle("-fx-background-color: #2b2b2b; -fx-background-radius: 10;");
    }
}
