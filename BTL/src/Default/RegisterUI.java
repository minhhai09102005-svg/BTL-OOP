package Default;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterUI {

    public Scene getScene(Stage stage) {

        // === VBOX chứa toàn bộ form đăng ký ===
        VBox layout = new VBox(10);
        layout.setPrefSize(400, 600);
        layout.setPadding(new Insets(30));
        layout.setStyle("""
            -fx-background-color: rgba(255,255,255,0.12);     /* màu nền mờ trắng */
            -fx-background-radius: 18;                        /* bo góc */
            -fx-border-color: rgba(255,255,255,0.30);         /* viền trắng mờ */
            -fx-border-width: 1;                              /* độ dày viền */
            -fx-border-radius: 18;                            /* bo tròn viền */
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 36, 0.2, 0, 12); /* đổ bóng */
        """);

        // === Tiêu đề ===
        Label lbl = new Label("Register");
        lbl.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(Double.MAX_VALUE);

        // === Ô nhập Username ===
        Label userName = new Label("Username");
        userName.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TextField txtUser = new TextField();
        txtUser.setPrefHeight(34); // giảm chiều cao
        txtUser.setStyle("""
            -fx-font-size: 22px;                             /* cỡ chữ */
            -fx-text-fill: #B0E0E6;                          /* màu chữ xanh nhạt dịu mắt */
            -fx-font-weight: bold;                           /* chữ đậm */
            -fx-background-color: transparent;               /* trong suốt nền */
            -fx-border-color: transparent transparent white transparent; /* chỉ có gạch dưới trắng */
            -fx-border-width: 0 0 1.5 0;
            -fx-focus-color: transparent;                    /* bỏ màu focus mặc định */
            -fx-faint-focus-color: transparent;
            -fx-prompt-text-fill: #BDBDBD;                   /* màu placeholder */
            -fx-padding: 2 0 6 0;                            /* đệm nhỏ */
        """);

        // === Ô nhập Password ===
        Label userPass = new Label("Password");
        userPass.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        PasswordField txtPass = new PasswordField();
        txtPass.setPrefHeight(34);
        txtPass.setStyle("""
            -fx-font-size: 22px;
            -fx-text-fill: #B0E0E6;
            -fx-font-weight: bold;
            -fx-background-color: transparent;
            -fx-border-color: transparent transparent white transparent;
            -fx-border-width: 0 0 1.5 0;
            -fx-focus-color: transparent;
            -fx-faint-focus-color: transparent;
            -fx-prompt-text-fill: #BDBDBD;
            -fx-padding: 2 0 6 0;
        """);

        // === Ô xác nhận mật khẩu ===
        Label confirmPass = new Label("Confirm Password");
        confirmPass.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        PasswordField txtConfirm = new PasswordField();
        txtConfirm.setStyle("""
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

        // === Phần chọn loại tài khoản (Register as: User / Artist) ===
        Label roleLabel = new Label("Register as:");
        roleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Tạo nhóm radio (chỉ chọn được 1 trong 2)
        ToggleGroup roleGroup = new ToggleGroup();

        RadioButton rbUser = new RadioButton("User");
        RadioButton rbArtist = new RadioButton("Artist");

        rbUser.setToggleGroup(roleGroup);
        rbArtist.setToggleGroup(roleGroup);
        rbUser.setSelected(true); // mặc định chọn "User"

        // Style chữ của radio (màu viền & nền sẽ xử lý bằng CSS riêng)
        String radioStyle = """
            -fx-text-fill: #B0E0E6;   /* màu chữ dịu mắt */
            -fx-font-size: 16px;
        """;
        rbUser.setStyle(radioStyle);
        rbArtist.setStyle(radioStyle);

        // Gom label và 2 option lên cùng 1 dòng (HBox)
        HBox roleRow = new HBox(20, roleLabel, rbUser, rbArtist);
        roleRow.setAlignment(Pos.CENTER_LEFT);

        // === Nút Register & Return ===
        Button btnRegister = new Button("Register");
        Button btnBack = new Button("Return");

        btnRegister.setStyle("""
            -fx-background-color: transparent;    /* nền trong suốt */
            -fx-text-fill: white;                 /* chữ trắng */
            -fx-font-size: 18px;                  /* cỡ chữ vừa phải */
            -fx-font-weight: bold;                /* chữ đậm */
            -fx-padding: 10 0 10 0;               /* đệm trên dưới */
            -fx-cursor :hand;
        """);

        btnBack.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 10 0 10 0;
        """);

        btnRegister.setAlignment(Pos.CENTER);
        btnRegister.setMaxWidth(Double.MAX_VALUE);
        btnBack.setAlignment(Pos.CENTER);
        btnBack.setMaxWidth(Double.MAX_VALUE);

        // === Nhãn thông báo lỗi/thành công ===
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: red;");

        // ===== Xử lý nút Register =====
        btnRegister.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPass.getText().trim();
            String confirm = txtConfirm.getText().trim();
            String role = ((RadioButton) roleGroup.getSelectedToggle()).getText().toLowerCase();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                lblMessage.setStyle("-fx-text-fill: #ff6b6b;");
                lblMessage.setText("Vui lòng nhập đầy đủ thông tin!");
                lblMessage.setAlignment(Pos.CENTER);        // căn giữa nội dung trong label
                lblMessage.setMaxWidth(Double.MAX_VALUE);   // label giãn toàn chiều ngang để căn giữa có hiệu lực

            } else if (!password.equals(confirm)) {
                lblMessage.setStyle("-fx-text-fill: #ff6b6b;");
                lblMessage.setText("Mật khẩu xác nhận không khớp!");
            } else {
                lblMessage.setStyle("-fx-text-fill: lightgreen;");
                lblMessage.setText("Đăng ký thành công cho user: " + username + " (" + role + ")");
                // TODO: Sau này lưu DB hoặc file
            }
        });

        // ===== Xử lý nút Return (quay lại Login) =====
        btnBack.setOnAction(e -> {
            LoginUI loginUI = new LoginUI();
            stage.setScene(loginUI.getScene(stage));
        });

        // Thêm toàn bộ phần tử vào layout
        layout.getChildren().addAll(
                lbl,
                userName, txtUser,
                userPass, txtPass,
                confirmPass, txtConfirm,
                roleRow,          // Register as + 2 lựa chọn trên cùng 1 dòng
                btnRegister, btnBack,
                lblMessage
        );

        // === Nền chính (ảnh nền mờ cover toàn màn) ===
        VBox registerSite = new VBox();
        registerSite.setAlignment(Pos.CENTER);
        registerSite.setPadding(new Insets(50));

        // Load ảnh nền
        URL res = getClass().getResource("/image/photo1.png");
        String url = (res != null) ? res.toExternalForm() : "";

        registerSite.setStyle("""
            -fx-background-image: url('%s');
            -fx-background-size: cover;
            -fx-background-position: center center;
            -fx-background-repeat: no-repeat;
        """.formatted(url));

        // Thêm form vào nền
        registerSite.getChildren().add(layout);
        registerSite.setFillWidth(false);
        layout.setMaxWidth(Region.USE_PREF_SIZE);
        layout.setMaxHeight(Region.USE_PREF_SIZE);

        // === Tạo Scene và thêm CSS (đổi màu radio khi chọn) ===
        Scene scene = new Scene(registerSite, 900, 700);
     

        return scene;
    }
}
