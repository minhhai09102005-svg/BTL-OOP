package Utils;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Đọc metadata từ file MP3 - Tham khảo Java-Swing-MusicPlayer-main/src/Song.java
 * Sử dụng mp3agic để đọc metadata (ID3v1 và ID3v2)
 */
public class MetadataExtractor {
    
    /**
     * Extract metadata từ file MP3 - Tham khảo Song.java dòng 18-41
     * Trả về Map chứa: title, artist, album, genre, duration
     */
    public Map<String, String> extract(String audioFilePath) {
        Map<String, String> meta = new HashMap<>();
        
        try {
            File file = new File(audioFilePath);
            if (!file.exists()) {
                System.err.println("❌ File không tồn tại: " + audioFilePath);
                return meta;
            }
            
            // ⭐ Tham khảo Song.java dòng 21: Dùng mp3agic để đọc thông tin cơ bản
            Mp3File mp3File = new Mp3File(audioFilePath);
            
            // ⭐ Đọc duration từ mp3agic (tham khảo Song.java dòng 43-49)
            long lengthInSeconds = mp3File.getLengthInSeconds();
            if (lengthInSeconds > 0) {
                meta.put("duration", String.valueOf(lengthInSeconds));
                
                // Format duration như Song.java: "MM:SS"
                long minutes = lengthInSeconds / 60;
                long seconds = lengthInSeconds % 60;
                String formattedTime = String.format("%02d:%02d", minutes, seconds);
                meta.put("duration_formatted", formattedTime);
            }
            
            // ⭐ Đọc metadata từ ID3 tags (mp3agic có thể đọc ID3v1 và ID3v2)
            // Ưu tiên ID3v2 (chi tiết hơn), fallback về ID3v1
            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                
                // Đọc title
                String title = id3v2Tag.getTitle();
                if (title != null && !title.trim().isEmpty()) {
                    meta.put("title", title.trim());
                }
                
                // Đọc artist
                String artist = id3v2Tag.getArtist();
                if (artist != null && !artist.trim().isEmpty()) {
                    meta.put("artist", artist.trim());
                }
                
                // Đọc album
                String album = id3v2Tag.getAlbum();
                if (album != null && !album.trim().isEmpty()) {
                    meta.put("album", album.trim());
                }
                
                // Đọc genre
                String genre = id3v2Tag.getGenreDescription();
                if (genre != null && !genre.trim().isEmpty()) {
                    meta.put("genre", genre.trim());
                }
                
            } else if (mp3File.hasId3v1Tag()) {
                // Fallback về ID3v1 (ít thông tin hơn)
                ID3v1 id3v1Tag = mp3File.getId3v1Tag();
                
                String title = id3v1Tag.getTitle();
                if (title != null && !title.trim().isEmpty()) {
                    meta.put("title", title.trim());
                }
                
                String artist = id3v1Tag.getArtist();
                if (artist != null && !artist.trim().isEmpty()) {
                    meta.put("artist", artist.trim());
                }
                
                String album = id3v1Tag.getAlbum();
                if (album != null && !album.trim().isEmpty()) {
                    meta.put("album", album.trim());
                }
                
                String genre = id3v1Tag.getGenreDescription();
                if (genre != null && !genre.trim().isEmpty()) {
                    meta.put("genre", genre.trim());
                }
            } else {
                // Không có ID3 tag (tham khảo Song.java dòng 33-36)
                System.out.println("⚠️ File MP3 không có ID3 tag metadata");
            }
            
            System.out.println("✅ Đã đọc metadata từ MP3 (mp3agic):");
            System.out.println("   Title: " + meta.getOrDefault("title", "N/A"));
            System.out.println("   Artist: " + meta.getOrDefault("artist", "N/A"));
            System.out.println("   Album: " + meta.getOrDefault("album", "N/A"));
            System.out.println("   Genre: " + meta.getOrDefault("genre", "N/A"));
            System.out.println("   Duration: " + meta.getOrDefault("duration_formatted", "N/A"));
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi extract metadata: " + e.getMessage());
            e.printStackTrace();
        }
        
        return meta;
    }
}


