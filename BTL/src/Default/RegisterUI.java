package Default;

import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

// 👉 Import các UI riêng cho từng thể loại
import Options.VpopUI;
import Options.RockUI;
import Options.RapUI;
import Options.USUKUI;
import Options.OtherUI;
import Default.LoginUI;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class RegisterUI {

    public Scene getScene(Stage stage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 30; -fx-background-color: #2c2c2c;");
        layout.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("Đăng ký tài khoản");
        lbl.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Ô nhập username
        TextField txtUser = new TextField();
        txtUser.setPromptText("Tên đăng nhập");

        // Ô nhập password
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Mật khẩu");

        // Ô nhập xác nhận password
        PasswordField txtConfirm = new PasswordField();
        txtConfirm.setPromptText("Xác nhận mật khẩu");

        // Nút đăng ký
        Button btnRegister = new Button("Đăng ký");

        // Nút quay lại Login
        Button btnBack = new Button("Quay lại");

        // Label hiển thị thông báo
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: red;");

        // 👉 Xử lý đăng ký
        btnRegister.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPass.getText().trim();
            String confirm = txtConfirm.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                lblMessage.setText("Vui lòng nhập đầy đủ thông tin!");
            } else if (!password.equals(confirm)) {
                lblMessage.setText("Mật khẩu xác nhận không khớp!");
            } else {
                lblMessage.setStyle("-fx-text-fill: lightgreen;");
                lblMessage.setText("Đăng ký thành công cho user: " + username);

                // 🚀 Sau này có thể lưu vào database hoặc file
            }
        });

        // 👉 Quay lại Login
        btnBack.setOnAction(e -> {
            LoginUI loginUI = new LoginUI();
            stage.setScene(loginUI.getScene(stage));
        });

        layout.getChildren().addAll(lbl, txtUser, txtPass, txtConfirm, btnRegister, btnBack, lblMessage);

        return new Scene(layout, 800, 400);
    }
}
