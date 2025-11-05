package Utils;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private ConfigManager() {}

    public static Properties loadProperties(String classpathResource) {
        try (InputStream in = ConfigManager.class.getResourceAsStream(classpathResource)) {
            Properties p = new Properties();
            if (in != null) {
                p.load(in);
            }
            return p;
        } catch (Exception e) {
            return new Properties();
        }
    }

    public static String get(String key, String defaultValue) {
        Properties p = loadProperties("/config.properties");
        return p.getProperty(key, defaultValue);
    }
}






