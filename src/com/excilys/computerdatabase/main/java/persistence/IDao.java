package com.excilys.computerdatabase.main.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDao {
	public Connection getConnection() throws SQLException;
	public ResultSet execute(String query) throws SQLException;
}
