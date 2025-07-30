package com.Phon.backend.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Profile("test-db")
public class DatabaseTestRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTestRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
        SpringApplication.run(DatabaseTestRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        testDatabaseConnection();
    }

    private void testDatabaseConnection() throws SQLException {
        System.out.println("=== KIỂM TRA KẾT NỐI DATABASE ===");
        
        if (dataSource == null) {
            System.out.println("❌ DataSource là null!");
            return;
        }
        
        System.out.println("✅ DataSource đã được cấu hình");
        
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null) {
                System.out.println("❌ Không thể tạo connection!");
                return;
            }
            
            System.out.println("✅ Kết nối database thành công!");
            System.out.println("📊 Thông tin database:");
            System.out.println("   - URL: " + connection.getMetaData().getURL());
            System.out.println("   - Product: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("   - Version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("   - Driver: " + connection.getMetaData().getDriverName());
            System.out.println("   - Connection Valid: " + connection.isValid(5));
            System.out.println("   - Connection Closed: " + connection.isClosed());
            
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== KẾT THÚC KIỂM TRA ===");
    }
}
