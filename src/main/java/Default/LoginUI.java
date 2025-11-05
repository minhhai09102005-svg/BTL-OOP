package Default;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import java.net.URL;

// ⭐ THÊM IMPORTS
import Backend.Controller.SessionController;
import Backend.Model.User;

public class LoginUI {
    
    // ⭐ Thêm SessionController
    private final SessionController session = SessionController.getInstance();
    
    public Scene getScene(Stage stage) {
        // === VBox chứa form đăng nhập ===
        VBox layout = new VBox(10);
        layout.setPrefSize(400, 500);
        layout.setPadding(new Insets(30));
        layout.setStyle("""
        -fx-background-color: rgba(255,255,255,0.12);
        -fx-background-radius: 18;
        -fx-border-color: rgba(255,255,255,0.30);
        -fx-border-width: 1;
        -fx-border-radius: 18;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 36, 0.2, 0, 12);
        """);

        Label lbl = new Label("Login");
        lbl.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(Double.MAX_VALUE);

        TextField txtUser = new TextField();
        Label userName = new Label("Username");
        userName.setStyle("""
            -fx-font-size: 20px;
            -fx-text-fill: white;
        """);

        txtUser.setStyle("""
            -fx-font-size: 22px;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-color: transparent;
            -fx-border-color: transparent transparent white transparent;
            -fx-border-width: 0 0 1.5 0;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-prompt-text-fill: #BDBDBD;
        """);

        Label userPass = new Label("Password");
        userPass.setStyle("""
            -fx-font-size: 20px;
            -fx-text-fill: white;
        """);

        PasswordField txtPass = new PasswordField();
        txtPass.setStyle("""
            -fx-font-size: 22px;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-color: transparent;
            -fx-border-color: transparent transparent white transparent;
            -fx-border-width: 0 0 1.5 0;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-prompt-text-fill: #BDBDBD;
        """);

        Button btnLogin = new Button("Log in");
        btnLogin.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 10 0 0 0;
            -fx-cursor: hand;
        """);
        
        Button btnRegister = new Button("Register");
        btnRegister.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 10 0 0 0;
            -fx-cursor: hand;
        """);
        
        btnLogin.setAlignment(Pos.CENTER);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnRegister.setAlignment(Pos.CENTER);
        btnRegister.setMaxWidth(Double.MAX_VALUE);

        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: red;");

        // ⭐ XỬ LÝ ĐĂNG NHẬP - Dùng database thật
        btnLogin.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPass.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("❌ Vui lòng nhập đầy đủ username và password!");
                lblMessage.setStyle("-fx-font-size: 14; -fx-text-fill: #FF4444;");
                return;
            }

            try {
                // ⭐ Đăng nhập qua database
                User user = session.login(username, password);
                
                if (user != null) {
                    // Đăng nhập thành công
                    lblMessage.setText("✅ Đăng nhập thành công! Đang chuyển...");
                    lblMessage.setStyle("-fx-font-size: 14; -fx-text-fill: #19A34B;");
                    
                    System.out.println("✅ Login thành công: " + user.getUserName());
                    
                    // Chuyển vào UserUI sau 0.5 giây
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            javafx.application.Platform.runLater(() -> {
                                UserUI userUI = new UserUI();
                                stage.setScene(userUI.getScene(stage));
                            });
                        } catch (InterruptedException ex) {}
                    }).start();
                } else {
                    // Đăng nhập thất bại
                    lblMessage.setText("❌ Sai username hoặc password!");
                    lblMessage.setStyle("-fx-font-size: 14; -fx-text-fill: #FF4444;");
                }
                
            } catch (Exception ex) {
                System.err.println("❌ Lỗi đăng nhập: " + ex.getMessage());
                ex.printStackTrace();
                lblMessage.setText("❌ Lỗi kết nối database!");
                lblMessage.setStyle("-fx-font-size: 14; -fx-text-fill: #FF4444;");
            }
        });

        // 👉 Xử lý khi bấm nút đăng ký
        btnRegister.setOnAction(e -> {
            RegisterUI registerUI = new RegisterUI();
            stage.setScene(registerUI.getScene(stage));
        });

        // Thêm tất cả phần tử vào VBox (form)
        layout.getChildren().addAll(
            lbl,
            userName, txtUser,
            userPass, txtPass,
            btnLogin, btnRegister,
            lblMessage
        );

        // === VBox nền chính ===
        VBox loginSite = new VBox();
        loginSite.setAlignment(Pos.CENTER);
        loginSite.setPadding(new Insets(50));

        URL res = getClass().getResource("/image/photo1.png");
        String url = (res != null) ? res.toExternalForm() : "";

        loginSite.setStyle("""
            -fx-background-image: url('%s');
            -fx-background-size: cover;
            -fx-background-position: center center;
            -fx-background-repeat: no-repeat;
            """.formatted(url));

        loginSite.getChildren().add(layout);
        loginSite.setFillWidth(false);
        layout.setMaxWidth(Region.USE_PREF_SIZE);
        layout.setMaxHeight(Region.USE_PREF_SIZE);

        return new Scene(loginSite, 900, 700);
    }
}