package test.java.com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.persistence.DataSource;

public class DataSourceTest {
    
    @Test
    public void getConnectionTest() throws SQLException {
        Connection conn = DataSource.getConnection();
        assertNotNull(conn);
    }

}