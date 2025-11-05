package Backend.Service;

import Backend.Database.SongDAO;
import Backend.Database.FavouriteDAO;
import Backend.Database.PlaylistDAO;
import Backend.Model.Song;
import java.util.List;

/**
 * Service xử lý logic liên quan đến bài hát
 */
public class SongService {
    
    private final SongDAO songDAO;
    private final FavouriteDAO favouriteDAO;
    private final PlaylistDAO playlistDAO;
    
    public SongService() {
        this.songDAO = new SongDAO();
        this.favouriteDAO = new FavouriteDAO();
        this.playlistDAO = new PlaylistDAO();
    }
    
    // ============ SONG OPERATIONS ============
    
    /**
     * Lấy tất cả bài hát
     */
    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }
    
    /**
     * Tìm kiếm bài hát
     */
    public List<Song> searchSongs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSongs();
        }
        return songDAO.searchSongsByTitle(keyword.trim());
    }
    
    /**
     * Lấy bài hát theo ID
     */
    public Song getSongById(int songId) {
        return songDAO.getSongById(songId);
    }
    
    /**
     * Lấy bài hát theo album
     */
    public List<Song> getSongsByAlbum(int albumId) {
        return songDAO.getSongsByAlbum(albumId);
    }
    
    /**
     * Lấy bài hát theo genre
     */
    public List<Song> getSongsByGenre(int genreId) {
        return songDAO.getSongsByGenre(genreId);
    }
    
    /**
     * Thêm bài hát mới (artist only)
     */
    public int addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song không được null");
        }
        if (song.getSongTitle() == null || song.getSongTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên bài hát không được rỗng");
        }
        
        return songDAO.addSong(song);
    }
    
    /**
     * Cập nhật thông tin bài hát
     */
    public boolean updateSong(Song song) {
        if (song == null || song.getId() <= 0) {
            throw new IllegalArgumentException("Song không hợp lệ");
        }
        
        return songDAO.updateSong(song);
    }
    
    /**
     * Xóa bài hát
     */
    public boolean deleteSong(int songId) {
        return songDAO.deleteSong(songId);
    }
    
    // ============ FAVOURITE OPERATIONS ============
    
    /**
     * Thêm/xóa favourite (toggle)
     */
    public boolean toggleFavourite(int userId, int songId) {
        if (favouriteDAO.isFavourite(userId, songId)) {
            return favouriteDAO.removeFavourite(userId, songId);
        } else {
            return favouriteDAO.addFavourite(userId, songId);
        }
    }
    
    /**
     * Lấy danh sách favourite
     */
    public List<Song> getFavouriteSongs(int userId) {
        return favouriteDAO.getFavouriteSongs(userId);
    }
    
    /**
     * Kiểm tra bài hát có trong favourite không
     */
    public boolean isFavourite(int userId, int songId) {
        return favouriteDAO.isFavourite(userId, songId);
    }
    
    /**
     * Xóa tất cả favourite
     */
    public boolean clearFavourites(int userId) {
        return favouriteDAO.clearFavourites(userId);
    }
    
    // ============ PLAYLIST OPERATIONS ============
    
    /**
     * Thêm bài hát vào playlist
     */
    public boolean addToPlaylist(int userId, int songId) {
        return playlistDAO.addToPlaylist(userId, songId);
    }
    
    /**
     * Xóa bài hát khỏi playlist
     */
    public boolean removeFromPlaylist(int userId, int songId) {
        return playlistDAO.removeFromPlaylist(userId, songId);
    }
    
    /**
     * Lấy danh sách playlist
     */
    public List<Song> getPlaylistSongs(int userId) {
        return playlistDAO.getPlaylistSongs(userId);
    }
    
    /**
     * Kiểm tra bài hát có trong playlist không
     */
    public boolean isInPlaylist(int userId, int songId) {
        return playlistDAO.isInPlaylist(userId, songId);
    }
    
    /**
     * Xóa tất cả playlist
     */
    public boolean clearPlaylist(int userId) {
        return playlistDAO.clearPlaylist(userId);
    }
    
    // ============ STATISTICS ============
    
    /**
     * Đếm tổng số bài hát
     */
    public int getTotalSongs() {
        return songDAO.getTotalSongs();
    }
    
    /**
     * Đếm số favourite
     */
    public int getFavouriteCount(int userId) {
        return favouriteDAO.countFavourites(userId);
    }
    
    /**
     * Đếm số bài trong playlist
     */
    public int getPlaylistCount(int userId) {
        return playlistDAO.countPlaylistSongs(userId);
    }
}