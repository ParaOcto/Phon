package com.Phon.backend.database;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "logging.level.com.Phon.backend.database=INFO"
})
class SimpleDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDatabaseTest.class);

    @Autowired
    private DataSource dataSource;

    @Test
    void testDatabaseConnectionWithOutput() throws SQLException {
        logger.info("=== KIỂM TRA KẾT NỐI DATABASE ===");
        
        assertNotNull(dataSource, "DataSource should not be null");
        logger.info("✅ DataSource đã được cấu hình");
        
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
            assertTrue(connection.isValid(5), "Connection should be valid");
            
            logger.info("✅ Kết nối database thành công!");
            logger.info("📊 Thông tin database:");
            logger.info("   - URL: {}", connection.getMetaData().getURL());
            logger.info("   - Product: {}", connection.getMetaData().getDatabaseProductName());
            logger.info("   - Version: {}", connection.getMetaData().getDatabaseProductVersion());
            logger.info("   - Driver: {}", connection.getMetaData().getDriverName());
            logger.info("   - Connection Valid: {}", connection.isValid(5));
            logger.info("   - Connection Closed: {}", connection.isClosed());
            
        } catch (SQLException e) {
            logger.error("❌ Lỗi kết nối database: {}", e.getMessage());
            fail("Database connection failed: " + e.getMessage());
        }
        
        logger.info("=== KẾT THÚC KIỂM TRA ===");
    }
}
