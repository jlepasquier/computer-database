package main.java.com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Database singleton.
 */
public enum Database {
    INSTANCE;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

    /**
     * Gets the connection.
     * @return the connection
     * @throws SQLException the SQL exception
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;

        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String dburl = bundle.getString("dburl");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dburl, user, password);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return conn;
    }
}
