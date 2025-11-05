package Backend.Database;

import Backend.Model.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    
    /**
     * ThÃªm genre má»›i
     */
    public int addGenre(String name) {
        String sql = "INSERT INTO genres (name) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm genre: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Láº¥y táº¥t cáº£ genres
     */
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                genres.add(new Genre(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y genres: " + e.getMessage());
        }
        
        return genres;
    }
    
    /**
     * Láº¥y genre theo ID
     */
    public Genre getGenreById(int genreId) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Genre(
                    rs.getInt("id"),
                    rs.getString("name")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y genre: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * GÃ¡n genre cho bÃ i hÃ¡t
     */
    public boolean addSongGenre(int songId, int genreId) {
        String sql = "INSERT INTO song_genres (song_id, genre_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, songId);
            stmt.setInt(2, genreId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm song_genre: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Láº¥y genres cá»§a 1 bÃ i hÃ¡t
     */
    public List<Genre> getSongGenres(int songId) {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.* FROM genres g " +
                     "INNER JOIN song_genres sg ON g.id = sg.genre_id " +
                     "WHERE sg.song_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, songId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                genres.add(new Genre(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y song genres: " + e.getMessage());
        }
        
        return genres;
    }
    
    /**
     * XÃ³a genre
     */
    public boolean deleteGenre(int genreId) {
        String sql = "DELETE FROM genres WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genreId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a genre: " + e.getMessage());
            return false;
        }
    }
}