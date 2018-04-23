package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The QueryMapper singleton.
 */
public enum QueryMapper {

    /** The singleton instance. */
    INSTANCE;

    /**
     * Executes a query.
     *
     * @param connection
     *            the connection
     * @param query
     *            the query
     * @param args
     *            the list of parameters to fill the query
     * @return the result set of the query
     * @throws SQLException
     *             the SQL exception
     */
    public ResultSet executeQuery(Connection connection, String query, Object... args) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Executes the update query.
     *
     * @param connection
     *            the connection
     * @param query
     *            the query
     * @param args
     *            the list of parameters to fill the query
     * @return the number of lines that have been updated
     * @throws SQLException
     *             the SQL exception
     */
    public int executeUpdate(Connection connection, String query, Object... args) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeUpdate();
    }

}
