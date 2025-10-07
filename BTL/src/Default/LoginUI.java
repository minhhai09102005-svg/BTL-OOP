package Default;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
public class LoginUI {
    
    public Scene getScene(Stage stage) {
        // === VBox chứa form đăng nhập ===
        VBox layout = new VBox(10);
        layout.setPrefSize(400, 550);
        layout.setPadding(new Insets(30));
        layout.setStyle("""
        -fx-background-color: linear-gradient(
        from 0% 0% to 0% 100%,
        #2c2c2c 0%,
        #282828 20%,
        #252525 40%,
        #232323 60%,
        #202020 80%,
        #1e1e1e 100%
        );
        -fx-background-radius: 15;
        """);

        Label lbl = new Label("WELCOME BACK!");
        lbl.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");
        lbl.setAlignment(Pos.CENTER);                 // căn giữa text bên trong Label
        lbl.setMaxWidth(Double.MAX_VALUE);
        TextField txtUser = new TextField();
        
        Label userName = new Label("username");
        userName.setStyle("-fx-font-size: 18px;"+
                          "-fx-text-fill: #9E9E9E; ");
        
        txtUser.setStyle("-fx-font-size: 22px;"+ 
                     "-fx-text-fill: white;"+ 
                     "-fx-font-weight: bold;"+
                     "-fx-background-color: transparent;"+
                     "-fx-border-color: transparent transparent white transparent;");

        Label userPass = new Label("password");
        userPass.setStyle("-fx-font-size: 18px;"+
                          "-fx-text-fill: #9E9E9E;");
        
        PasswordField txtPass = new PasswordField();
        txtPass.setStyle("-fx-font-size: 22px;"+ 
                     "-fx-text-fill: white;"+ 
                     "-fx-font-weight: bold;"+
                     "-fx-background-color: transparent;"+
                     "-fx-border-color: transparent transparent white transparent;");

        Button btnLogin = new Button("Log in");
        Button btnRegister = new Button("Register");
        btnLogin.setStyle("-fx-background-color :transparent;"+
                          "-fx-text-fill: white;"+
                          "-fx-font-size: 18px;"+
                          "-fx-font-weight: 700;"+
                          "-fx-border-color: transparent transparent transparent transparent;"+
                          "-fx-cursor :hand;");
        btnLogin.setAlignment(Pos.CENTER);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setPadding(new Insets(15,0,0,0));
        btnRegister.setStyle("-fx-background-color :transparent;"+
                          "-fx-text-fill: white;"+
                          "-fx-font-size: 18px;"+
                          "-fx-font-weight: 700;"+
                          "-fx-border-color: transparent transparent transparent transparent;"+
                          "-fx-cursor :hand;");
        btnRegister.setAlignment(Pos.CENTER);
        btnRegister.setMaxWidth(Double.MAX_VALUE);
        btnRegister.setPadding(new Insets(15,0,0,0));
        
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

        // Thêm tất cả phần tử vào VBox (form)
        layout.getChildren().addAll(lbl,userName, txtUser,userPass, txtPass, btnLogin, btnRegister, lblMessage);

        // === VBox nền chính (thay StackPane) ===
        VBox loginSite = new VBox();
        loginSite.setAlignment(Pos.CENTER); // ✅ căn giữa form trong VBox
        loginSite.setPadding(new Insets(50)); // ✅ tạo khoảng trống xung quanh
        loginSite.setStyle("""
            -fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #0D1A2A, #325A8D);
        """);
        loginSite.getChildren().add(layout);
        loginSite.setFillWidth(false);                 // 1) VBox cha không kéo giãn width của con
        layout.setMaxWidth(Region.USE_PREF_SIZE);      // 2) khóa maxWidth = pref (400)
        layout.setMaxHeight(Region.USE_PREF_SIZE);

        // ✅ Trả về Scene chính
        return new Scene(loginSite, 1120, 700);
    }
}
