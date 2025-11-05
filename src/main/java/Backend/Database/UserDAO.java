package Backend.Database;

import Backend.Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho báº£ng user
 * Xá»­ lÃ½ má»i thao tÃ¡c CRUD vá»›i database
 */
public class UserDAO {
    
    private final DatabaseConnection db;
    
    public UserDAO() {
        this.db = DatabaseConnection.getInstance();
    }
    
    /**
     * ÄÄƒng kÃ½ user má»›i
     */
    public boolean register(String username, String password, String role) {
        String sql = "INSERT INTO user (user_name, password, role) VALUES (?, ?, ?)";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // TODO: hash password vá»›i BCrypt
            stmt.setString(3, role);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i Ä‘Äƒng kÃ½ user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * ÄÄƒng nháº­p - tráº£ vá» User náº¿u thÃ nh cÃ´ng
     */
    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE user_name = ? AND password = ?";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // TODO: verify hash password
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i Ä‘Äƒng nháº­p: " + e.getMessage());
        }
        
        return null; // ÄÄƒng nháº­p tháº¥t báº¡i
    }
    
    /**
     * Kiá»ƒm tra username Ä‘Ã£ tá»“n táº¡i chÆ°a
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_name = ?";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(sql) : null) {
            if (connection == null || stmt == null) {
                System.err.println("KhÃ´ng thá»ƒ káº¿t ná»‘i database Ä‘á»ƒ kiá»ƒm tra username");
                return false;
            }
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i kiá»ƒm tra username: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Láº¥y User theo ID
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE id = ?";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(sql) : null) {
            if (connection == null || stmt == null) {
                System.err.println("KhÃ´ng thá»ƒ káº¿t ná»‘i database Ä‘á»ƒ láº¥y user theo id");
                return null;
            }
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y user: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Cáº­p nháº­t thÃ´ng tin user
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET user_name = ?, password = ?, role = ? WHERE id = ?";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(sql) : null) {
            if (connection == null || stmt == null) {
                System.err.println("KhÃ´ng thá»ƒ káº¿t ná»‘i database Ä‘á»ƒ cáº­p nháº­t user");
                return false;
            }
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i cáº­p nháº­t user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * XÃ³a user
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE id = ?";
        
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(sql) : null) {
            if (connection == null || stmt == null) {
                System.err.println("KhÃ´ng thá»ƒ káº¿t ná»‘i database Ä‘á»ƒ xoÃ¡ user");
                return false;
            }
            stmt.setInt(1, userId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Láº¥y táº¥t cáº£ users (admin only)
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        
        try (Connection connection = db.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y danh sÃ¡ch user: " + e.getMessage());
        }
        
        return users;
    }
}