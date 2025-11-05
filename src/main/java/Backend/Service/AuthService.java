package Backend.Service;

import Backend.Database.UserDAO;
import Backend.Model.User;
import Utils.LocalStorage;

/**
 * Service xử lý logic đăng nhập/đăng ký
 */
public class AuthService {
    
    private final UserDAO userDAO;
    private User currentUser; // User đang đăng nhập
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Set current user (dùng cho bypass)
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Đăng nhập - Dùng database thật
     * @return User nếu thành công, null nếu thất bại
     */
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("❌ Username không được rỗng");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.err.println("❌ Password không được rỗng");
            return null;
        }
        
        // ⭐ Đăng nhập qua database
        User user = userDAO.login(username.trim(), password);
        
        if (user != null) {
            this.currentUser = user;
            System.out.println("✅ Đăng nhập thành công: " + user.getUserName());
            
            // Lưu username lên máy
            LocalStorage.setLastUsername(username.trim());
        } else {
            System.err.println("❌ Đăng nhập thất bại: Sai username hoặc password");
        }
        
        return user;
    }
    
    /**
     * Đăng ký user mới
     */
    public boolean register(String username, String password, String confirmPassword, String role) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được rỗng");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password không được rỗng");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password xác nhận không khớp");
        }
        
        // Validate role
        if (!role.equals("user") && !role.equals("artist")) {
            role = "user"; // Default
        }
        
        // Kiểm tra username đã tồn tại chưa
        if (userDAO.isUsernameExists(username.trim())) {
            System.out.println("❌ Username đã tồn tại");
            return false;
        }
        
        // TODO: Hash password với BCrypt trước khi lưu
        // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        boolean success = userDAO.register(username.trim(), password, role);
        
        if (success) {
            System.out.println("✅ Đăng ký thành công: " + username);
        }
        
        return success;
    }
    
    /**
     * Đăng xuất
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 Đăng xuất: " + currentUser.getUserName());
            this.currentUser = null;
        }
    }

    /**
     * Lấy username đã đăng nhập gần nhất được lưu trên máy
     */
    public String getLastLocalUsername() {
        return LocalStorage.getLastUsername();
    }
    
    /**
     * Lấy user đang đăng nhập
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Kiểm tra có user đang đăng nhập không
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Kiểm tra user hiện tại có phải artist không
     */
    public boolean isArtist() {
        return currentUser != null && "artist".equals(currentUser.getRole());
    }
    
    /**
     * Thay đổi password
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) {
            throw new IllegalStateException("Chưa đăng nhập");
        }
        
        // Verify old password
        if (!currentUser.getPassword().equals(oldPassword)) {
            System.out.println("❌ Mật khẩu cũ không đúng");
            return false;
        }
        
        // Update password
        currentUser.setPassword(newPassword);
        boolean success = userDAO.updateUser(currentUser);
        
        if (success) {
            System.out.println("✅ Đổi mật khẩu thành công");
        }
        
        return success;
    }
}