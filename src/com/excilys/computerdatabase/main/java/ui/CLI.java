package com.excilys.computerdatabase.main.java.ui;

import java.sql.SQLException;

import com.excilys.computerdatabase.main.java.persistence.DaoSingletonFactory;
import com.excilys.computerdatabase.main.java.persistence.IDao;

public final class CLI {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Aucun argument détecté. Utilisez 'help' pour voir la liste des commandes disponibles.");
		}

		IDao db = DaoSingletonFactory.getInstance();
		
		try {
			db.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} 
	}

}
