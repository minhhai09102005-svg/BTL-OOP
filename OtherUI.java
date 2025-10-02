/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Option;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class OtherUI extends VBox {
    public OtherUI() {
        this.setStyle("-fx-background-color: #010101;");
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("✨ Other Genres");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");
        this.getChildren().add(title);

        this.getChildren().addAll(
            new Label("• Jazz - Frank Sinatra - Fly Me To The Moon"),
            new Label("• Classical - Beethoven - Symphony No.5"),
            new Label("• EDM - Alan Walker - Faded"),
            new Label("• K-Pop - BTS - Dynamite")
        );
        this.getChildren().forEach(node -> ((Label)node).setStyle("-fx-text-fill: white; -fx-font-size: 16px;"));
    }
}

