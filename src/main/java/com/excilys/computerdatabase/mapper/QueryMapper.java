package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum QueryMapper {
	INSTANCE;
	
	public ResultSet executeQuery(Connection connection, String query, Object... args) throws SQLException {
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			for (int i=0 ; i<args.length ; i++) {
				preparedStatement.setObject(i+1, args[i]);
			}
			return preparedStatement.executeQuery();
	}
	
	
	public int executeUpdate(Connection connection, String query, Object... args) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		for (int i=0 ; i<args.length ; i++) {
			preparedStatement.setObject(i+1, args[i]);
		}
		return preparedStatement.executeUpdate();
}
	
}
