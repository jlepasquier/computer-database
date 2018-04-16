package com.excilys.computerdatabase.main.java.ui;

import java.sql.SQLException;

import com.excilys.computerdatabase.main.java.persistence.Database;



public final class CLI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			System.out.println("Aucun argument détecté. Utilisez 'help' pour voir la liste des commandes disponibles.");
		}
		
		Database db = new Database();
		try {
			db.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
	}

}
