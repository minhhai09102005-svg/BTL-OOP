# Music Player Application

Ứng dụng Music Player được xây dựng bằng JavaFX với kết nối MySQL database.

## Yêu cầu hệ thống

- Các phiên bản dưới đây đảm bảo JavaFX hoạt động ổn định và kết nối được MySQL.
- Java 17 hoặc cao hơn
- MySQL Server 8.0 hoặc cao hơn
- Maven 3.6+ (để build project)

## Cài đặt và Chạy

### 1. Cài đặt Database

Mục tiêu: tạo schema `music_app` và cấu hình thông số kết nối cho ứng dụng.
1. **Khởi động MySQL Server:**
   - Chạy dịch vụ MySQL80 trước khi app kết nối tới DB.
   ```bash
   # Windows (với quyền Admin)
   net start MySQL80
   
   # Hoặc mở Services (Win+R → services.msc) → Tìm MySQL80 → Start
   ```

2. **Tạo database:**
   - Lệnh dưới đây tạo toàn bộ bảng/khóa theo file `music_app.sql`.
   ```bash
   mysql -u root -p < database/music_app.sql
   ```
   
   Hoặc mở MySQL Workbench và chạy file `database/music_app.sql`

3. **Cấu hình kết nối database:**
   - Cập nhật user/password đúng với cài đặt MySQL của bạn.
   
   Mở file `src/main/resources/database.properties` và cập nhật:
   ```properties
   db.url=jdbc:mysql://localhost:3306/music_app?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   db.username=root
   db.password=your_password_here
   ```

### 2. Build và Chạy ứng dụng

Bạn có thể chạy trực tiếp từ Maven, đóng gói thành JAR, hoặc chạy trong IDE.
#### Cách 1: Chạy trực tiếp với Maven
Nhanh nhất khi phát triển, Maven sẽ tự tải dependencies và khởi chạy JavaFX.
```bash
# Build project
mvn clean compile

# Chạy ứng dụng
mvn javafx:run
```

#### Cách 2: Tạo JAR và chạy
Phù hợp khi cần chạy phân phối; JAR đã kèm dependencies (shaded).
```bash
# Build JAR file
mvn clean package

# Chạy JAR (khuyến nghị dùng shaded JAR)
java --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/MusicPlayer-1.0.0-shaded.jar
```

#### Cách 3: Chạy từ IDE (IntelliJ IDEA / Eclipse / VS Code)
Tiện cho debug/đặt breakpoint; chỉ cần trỏ tới `MAIN.Main` là chạy.

1. **IntelliJ IDEA:**
   - Mở project
   - File → Project Structure → Project → SDK: Java 17
   - Run → Edit Configurations → Add New → Application
   - Main class: `MAIN.Main`
   - VM options: `--module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml`
   - Chạy `Main.java`

2. **Eclipse:**
   - File → Import → Existing Maven Projects
   - Run → Run Configurations → Java Application
   - Main class: `MAIN.Main`

3. **VS Code:**
   - Cài extension: Java Extension Pack, Extension Pack for Java
   - Mở file `src/main/java/MAIN/Main.java`
   - Nhấn F5 hoặc Run → Run Without Debugging

### 3. Kiểm tra kết nối Database

Nếu app báo lỗi DB, kiểm tra lần lượt các bước sau:
Nếu gặp lỗi kết nối database, kiểm tra:

1. **MySQL Server đã chạy chưa:**
   ```bash
   # Windows
   net start MySQL80
   
   # Kiểm tra status
   sc query MySQL80
   ```

2. **Database đã được tạo chưa:**
   ```bash
   mysql -u root -p
   SHOW DATABASES;
   # Phải thấy database 'music_app'
   ```

3. **Username/Password trong database.properties đúng chưa**

## Cấu trúc Project

Sơ đồ nhanh các thư mục/chức năng chính của dự án.
```
MusicPlayer/
├── src/main/java/
│   ├── MAIN/              # Main class
│   ├── Backend/           # Business logic
│   │   ├── Controller/    # Controllers
│   │   ├── Database/      # DAO classes
│   │   ├── Model/         # Entity classes
│   │   └── Service/       # Service classes
│   ├── Default/           # Login/Register UI
│   └── UserUI_Components/ # Main UI components
├── src/main/resources/
│   ├── database.properties # Database config
│   ├── config.properties   # App config
│   └── image/              # Images
├── database/               # SQL scripts
└── pom.xml                # Maven config
```

## Troubleshooting

Một số lỗi phổ biến và cách khắc phục nhanh.
### Lỗi: "No module named javafx.controls"
- **Giải pháp:** Đảm bảo JavaFX đã được thêm vào classpath hoặc sử dụng `--module-path` và `--add-modules` (bao gồm `javafx.media`).
  - Với JAR: thêm `--module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.media`.

### Chạy không cần cài Maven (Maven Wrapper)
```bash
# Tạo wrapper 1 lần (cần Maven lần đầu)
mvn -N io.takari:maven:wrapper

# Chạy ứng dụng về sau (không cần Maven global)
./mvnw javafx:run
```

### Lỗi: "Connection refused" hoặc "Communications link failure"
- **Giải pháp:** 
  - Kiểm tra MySQL Server đã chạy: `net start MySQL80`
  - Kiểm tra port 3306 có bị chặn không

### Lỗi: "Unknown database 'music_app'"
- **Giải pháp:** Chạy script SQL để tạo database: `mysql -u root -p < database/music_app.sql`

### Lỗi: "Access denied for user"
- **Giải pháp:** Kiểm tra lại username/password trong `database.properties`

## Tác giả

120 An Liễng

## License

MIT License — bạn có thể sử dụng/sửa đổi/mở rộng cho mục đích học tập & dự án cá nhân.
