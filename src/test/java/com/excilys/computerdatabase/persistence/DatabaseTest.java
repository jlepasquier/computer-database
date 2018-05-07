package test.java.com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import main.java.com.excilys.computerdatabase.persistence.Database;

/**
 * The Class DatabaseTest.
 */
public class DatabaseTest {

    Connection conn;

    /**
     * Set up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        conn = Database.INSTANCE.getConnection();
    }

    /**
     * Test.
     *
     * @throws SQLException
     *             the SQL exception
     */
    @Test
    public void testGetConnection() throws SQLException {
        assertNotNull(conn);
    }

}
