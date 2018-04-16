package com.excilys.computerdatabase.main.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
	
	Statement stat;
	
	public Database() {
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;

	    conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/computer-database-db",
                "admincdb",
                "qwerty1234");
	    this.stat = conn.createStatement();
	    
	    System.out.println("Connected to database");
	    return conn;
	}
	
	
	
	
}
