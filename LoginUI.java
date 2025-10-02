package Default;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI {
    public Scene getScene(Stage stage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 30; -fx-background-color: #2c2c2c;");
        layout.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("Loggin");
        lbl.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Ô nhập username
        TextField txtUser = new TextField();
        txtUser.setPromptText("Tên đăng nhập");

        // Ô nhập password
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Mật khẩu");

        // Nút đăng nhập
        Button btnLogin = new Button("Đăng nhập");

        // Nút đăng ký
        Button btnRegister = new Button("Đăng ký");

        // Label hiển thị thông báo
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: red;");

        // 👉 Xử lý khi bấm nút đăng nhập
        btnLogin.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPass.getText().trim();

            if (username.equals("user") && password.equals("123")) {
                UserUI userUI = new UserUI();
                stage.setScene(userUI.getScene(stage));
            } else if (username.equals("artist") && password.equals("123")) {
                ArtistUI artistUI = new ArtistUI(username);
                stage.setScene(artistUI.getScene(stage));
            } else {
                lblMessage.setText("Sai tên đăng nhập hoặc mật khẩu!");
            }
        });

        // 👉 Xử lý khi bấm nút đăng ký
        btnRegister.setOnAction(e -> {
            RegisterUI registerUI = new RegisterUI();
            stage.setScene(registerUI.getScene(stage));
        });

        layout.getChildren().addAll(lbl, txtUser, txtPass, btnLogin, btnRegister, lblMessage);

        return new Scene(layout, 800, 400);
    }
}
