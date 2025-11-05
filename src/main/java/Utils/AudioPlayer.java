package Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AudioPlayer {
    private MediaPlayer mediaPlayer;
    private String currentPath;

    public void load(String filePath) {
        this.currentPath = filePath;
        
        // Stop current player if playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("❌ File path rỗng!");
            return;
        }
        
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("❌ File không tồn tại: " + filePath);
                return;
            }
            
            // Convert to URI
            String uri = file.toURI().toString();
            System.out.println("📂 Loading file: " + uri);
            
            Media media = new Media(uri);
            
            mediaPlayer = new MediaPlayer(media);
            
            // ⭐ Đảm bảo phát qua thiết bị ngoại vi (headphones/loa)
            // JavaFX MediaPlayer tự động phát qua thiết bị audio mặc định của hệ thống
            // Không cần cấu hình thêm, nhưng có thể log để xác nhận
            System.out.println("🔊 Audio sẽ phát qua thiết bị mặc định (headphones/loa)");
            
            // Xử lý lỗi MediaPlayer
            mediaPlayer.setOnError(() -> {
                if (mediaPlayer.getError() != null) {
                    System.err.println("❌ MediaPlayer error: " + mediaPlayer.getError().getMessage());
                    mediaPlayer.getError().printStackTrace();
                }
            });
            
            // Log khi Media ready
            mediaPlayer.setOnReady(() -> {
                System.out.println("✅ Media ready: " + filePath);
                if (media.getDuration() != null && !media.getDuration().isUnknown()) {
                    System.out.println("   Duration: " + (int)Math.round(media.getDuration().toSeconds()) + " giây");
                }
                System.out.println("✅ Sẵn sàng phát qua thiết bị audio");
            });
            
            // Auto-play khi ready (nếu cần)
            // Được xử lý trong play() method
            
            System.out.println("✅ Đã load file: " + filePath);
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi load file: " + e.getMessage());
            e.printStackTrace();
            mediaPlayer = null;
        }
    }

    public void play() {
        if (mediaPlayer == null) {
            System.err.println("⚠️ MediaPlayer chưa được khởi tạo!");
            if (currentPath != null) {
                System.out.println("🔄 Đang thử load lại...");
                load(currentPath);
            }
            return;
        }
        
        try {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            System.out.println("📊 MediaPlayer status: " + status);
            
            // Kiểm tra MediaPlayer có sẵn sàng không
            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                System.err.println("⚠️ MediaPlayer chưa ready, đợi...");
                // Đợi MediaPlayer ready
                mediaPlayer.setOnReady(() -> {
                    System.out.println("✅ MediaPlayer đã ready, bắt đầu phát...");
                    mediaPlayer.play();
                });
                return;
            }
            
            // Nếu đã ready hoặc đang paused/stopped, phát ngay
            if (status == MediaPlayer.Status.READY || 
                status == MediaPlayer.Status.PAUSED ||
                status == MediaPlayer.Status.STOPPED) {
                mediaPlayer.play();
                System.out.println("▶️ Đang phát nhạc (status: " + status + ")");
            } else if (status == MediaPlayer.Status.PLAYING) {
                System.out.println("ℹ️ Nhạc đang phát rồi");
            } else {
                // Các trạng thái khác, đợi ready
                System.out.println("⏳ Đợi MediaPlayer ready...");
                mediaPlayer.setOnReady(() -> {
                    mediaPlayer.play();
                    System.out.println("▶️ Đang phát nhạc (sau khi ready)");
                });
            }
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi phát nhạc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            System.out.println("⏸ Đã tạm dừng");
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            // Tham khảo MasterClass.Stop() - reset về đầu khi stop
            mediaPlayer.seek(mediaPlayer.getStartTime());
            System.out.println("⏹ Đã dừng và reset về đầu");
        }
    }
    
    /**
     * Tham khảo MasterClass: Lấy current time để resume
     */
    public double getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime().toSeconds();
        }
        return 0.0;
    }
    
    /**
     * Tham khảo MasterClass: Seek đến vị trí (dùng cho resume)
     */
    public void seek(double seconds) {
        if (mediaPlayer != null && mediaPlayer.getMedia() != null) {
            javafx.util.Duration seekTime = javafx.util.Duration.seconds(seconds);
            mediaPlayer.seek(seekTime);
            System.out.println("⏩ Seek đến: " + seconds + " giây");
        }
    }
    
    /**
     * Lấy total duration (giây)
     */
    public double getTotalDuration() {
        if (mediaPlayer != null && mediaPlayer.getMedia() != null) {
            javafx.util.Duration duration = mediaPlayer.getMedia().getDuration();
            if (duration != null && !duration.isUnknown()) {
                return duration.toSeconds();
            }
        }
        return 0.0;
    }
    
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
    
    /**
     * Kiểm tra nhạc đã phát xong chưa (tham khảo MasterClass dòng 61)
     */
    public boolean isComplete() {
        if (mediaPlayer != null) {
            return mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED && 
                   getCurrentTime() >= getTotalDuration() - 0.5; // Cho phép sai số 0.5s
        }
        return false;
    }
}


