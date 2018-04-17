package com.excilys.computerdatabase.main.java.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Database {
	INSTANCE;

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties properties = new Properties();

		try (InputStream inputStream = new FileInputStream("resources/config/config.properties")) {
			String dburl = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
			properties.load(inputStream);
			conn = DriverManager.getConnection(dburl, properties);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conn;
	}

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
