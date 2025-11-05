package Utils;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.util.List;

/**
 * Utility cho FileChooser - Tham khảo từ Music-Player-master
 * Cải thiện: Lưu last location để mở lại folder cũ
 */
public class FileChooserUtil {
    private static String lastLocation = null; // Lưu last location như code tham khảo
    
    /**
     * Mở FileChooser để chọn file audio (JavaFX)
     * Tham khảo: MainGui.java - lưu last location
     */
    public static List<File> chooseAudioFiles(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file nhạc");
        
        // ⭐ Lưu last location như code tham khảo
        if (lastLocation != null) {
            File lastDir = new File(lastLocation);
            if (lastDir.exists() && lastDir.isDirectory()) {
                fileChooser.setInitialDirectory(lastDir);
            }
        } else {
            // Mặc định mở Music folder (Windows)
            String userHome = System.getProperty("user.home");
            File musicFolder = new File(userHome, "Music");
            if (musicFolder.exists()) {
                fileChooser.setInitialDirectory(musicFolder);
            }
        }
        
        // Lọc chỉ file audio - Tham khảo: MainGui.java dòng 100
        FileChooser.ExtensionFilter audioFilter = new FileChooser.ExtensionFilter(
            "MP3 Files", 
            "*.mp3", "*.mpeg3"
        );
        fileChooser.getExtensionFilters().add(audioFilter);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
            "Audio Files", 
            "*.mp3", "*.wav", "*.m4a", "*.aac", "*.flac", "*.ogg", "*.wma"
        ));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.setSelectedExtensionFilter(audioFilter);
        
        // Cho phép chọn nhiều file
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(ownerWindow);
        
        // ⭐ Lưu last location sau khi chọn file
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            File firstFile = selectedFiles.get(0);
            if (firstFile.getParent() != null) {
                lastLocation = firstFile.getParent();
                System.out.println("💾 Đã lưu last location: " + lastLocation);
            }
        }
        
        return selectedFiles;
    }
    
    /**
     * Chọn một file audio
     */
    public static File chooseAudioFile(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file nhạc");
        
        // ⭐ Lưu last location
        if (lastLocation != null) {
            File lastDir = new File(lastLocation);
            if (lastDir.exists() && lastDir.isDirectory()) {
                fileChooser.setInitialDirectory(lastDir);
            }
        } else {
            String userHome = System.getProperty("user.home");
            File musicFolder = new File(userHome, "Music");
            if (musicFolder.exists()) {
                fileChooser.setInitialDirectory(musicFolder);
            }
        }
        
        FileChooser.ExtensionFilter audioFilter = new FileChooser.ExtensionFilter(
            "MP3 Files", 
            "*.mp3", "*.mpeg3"
        );
        fileChooser.getExtensionFilters().add(audioFilter);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
            "Audio Files", 
            "*.mp3", "*.wav", "*.m4a", "*.aac", "*.flac", "*.ogg", "*.wma"
        ));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.setSelectedExtensionFilter(audioFilter);
        
        File selectedFile = fileChooser.showOpenDialog(ownerWindow);
        
        // ⭐ Lưu last location
        if (selectedFile != null && selectedFile.getParent() != null) {
            lastLocation = selectedFile.getParent();
            System.out.println("💾 Đã lưu last location: " + lastLocation);
        }
        
        return selectedFile;
    }
    
    /**
     * Lấy last location (để sử dụng nếu cần)
     */
    public static String getLastLocation() {
        return lastLocation;
    }
    
    /**
     * Set last location (để restore từ config nếu cần)
     */
    public static void setLastLocation(String location) {
        lastLocation = location;
    }
}


