package Backend.Database;

import Backend.Model.Album;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {
    
    /**
     * ThÃªm album má»›i
     */
    public int addAlbum(Album album) {
        String sql = "INSERT INTO album (full_name, release_date, artist_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, album.getFullName());
            
            if (album.getReleaseDate() != null) {
                stmt.setDate(2, Date.valueOf(album.getReleaseDate()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            
            if (album.getArtistId() != null) {
                stmt.setInt(3, album.getArtistId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i thÃªm album: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Láº¥y táº¥t cáº£ albums
     */
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM album ORDER BY release_date DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                albums.add(mapResultSetToAlbum(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y albums: " + e.getMessage());
        }
        
        return albums;
    }
    
    /**
     * Láº¥y album theo ID
     */
    public Album getAlbumById(int albumId) {
        String sql = "SELECT * FROM album WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, albumId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAlbum(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y album: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Láº¥y albums cá»§a artist
     */
    public List<Album> getAlbumsByArtist(int artistId) {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM album WHERE artist_id = ? ORDER BY release_date DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, artistId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                albums.add(mapResultSetToAlbum(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Lá»—i láº¥y albums cá»§a artist: " + e.getMessage());
        }
        
        return albums;
    }
    
    /**
     * Cáº­p nháº­t album
     */
    public boolean updateAlbum(Album album) {
        String sql = "UPDATE album SET full_name = ?, release_date = ?, artist_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, album.getFullName());
            
            if (album.getReleaseDate() != null) {
                stmt.setDate(2, Date.valueOf(album.getReleaseDate()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            
            if (album.getArtistId() != null) {
                stmt.setInt(3, album.getArtistId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setInt(4, album.getId());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i cáº­p nháº­t album: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * XÃ³a album
     */
    public boolean deleteAlbum(int albumId) {
        String sql = "DELETE FROM album WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, albumId);
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Lá»—i xÃ³a album: " + e.getMessage());
            return false;
        }
    }
    
    private Album mapResultSetToAlbum(ResultSet rs) throws SQLException {
        Album album = new Album();
        album.setId(rs.getInt("id"));
        album.setFullName(rs.getString("full_name"));
        
        Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) {
            album.setReleaseDate(releaseDate.toLocalDate());
        }
        
        int artistId = rs.getInt("artist_id");
        if (!rs.wasNull()) {
            album.setArtistId(artistId);
        }
        
        return album;
    }
}