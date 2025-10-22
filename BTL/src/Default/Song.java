package Default;

import java.util.Objects;

// Model bài hát + kèm interface điều khiển phát nhạc cho HomeUI/PlayerBar
public class Song {
    // --- dữ liệu chính của 1 bài hát ---
    private String name;
    private String artist;
    private int durationSeconds;
    private String album;
    private String genre;

    private String lyrics; // optional

    //đánh dấu bài hát đã yêu thích hay chưa
    private boolean isFavourite; 

    // --- Hợp đồng hành vi phát nhạc, để UI gọi mà không phụ thuộc lớp cụ thể ---
    public interface PlayerController {
        void play(Song song);   // gọi khi muốn phát 1 bài hát
        default void togglePause() {} // đảo play/pause (tuỳ chọn)
    }

    // --- ctor tiện dụng khi không có lyrics ---
    public Song(String name, String artist, int durationSeconds, String album, String genre) {
        this(name, artist, durationSeconds, album, genre, null);
    }

    // --- ctor chính: nhận đủ trường + validate cơ bản ---
    public Song(String name, String artist, int durationSeconds, String album, String genre, String lyrics) {
        this.name = Objects.requireNonNull(name).trim();     // bắt buộc có name
        this.artist = Objects.requireNonNull(artist).trim(); // bắt buộc có artist
        if (durationSeconds < 0) throw new IllegalArgumentException("durationSeconds >= 0");
        this.durationSeconds = durationSeconds;
        this.album = (album == null) ? "" : album.trim();    // null -> ""
        this.genre = (genre == null) ? "" : genre.trim();    // null -> ""
        this.lyrics = (lyrics == null || lyrics.isBlank()) ? null : lyrics.trim(); // blank -> null
        // isFavourite mặc định là false
        this.isFavourite = false;
    }

    // --- getters/setters cơ bản (đã trim/validate cần thiết) ---
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name blank");
        this.name = name.trim();
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) {
        if (artist == null || artist.isBlank()) throw new IllegalArgumentException("artist blank");
        this.artist = artist.trim();
    }

    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) {
        if (durationSeconds < 0) throw new IllegalArgumentException("durationSeconds >= 0");
        this.durationSeconds = durationSeconds;
    }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = (album == null) ? "" : album.trim(); }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = (genre == null) ? "" : genre.trim(); }

    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = (lyrics == null || lyrics.isBlank()) ? null : lyrics.trim(); }

    // [ADDED] getter/setter cho isFavourite
    public boolean isFavourite() { return isFavourite; }
    public void setFavourite(boolean favourite) { this.isFavourite = favourite; }

    // --- tiện ích: đổi giây -> "mm:ss" để hiển thị ---
    public String getFormattedDuration() {
        int m = durationSeconds / 60, s = durationSeconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    // --- để log/debug hiển thị gọn ---
    @Override public String toString() {
        return name + " — " + artist + " (" + getFormattedDuration() + ")"
               + (genre.isEmpty() ? "" : " [" + genre + "]");
    }
}
