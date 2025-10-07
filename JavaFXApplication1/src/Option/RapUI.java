/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Option;

/**
 *
 * @author Admin
 */
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class RapUI extends VBox {
    public RapUI() {
        this.setStyle("-fx-background-color: #010101;");
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("🎤 Rap Playlist");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");
        this.getChildren().add(title);

        this.getChildren().addAll(
            new Label("• Đen Vâu - Trốn Tìm"),
            new Label("• Binz - Bigcityboi"),
            new Label("• Karik - Người Lạ Ơi"),
            new Label("• Suboi - N-Sao?")
        );
        this.getChildren().forEach(node -> ((Label)node).setStyle("-fx-text-fill: white; -fx-font-size: 16px;"));
    }
}

