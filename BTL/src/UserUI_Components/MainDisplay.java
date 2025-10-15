package UserUI_Components;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// 👇 thêm import HomeUI
import Sidebar_Options.HomeUI;

// Vùng đen hiển thị nội dung trung tâm 
public class MainDisplay extends StackPane {

    private final StackPane defaultContent;

    public MainDisplay() {
        setPrefSize(900, 400);
        setStyle("-fx-background-color: #010101;");

        defaultContent = new StackPane(new Label("Welcome"));
        defaultContent.setStyle("-fx-background-color: #010101;");
        // defaultContent co giãn theo mainDisplay
        defaultContent.prefWidthProperty().bind(widthProperty());
        defaultContent.prefHeightProperty().bind(heightProperty());

        // 👇 thay vì hiển thị defaultContent, hiển thị HomeUI mặc định
        HomeUI home = new HomeUI();
        show(bindInto(home));
        // getChildren().setAll(defaultContent); // (giữ lại nếu muốn fallback)
    }

    // Hiển thị view mới (nếu null -> về mặc định)
    public void show(Node view) {
        if (view == null) {
            getChildren().setAll(defaultContent);
            return;
        }
        getChildren().setAll(view);
    }

    // Quay về nội dung mặc định
    public void showDefault() {
        show(null);
    }

    // Tiện ích: bind kích thước view mới với mainDisplay, nếu là Region
    public <T extends Node> T bindInto(T view) {
        if (view instanceof Region r) {
            r.prefWidthProperty().bind(widthProperty());
            r.prefHeightProperty().bind(heightProperty());
        } else {
            // nếu không phải Region, bọc 1 lớp để bind
            StackPane wrapper = new StackPane(view);
            wrapper.prefWidthProperty().bind(widthProperty());
            wrapper.prefHeightProperty().bind(heightProperty());
            show(wrapper);
            return view;
        }
        return view;
    }
}
