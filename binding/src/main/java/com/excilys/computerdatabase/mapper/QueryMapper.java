package com.excilys.computerdatabase.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryMapper {    
    
    public static void prepareStatement(PreparedStatement preparedStatement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
    }
    
    public static PreparedStatement prepareStatement(Connection connection, String query, Object... args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement;
    }
}
