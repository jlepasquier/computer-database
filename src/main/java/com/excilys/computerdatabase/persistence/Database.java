package main.java.com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The Database singleton.
 */
public enum Database {

    /** The singleton instance. */
    INSTANCE;

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException
     *             the SQL exception
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;

        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String dburl = bundle.getString("dburl");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        try {
            conn = DriverManager.getConnection(dburl, user, password);
        } catch (SQLException e) {
            throw e;
        }

        return conn;
    }

    /**
     * Close connection.
     *
     * @param conn
     *            the connection to database
     */
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
