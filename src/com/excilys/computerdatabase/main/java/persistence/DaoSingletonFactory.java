package com.excilys.computerdatabase.main.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DaoSingletonFactory {
	private static Dao _dao = null;

	public static Dao getInstance() {
		if (_dao == null) {
			_dao = new Dao();
		}
		return _dao;
	}

}

class Dao implements IDao {
	Statement _stat;

	public Connection getConnection() throws SQLException {
		Connection conn = null;

		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", 
				"admincdb",
				"qwerty1234");
		_stat = conn.createStatement();

		System.out.println("Connected to database");
		return conn;
	}
}