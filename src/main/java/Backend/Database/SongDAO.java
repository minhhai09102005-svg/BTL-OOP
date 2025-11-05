package Backend.Database;

import Backend.Model.Song;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho báº£ng songs
 */
public class SongDAO {
    
    /**
     * ThÃªm bÃ i hÃ¡t má»›i
     */
    public int addSong(Song song) {
        String sql = "INSERT INTO songs (song_title, artists, album_id, duration, lyric, release_date, file_path) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, song.getSongTitle());
            stmt.setString(2, song.getArtists());
            
            if (song.getAlbumId() != null) {
                stmt.setInt(3, song.getAlbumId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setInt(4, song.getDuration());
            stmt.setString(5, song.getLyric());
            
            if (song.getReleaseDate() != null) {
                stmt.setDate(6, Date.valueOf(song.getReleaseDate()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            
            // â­ LÆ°u file_path vÃ o database
            stmt.setString(7, song.getFilePath());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Tráº£ vá» ID vá»«a táº¡o
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm bÃ i hÃ¡t: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Láº¥y táº¥t cáº£ bÃ i hÃ¡t
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs ORDER BY release_date DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y danh sÃ¡ch bÃ i hÃ¡t: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Láº¥y bÃ i hÃ¡t theo ID
     */
    public Song getSongById(int songId) {
        String sql = "SELECT * FROM songs WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, songId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSong(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y bÃ i hÃ¡t: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * TÃ¬m kiáº¿m bÃ i hÃ¡t theo tÃªn
     */
    public List<Song> searchSongsByTitle(String keyword) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE song_title LIKE ? ORDER BY song_title";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i tÃ¬m kiáº¿m bÃ i hÃ¡t: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Láº¥y bÃ i hÃ¡t theo album
     */
    public List<Song> getSongsByAlbum(int albumId) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE album_id = ? ORDER BY release_date";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y bÃ i hÃ¡t theo album: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Láº¥y bÃ i hÃ¡t theo genre
     */
    public List<Song> getSongsByGenre(int genreId) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT s.* FROM songs s " +
                     "INNER JOIN song_genres sg ON s.id = sg.song_id " +
                     "WHERE sg.genre_id = ? ORDER BY s.song_title";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y bÃ i hÃ¡t theo genre: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Cáº­p nháº­t thÃ´ng tin bÃ i hÃ¡t
     */
    public boolean updateSong(Song song) {
        String sql = "UPDATE songs SET song_title = ?, artists = ?, album_id = ?, " +
                     "duration = ?, lyric = ?, release_date = ?, file_path = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, song.getSongTitle());
            stmt.setString(2, song.getArtists());
            
            if (song.getAlbumId() != null) {
                stmt.setInt(3, song.getAlbumId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setInt(4, song.getDuration());
            stmt.setString(5, song.getLyric());
            
            if (song.getReleaseDate() != null) {
                stmt.setDate(6, Date.valueOf(song.getReleaseDate()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            
            stmt.setString(7, song.getFilePath()); // â­ Cáº­p nháº­t file_path
            stmt.setInt(8, song.getId());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i cáº­p nháº­t bÃ i hÃ¡t: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * XÃ³a bÃ i hÃ¡t
     */
    public boolean deleteSong(int songId) {
        String sql = "DELETE FROM songs WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, songId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a bÃ i hÃ¡t: " + e.getMessage());
            return false;
        }
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
        song.setFilePath(rs.getString("file_path")); // â­ Äá»c file_path tá»« database
        
        Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) {
            song.setReleaseDate(releaseDate.toLocalDate());
        }
        
        return song;
    }
    
    /**
     * TÃ¬m bÃ i hÃ¡t theo file_path (Ä‘á»ƒ trÃ¡nh duplicate)
     */
    public Song getSongByFilePath(String filePath) {
        String sql = "SELECT * FROM songs WHERE file_path = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filePath);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSong(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i tÃ¬m bÃ i hÃ¡t theo file_path: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Äáº¿m tá»•ng sá»‘ bÃ i hÃ¡t
     */
    public int getTotalSongs() {
        String sql = "SELECT COUNT(*) FROM songs";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i Ä‘áº¿m bÃ i hÃ¡t: " + e.getMessage());
        }
        
        return 0;
    }
}