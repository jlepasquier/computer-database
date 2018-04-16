package com.excilys.computerdatabase.main.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Dao implements IDao {
	Statement stat;

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", 
				"admincdb",
				"qwerty1234");
		stat = conn.createStatement();

		System.out.println("Connected to database");
		return conn;
	}

	public ResultSet execute(String query) throws SQLException {
		return stat.executeQuery(query);
	}
}


public final class DaoSingletonFactory {
	private static Dao dao = null;

	public static Dao getInstance() {
		if (dao == null) {
			dao = new Dao();
		}
		return dao;
	}
}