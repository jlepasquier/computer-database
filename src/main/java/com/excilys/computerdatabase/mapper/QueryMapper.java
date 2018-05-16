package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The QueryMapper singleton.
 */
public enum QueryMapper {
    INSTANCE;

    /**
     * Executes a query.
     * @param connection the connection
     * @param query the query
     * @param args the list of parameters to fill the query
     * @return the result set of the query
     * @throws SQLException the SQL exception
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
     * @param connection the connection
     * @param query the query
     * @param args the list of parameters to fill the query
     * @return the id of the line we created o
     * @throws SQLException the SQL exception
     */
    public long executeCreate(Connection connection, String query, Object... args) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }

        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();

        long id = -1;
        while (rs.next()) {
            id = rs.getLong(1);
        }
        return id;
    }

    /**
     * Executes the update query.
     * @param connection the connection
     * @param query the query
     * @param args the list of parameters to fill the query
     * @return boolean if the update was successful or not
     * @throws SQLException the SQL exception
     */
    public boolean executeUpdate(Connection connection, String query, Object... args) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }

        int updatedLines = preparedStatement.executeUpdate();

        return (updatedLines > 0);
    }
}
