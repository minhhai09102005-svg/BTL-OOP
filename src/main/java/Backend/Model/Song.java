package Backend.Model;

import java.time.LocalDate;

public class Song {
    private int id;
    private String songTitle;
    private String artists;
    private Integer albumId;
    private int duration; // tính bằng giây
    private String lyric;
    private LocalDate releaseDate;
    private String filePath; // Đường dẫn file MP3
    
    public Song() {}
    
    public Song(int id, String songTitle, String artists, Integer albumId, 
                int duration, String lyric, LocalDate releaseDate) {
        this.id = id;
        this.songTitle = songTitle;
        this.artists = artists;
        this.albumId = albumId;
        this.duration = duration;
        this.lyric = lyric;
        this.releaseDate = releaseDate;
    }
    
    public Song(int id, String songTitle, String artists, Integer albumId, 
                int duration, String lyric, LocalDate releaseDate, String filePath) {
        this.id = id;
        this.songTitle = songTitle;
        this.artists = artists;
        this.albumId = albumId;
        this.duration = duration;
        this.lyric = lyric;
        this.releaseDate = releaseDate;
        this.filePath = filePath;
    }
    
    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSongTitle() { return songTitle; }
    public void setSongTitle(String songTitle) { this.songTitle = songTitle; }
    
    public String getArtists() { return artists; }
    public void setArtists(String artists) { this.artists = artists; }
    
    public Integer getAlbumId() { return albumId; }
    public void setAlbumId(Integer albumId) { this.albumId = albumId; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    
    public String getLyric() { return lyric; }
    public void setLyric(String lyric) { this.lyric = lyric; }
    
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    /**
     * Chuyển duration (giây) sang format mm:ss
     */
    public String getFormattedDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    @Override
    public String toString() {
        return "Song{id=" + id + ", title='" + songTitle + "', artists='" + artists + 
               "', duration=" + getFormattedDuration() + "}";
    }
}