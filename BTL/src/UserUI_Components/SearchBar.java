package UserUI_Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

///**
// * SearchBar: thanh tìm kiếm độc lập để gắn vào UserUI.
// */
public class SearchBar extends HBox {

    // Ô nhập nội dung tìm kiếm
    private final TextField input = new TextField();

    // Nút bấm tìm (icon kính lúp)
    private final Button btn = new Button("🔍");

    // Callback do bên ngoài truyền vào; được gọi khi ấn Enter / click nút
    private Consumer<String> onSearch;

    public SearchBar() {
        // Layout & khung nền của thanh tìm kiếm
        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(8, 12, 8, 12));
        setStyle("-fx-background-color:#1F2937; -fx-background-radius:8;");

        // Cấu hình ô nhập: placeholder, chiều cao, màu sắc
        input.setPromptText("Search songs, artists, albums…");
        input.setPrefHeight(36);
        input.setStyle(
            "-fx-background-color:#111827;" +
            "-fx-text-fill:#E5E7EB;" +
            "-fx-prompt-text-fill:#9CA3AF;" +
            "-fx-background-radius:6;"
        );
        // Cho input giãn chiếm hết phần còn lại trong HBox
        HBox.setHgrow(input, Priority.ALWAYS);

        // Nút tìm: chiều cao khớp input, style tối
        btn.setPrefHeight(36);
        btn.setStyle(
            "-fx-background-color:#374151;" +
            "-fx-text-fill:white;" +
            "-fx-font-weight:700;" +
            "-fx-background-radius:6;" +
            "-fx-cursor: hand;"
        );
        
        // style mặc định + hover (giống mkPrimary nhưng tông xám)
        final String normalBtn =
            "-fx-background-color:#374151;" +   // bình thường
            "-fx-text-fill:white;" +
            "-fx-font-weight:700;" +
            "-fx-background-radius:6;" +
            "-fx-cursor: hand;";
        final String hoverBtn  =
            "-fx-background-color:#4B5563;" +   // hover đậm hơn
            "-fx-text-fill:white;" +
            "-fx-font-weight:700;" +
            "-fx-background-radius:6;" +
            "-fx-cursor: hand;";

        // áp style mặc định
        btn.setStyle(normalBtn);

        // hover giống mkPrimary
        btn.setOnMouseEntered(e -> btn.setStyle(hoverBtn));
        btn.setOnMouseExited (e -> btn.setStyle(normalBtn));


        // Hành vi: click nút -> gọi trigger()
        btn.setOnAction(e -> trigger());
        // Nhấn Enter trong ô input -> gọi trigger()
        input.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) trigger(); });

        // Thêm control vào HBox
        getChildren().addAll(input, btn);
    }

//    /**
//     * Gọi callback onSearch (nếu đã gán) với chuỗi query đã trim.
//     * Dùng chung cho cả click nút lẫn nhấn Enter.
//     */
    private void trigger() {
        if (onSearch != null) onSearch.accept(input.getText().trim());
    }

//    /**
//     * Gán hàm xử lý khi người dùng thực hiện tìm kiếm.
//     * @param cb Consumer nhận chuỗi query
//     */
    public void setOnSearch(Consumer<String> cb) { this.onSearch = cb; }

//    // ---- Tiện ích (optional) ------------------------------------------------
//
//    /** Lấy text hiện tại trong ô tìm kiếm. */
    public String getText() { return input.getText(); }

//    /** Set text cho ô tìm kiếm (ví dụ: prefill từ lịch sử). */
    public void setText(String s) { input.setText(s); }
}
