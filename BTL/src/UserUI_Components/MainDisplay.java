package UserUI_Components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import Sidebar_Options.HomeUI;
import Default.Song;

public class MainDisplay extends StackPane {

    // Container thật chứa view; chính nó là content của ScrollPane
    private final StackPane contentRoot = new StackPane();
    private final Node defaultContent;

    // Controller để HomeUI gọi play(song)
    private final Song.PlayerController controller;

    // --- Giữ constructor cũ (no-arg) để không vỡ chỗ gọi cũ ---
    public MainDisplay() {
        this(new Song.PlayerController() {
            @Override public void play(Song song) { /* no-op */ }
        });
    }

    // --- Constructor chuẩn: truyền PlayerBar (implements Song.PlayerController) ---
    public MainDisplay(Song.PlayerController controller) {
        this.controller = controller;

        setPrefSize(900, 400);
        setStyle("-fx-background-color:#010101;");

        // ===== Default content =====
        defaultContent = new StackPane(new Label("Welcome"));
        ((Region) defaultContent).setStyle("-fx-background-color:#010101;");

        // contentRoot không bind height để cho phép dài hơn viewport -> cuộn dọc
        contentRoot.setStyle("-fx-background-color:#010101;");
        contentRoot.setMinHeight(Region.USE_PREF_SIZE);
        contentRoot.getChildren().setAll(defaultContent);

        // ===== ScrollPane bọc contentRoot =====
        ScrollPane scroller = new ScrollPane(contentRoot);
        scroller.setFitToWidth(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setPannable(true);
        scroller.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        // >>> Khoá bề ngang để không phát sinh thanh trượt ngang
        contentRoot.minWidthProperty().bind(scroller.widthProperty());
        contentRoot.maxWidthProperty().bind(scroller.widthProperty());

        // MainDisplay chỉ chứa ScrollPane
        getChildren().setAll(scroller);

        // Hiển thị HomeUI mặc định (dùng controller truyền vào)
        HomeUI home = new HomeUI(controller);
        show(bindInto(home));
    }

    // Hiển thị view mới; null -> quay về defaultContent.
    public void show(Node view) {
        contentRoot.getChildren().setAll(view == null ? defaultContent : view);
    }

    // Quay về nội dung mặc định.
    public void showDefault() { show(null); }

    // Bind WIDTH của view với contentRoot để view ôm ngang, chiều cao để tự do cuộn dọc.
    public <T extends Node> T bindInto(T view) {
        if (view instanceof Region r) {
            r.prefWidthProperty().bind(contentRoot.widthProperty());
            r.maxWidthProperty().bind(contentRoot.widthProperty()); // <<< thêm dòng này
        }
        return view;
    }
}
