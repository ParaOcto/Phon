package com.Phon.backend.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnabledIfEnvironmentVariable(named = "DB_PASSWORD", matches = ".*")
class MySQLConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testMySQLConnection() throws SQLException {
        assertNotNull(dataSource, "DataSource should not be null");
        
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
            assertTrue(connection.isValid(10), "Connection should be valid");
            
            // Test thực hiện query đơn giản
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT 1 as test_value")) {
                
                assertTrue(resultSet.next(), "ResultSet should have at least one row");
                assertEquals(1, resultSet.getInt("test_value"), "Test value should be 1");
            }
            
            System.out.println("✅ MySQL connection test passed!");
            System.out.println("Database URL: " + connection.getMetaData().getURL());
            System.out.println("Database Product: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Database Version: " + connection.getMetaData().getDatabaseProductVersion());
        }
    }

    @Test
    void testDatabaseSchema() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Kiểm tra xem có thể tạo table hay không
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(50))");
                statement.execute("INSERT INTO test_table (id, name) VALUES (1, 'test') ON DUPLICATE KEY UPDATE name='test'");
                
                try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as count FROM test_table")) {
                    assertTrue(resultSet.next(), "Should be able to query test table");
                    assertTrue(resultSet.getInt("count") >= 1, "Should have at least 1 record");
                }
                
                statement.execute("DROP TABLE test_table");
            }
            
            System.out.println("✅ Database schema operations test passed!");
        }
    }
}
