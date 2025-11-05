package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Lưu thông tin nhẹ (ví dụ: username đăng nhập gần nhất) trên máy người dùng.
 * Dùng file properties tại thư mục người dùng: ~/.musicplayer/session.properties
 */
public final class LocalStorage {
    private static final String APP_DIR = System.getProperty("user.home") + File.separator + ".musicplayer";
    private static final String SESSION_FILE = APP_DIR + File.separator + "session.properties";
    private static final String KEY_LAST_USERNAME = "last_username";

    private LocalStorage() {}

    private static Properties load() {
        Properties p = new Properties();
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            return p;
        }
        try (FileInputStream in = new FileInputStream(file)) {
            p.load(in);
        } catch (IOException ignored) {
        }
        return p;
    }

    private static void save(Properties p) {
        try {
            Files.createDirectories(Path.of(APP_DIR));
            try (FileOutputStream out = new FileOutputStream(SESSION_FILE)) {
                p.store(out, "MusicPlayer local session");
            }
        } catch (IOException ignored) {
        }
    }

    public static void setLastUsername(String username) {
        if (username == null) return;
        Properties p = load();
        p.setProperty(KEY_LAST_USERNAME, username);
        save(p);
    }

    public static String getLastUsername() {
        Properties p = load();
        return p.getProperty(KEY_LAST_USERNAME, "");
    }

    public static void clearLastUsername() {
        Properties p = load();
        p.remove(KEY_LAST_USERNAME);
        save(p);
    }
}






