package Backend.Controller;

import Backend.Model.User;
import Backend.Service.AuthService;
import Backend.Service.SongService;

/**
 * Singleton quản lý session của app
 * Giữ thông tin user đang đăng nhập và các service
 */
public class SessionController {
    
    private static SessionController instance;
    
    // Services
    private final AuthService authService;
    private final SongService songService;
    
    // Private constructor (Singleton)
    private SessionController() {
        this.authService = new AuthService();
        this.songService = new SongService();
    }
    
    /**
     * Lấy instance duy nhất
     */
    public static SessionController getInstance() {
        if (instance == null) {
            synchronized (SessionController.class) {
                if (instance == null) {
                    instance = new SessionController();
                }
            }
        }
        return instance;
    }
    
    // ============ AUTH METHODS ============
    
    /**
     * Đăng nhập
     */
    public User login(String username, String password) {
        return authService.login(username, password);
    }
    
    /**
     * Đăng ký
     */
    public boolean register(String username, String password, String confirmPassword, String role) {
        return authService.register(username, password, confirmPassword, role);
    }
    
    /**
     * Đăng xuất
     */
    public void logout() {
        authService.logout();
    }
    
    /**
     * Lấy user hiện tại
     */
    public User getCurrentUser() {
        return authService.getCurrentUser();
    }
    
    /**
     * Set user hiện tại (dùng cho bypass)
     */
    public void setCurrentUser(User user) {
        authService.setCurrentUser(user);
    }
    
    /**
     * Kiểm tra đã đăng nhập chưa
     */
    public boolean isLoggedIn() {
        return authService.isLoggedIn();
    }
    
    /**
     * Lấy user ID (tiện dùng)
     */
    public int getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : -1;
    }
    
    // ============ SERVICE ACCESSORS ============
    
    /**
     * Lấy AuthService
     */
    public AuthService getAuthService() {
        return authService;
    }
    
    /**
     * Lấy SongService
     */
    public SongService getSongService() {
        return songService;
    }
    
    // ============ QUICK ACCESS METHODS ============
    
    /**
     * Toggle favourite (cần đăng nhập)
     */
    public boolean toggleFavourite(int songId) {
        if (!isLoggedIn()) {
            System.err.println("Cần đăng nhập để thêm favourite");
            return false;
        }
        return songService.toggleFavourite(getCurrentUserId(), songId);
    }
    
    /**
     * Thêm vào playlist (cần đăng nhập)
     */
    public boolean addToPlaylist(int songId) {
        if (!isLoggedIn()) {
            System.err.println("Cần đăng nhập để thêm vào playlist");
            return false;
        }
        return songService.addToPlaylist(getCurrentUserId(), songId);
    }
    
    /**
     * Xóa khỏi playlist (cần đăng nhập)
     */
    public boolean removeFromPlaylist(int songId) {
        if (!isLoggedIn()) {
            return false;
        }
        return songService.removeFromPlaylist(getCurrentUserId(), songId);
    }
}