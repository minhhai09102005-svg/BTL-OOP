package Backend.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import javax.sql.DataSource;

/**
 * Quáº£n lÃ½ Connection Pool vá»›i HikariCP
 * Singleton pattern Ä‘á»ƒ Ä‘áº£m báº£o chá»‰ cÃ³ 1 DataSource
 * 
 * NguyÃªn táº¯c:
 * - KHÃ”NG lÆ°u Connection lÃ m field
 * - LuÃ´n láº¥y Connection tá»« DataSource ngay trÆ°á»›c khi dÃ¹ng vÃ  Ä‘Ã³ng trong try-with-resources
 * - KhÃ´ng reuse PreparedStatement/ResultSet sau khi Connection Ä‘Ã³ng
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private HikariDataSource dataSource;
    
    // ThÃ´ng tin káº¿t ná»‘i (Ä‘á»c tá»« database.properties)
    private String url;
    private String username;
    private String password;
    
    // Private constructor (Singleton)
    private DatabaseConnection() {
        loadDatabaseConfig();
        initializeDataSource();
    }
    
    /**
     * Láº¥y instance duy nháº¥t cá»§a DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Äá»c thÃ´ng tin database tá»« file properties
     */
    private void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            
            if (input == null) {
                System.err.println("KhÃ´ng tÃ¬m tháº¥y database.properties, dÃ¹ng giÃ¡ trá»‹ máº·c Ä‘á»‹nh");
                useDefaultConfig();
                return;
            }
            
            props.load(input);
            this.url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/music_app");
            this.username = props.getProperty("db.username", "root");
            this.password = props.getProperty("db.password", "");
            
        } catch (IOException e) {
            System.err.println("Lá»—i Ä‘á»c database.properties: " + e.getMessage());
            useDefaultConfig();
        }
    }
    
    /**
     * DÃ¹ng config máº·c Ä‘á»‹nh náº¿u khÃ´ng Ä‘á»c Ä‘Æ°á»£c file properties
     */
    private void useDefaultConfig() {
        this.url = "jdbc:mysql://localhost:3306/music_app?useSSL=false&serverTimezone=UTC";
        this.username = "root";
        this.password = "";
    }
    
    /**
     * Khá»Ÿi táº¡o HikariCP DataSource
     */
    private void initializeDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            
            // ThÃ´ng tin káº¿t ná»‘i
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            
            // Cáº¥u hÃ¬nh pool
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000); // 30 giÃ¢y
            config.setIdleTimeout(600000); // 10 phÃºt
            config.setMaxLifetime(1800000); // 30 phÃºt
            config.setLeakDetectionThreshold(60000); // PhÃ¡t hiá»‡n leak sau 60 giÃ¢y
            
            // Tá»‘i Æ°u cho MySQL
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");
            
            // Táº¡o DataSource
            this.dataSource = new HikariDataSource(config);
            
            // Test connection
            try (Connection testConn = dataSource.getConnection()) {
                System.out.println("âœ… Káº¿t ná»‘i database thÃ nh cÃ´ng vá»›i HikariCP!");
            }
            
        } catch (SQLException e) {
            System.err.println("\nâŒ ============================================");
            System.err.println("âŒ Lá»–I Káº¾T Ná»I DATABASE!");
            System.err.println("âŒ ============================================");
            System.err.println("URL: " + url);
            System.err.println("Username: " + username);
            System.err.println("Password: " + (password.isEmpty() ? "(rá»—ng)" : "***"));
            
            String errorMsg = e.getMessage();
            if (errorMsg != null) {
                if (errorMsg.contains("Connection refused") || errorMsg.contains("connect") || 
                    errorMsg.contains("Communications link failure")) {
                    System.err.println("\nâš ï¸  MySQL Server chÆ°a Ä‘Æ°á»£c khá»Ÿi Ä‘á»™ng hoáº·c khÃ´ng thá»ƒ káº¿t ná»‘i!");
                    System.err.println("ðŸ“ HÆ¯á»šNG DáºªN KHáº®C PHá»¤C:");
                    System.err.println("   1. Cháº¡y script kiá»ƒm tra: test_database.bat");
                    System.err.println("   2. Khá»Ÿi Ä‘á»™ng MySQL: start_mysql.bat (cáº§n quyá»n Admin)");
                    System.err.println("   3. Hoáº·c thá»§ cÃ´ng:");
                    System.err.println("      - Windows: Win+R â†’ 'services.msc' â†’ TÃ¬m 'MySQL80' â†’ Start");
                    System.err.println("      - CMD (Admin): net start MySQL80");
                    System.err.println("   4. Kiá»ƒm tra tráº¡ng thÃ¡i: check_mysql.bat");
                } else if (errorMsg.contains("Unknown database") || errorMsg.contains("doesn't exist")) {
                    System.err.println("\nâš ï¸  Database 'music_app' chÆ°a Ä‘Æ°á»£c táº¡o!");
                    System.err.println("ðŸ“ HÆ¯á»šNG DáºªN:");
                    System.err.println("   1. Cháº¡y: mysql -u root -p < database/music_app.sql");
                    System.err.println("   2. Hoáº·c trong MySQL Workbench: Má»Ÿ vÃ  cháº¡y database/music_app.sql");
                } else if (errorMsg.contains("Access denied") || errorMsg.contains("authentication")) {
                    System.err.println("\nâš ï¸  Sai username hoáº·c password!");
                    System.err.println("ðŸ“ HÆ¯á»šNG DáºªN:");
                    System.err.println("   1. Kiá»ƒm tra file: src/main/resources/database.properties");
                    System.err.println("   2. Cáº­p nháº­t password náº¿u MySQL cÃ³ password:");
                    System.err.println("      db.password=your_password_here");
                    System.err.println("   3. Náº¿u MySQL khÃ´ng cÃ³ password, Ä‘á»ƒ trá»‘ng: db.password=");
                } else {
                    System.err.println("\nâš ï¸  Lá»—i khÃ´ng xÃ¡c Ä‘á»‹nh!");
                    System.err.println("ðŸ’¡ Cháº¡y: test_database.bat Ä‘á»ƒ kiá»ƒm tra chi tiáº¿t");
                }
            }
            
            System.err.println("\nðŸ’¡ Chi tiáº¿t lá»—i:");
            e.printStackTrace();
            System.err.println("\nðŸ“‹ TÃ“M Táº®T:");
            System.err.println("   - test_database.bat: Kiá»ƒm tra káº¿t ná»‘i database");
            System.err.println("   - start_mysql.bat: Khá»Ÿi Ä‘á»™ng MySQL (cáº§n Admin)");
            System.err.println("   - check_mysql.bat: Kiá»ƒm tra tráº¡ng thÃ¡i MySQL");
            System.err.println("âŒ ============================================\n");
        }
    }
    
    /**
     * Láº¥y Connection tá»« pool (pháº£i Ä‘Ã³ng trong try-with-resources)
     * 
     * CÃ¡ch sá»­ dá»¥ng:
     * try (Connection conn = getConnection()) {
     *     // sá»­ dá»¥ng conn
     * } // tá»± Ä‘á»™ng Ä‘Ã³ng
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource chÆ°a Ä‘Æ°á»£c khá»Ÿi táº¡o! HÃ£y kiá»ƒm tra MySQL Server Ä‘Ã£ Ä‘Æ°á»£c khá»Ÿi Ä‘á»™ng chÆ°a.");
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("âŒ Lá»—i láº¥y connection tá»« pool: " + e.getMessage());
            System.err.println("   - Kiá»ƒm tra MySQL Server Ä‘Ã£ cháº¡y chÆ°a");
            System.err.println("   - Kiá»ƒm tra database.properties cÃ³ Ä‘Ãºng khÃ´ng");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Láº¥y DataSource (dÃ¹ng cho transaction náº¿u cáº§n)
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * ÄÃ³ng DataSource (chá»‰ gá»i khi shutdown app)
     */
    public void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("âœ… ÄÃ£ Ä‘Ã³ng HikariCP DataSource");
        }
    }
    
    /**
     * Test connection
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}