package Backend.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileService {
    public boolean exists(String path) {
        return path != null && Files.exists(Path.of(path));
    }

    public String copyToLibrary(String sourcePath, String libraryDir) {
        try {
            Path source = Path.of(sourcePath);
            Files.createDirectories(Path.of(libraryDir));
            Path target = Path.of(libraryDir, source.getFileName().toString());
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(String path) {
        try {
            return new File(path).delete();
        } catch (Exception e) {
            return false;
        }
    }
}






