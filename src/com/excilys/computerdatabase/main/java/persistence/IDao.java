package com.excilys.computerdatabase.main.java.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDao {
	public Connection getConnection() throws SQLException;
}
