package com.excilys.computerdatabase.main.java.ui;

import java.util.Scanner;

public class CLI {

	private static Scanner scan;

	public void initView() {
		scan = new Scanner(System.in);
	}

	public void stop() {
		scan.close();
	}

	public void showActions() {
		System.out.println();
		System.out.println("---------------------------------------------------------------");
		System.out.println("-------------- Liste des commandes disponibles : --------------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("0 - Quitter");
		System.out.println("1 - Obtenir la liste de tous les ordinateurs");
		System.out.println("2 - Obtenir les informations d'un ordinateur à partir de son id");
		System.out.println("3 - Supprimer un ordinateur à partir de son id");
		System.out.println("4 - Mettre à jour les informations d'un ordinateur");
		System.out.println("5 - Créer un nouvel ordinateur");
		System.out.println("---------------------------------------------------------------");
	}

	public String readData() {
		System.out.print("> ");
		return scan.nextLine();
	}

}
