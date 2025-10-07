/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Option;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class USUKUI extends VBox {
    public USUKUI() {
        this.setStyle("-fx-background-color: #010101;");
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("🌎 US-UK Playlist");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");
        this.getChildren().add(title);

        this.getChildren().addAll(
            new Label("• Taylor Swift - Lover"),
            new Label("• Ed Sheeran - Shape of You"),
            new Label("• The Weeknd - Blinding Lights"),
            new Label("• Billie Eilish - Bad Guy")
        );
        this.getChildren().forEach(node -> ((Label)node).setStyle("-fx-text-fill: white; -fx-font-size: 16px;"));
    }
}

