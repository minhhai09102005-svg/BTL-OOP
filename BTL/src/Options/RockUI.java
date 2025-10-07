/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Options;

/**
 *
 * @author Admin
 */
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class RockUI extends VBox {
    public RockUI() {
        this.setStyle("-fx-background-color: #010101;");
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("🎸 Rock Playlist");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");
        this.getChildren().add(title);

        this.getChildren().addAll(
            new Label("• Linkin Park - Numb"),
            new Label("• Nirvana - Smells Like Teen Spirit"),
            new Label("• Metallica - Nothing Else Matters"),
            new Label("• Green Day - Boulevard of Broken Dreams")
        );
        this.getChildren().forEach(node -> ((Label)node).setStyle("-fx-text-fill: white; -fx-font-size: 16px;"));
    }
}

