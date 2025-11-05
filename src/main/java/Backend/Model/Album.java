package Backend.Model;

import java.time.LocalDate;

public class Album {
    private int id;
    private String fullName;
    private LocalDate releaseDate;
    private Integer artistId; // Foreign key to user
    
    public Album() {}
    
    public Album(int id, String fullName, LocalDate releaseDate, Integer artistId) {
        this.id = id;
        this.fullName = fullName;
        this.releaseDate = releaseDate;
        this.artistId = artistId;
    }
    
    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    
    public Integer getArtistId() { return artistId; }
    public void setArtistId(Integer artistId) { this.artistId = artistId; }
    
    @Override
    public String toString() {
        return "Album{id=" + id + ", name='" + fullName + "'}";
    }
}