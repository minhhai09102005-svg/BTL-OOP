package Backend.Database;

import Backend.Model.Song;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho báº£ng favourite
 */
public class FavouriteDAO {
    
    private final SongDAO songDAO;
    
    public FavouriteDAO() {
        this.songDAO = new SongDAO();
    }
    
    /**
     * ThÃªm bÃ i hÃ¡t vÃ o favourite
     */
    public boolean addFavourite(int userId, int songId) {
        // Kiá»ƒm tra Ä‘Ã£ tá»“n táº¡i chÆ°a
        if (isFavourite(userId, songId)) {
            return true; // ÄÃ£ cÃ³ rá»“i
        }
        
        // Láº¥y file_path tá»« song
        String filePath = null;
        try {
            Song song = songDAO.getSongById(songId);
            if (song != null) {
                filePath = song.getFilePath();
            }
        } catch (Exception e) {
            System.err.println("Lá»—i láº¥y file_path tá»« song: " + e.getMessage());
        }
        
        String sql = "INSERT INTO favourite (user_id, song_id, file_path) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            stmt.setString(3, filePath);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm favourite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * ThÃªm bÃ i hÃ¡t vÃ o favourite vá»›i file_path trá»±c tiáº¿p
     */
    public boolean addFavourite(int userId, int songId, String filePath) {
        // Kiá»ƒm tra Ä‘Ã£ tá»“n táº¡i chÆ°a
        if (isFavourite(userId, songId)) {
            return true; // ÄÃ£ cÃ³ rá»“i
        }
        
        String sql = "INSERT INTO favourite (user_id, song_id, file_path) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            stmt.setString(3, filePath);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm favourite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * XÃ³a bÃ i hÃ¡t khá»i favourite
     */
    public boolean removeFavourite(int userId, int songId) {
        String sql = "DELETE FROM favourite WHERE user_id = ? AND song_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a favourite: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Kiá»ƒm tra bÃ i hÃ¡t cÃ³ trong favourite khÃ´ng
     */
    public boolean isFavourite(int userId, int songId) {
        String sql = "SELECT COUNT(*) FROM favourite WHERE user_id = ? AND song_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i kiá»ƒm tra favourite: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Láº¥y táº¥t cáº£ bÃ i hÃ¡t favourite cá»§a user
     */
    public List<Song> getFavouriteSongs(int userId) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT s.* FROM songs s " +
                     "INNER JOIN favourite f ON s.id = f.song_id " +
                     "WHERE f.user_id = ? " +
                     "ORDER BY s.song_title";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y danh sÃ¡ch favourite: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Láº¥y ID táº¥t cáº£ bÃ i hÃ¡t favourite (dÃ¹ng cho sync UI)
     */
    public List<Integer> getFavouriteSongIds(int userId) {
        List<Integer> songIds = new ArrayList<>();
        String sql = "SELECT song_id FROM favourite WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                songIds.add(rs.getInt("song_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y favourite IDs: " + e.getMessage());
        }
        
        return songIds;
    }
    
    /**
     * XÃ³a táº¥t cáº£ favourite cá»§a user
     */
    public boolean clearFavourites(int userId) {
        String sql = "DELETE FROM favourite WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a favourite: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Äáº¿m sá»‘ lÆ°á»£ng favourite cá»§a user
     */
    public int countFavourites(int userId) {
        String sql = "SELECT COUNT(*) FROM favourite WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i Ä‘áº¿m favourite: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Map ResultSet sang Song object
     */
    private Song mapResultSetToSong(ResultSet rs) throws SQLException {
        Song song = new Song();
        song.setId(rs.getInt("id"));
        song.setSongTitle(rs.getString("song_title"));
        song.setArtists(rs.getString("artists"));
        song.setAlbumId(rs.getInt("album_id"));
        song.setDuration(rs.getInt("duration"));
        song.setLyric(rs.getString("lyric"));
        song.setFilePath(rs.getString("file_path")); // â­ Äáº£m báº£o láº¥y file_path
        
        Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) {
            song.setReleaseDate(releaseDate.toLocalDate());
        }
        
        return song;
    }
}