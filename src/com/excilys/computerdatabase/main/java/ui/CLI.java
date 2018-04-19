package com.excilys.computerdatabase.main.java.ui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;

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

	/*** METHODS TO READ DATA ***/
	public String readData() {
		System.out.print("> ");
		return scan.nextLine();
	}

	public int readInt() {
		try {
			return Integer.parseInt(readData());
		} catch (Exception e) {
			System.out.println("Entrez un nombre entier svp.");
			return readInt();
		}
	}

	public String readString() {
		return readData();
	}

	public Date readDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return new Date(format.parse(readString()).getTime());
		} catch (ParseException e) {
			System.out.println("Erreur dans le format de la date, veuillez réessayer : dd/MM/yyyy");
			return readDate();
		}
	}

	public Computer readCpuToUpdate() {
		System.out.println("ID de l'ordinateur ?");
		int id = readInt();
		System.out.println("Nom de l'ordinateur ?");
		String name = readString();
		System.out.println("Date de mise sur le marché ?");
		Date introduced = readDate();
		System.out.println("Date de retrait du marché ?");
		Date discontinued = readDate();
		System.out.println("ID de l'entreprise ?");
		int comp_id = readInt();
		Company company = new Company.Builder(comp_id).build();
		return new Computer.Builder(name).withId(id).withCompany(company).withIntroduced(introduced)
				.withDiscontinued(discontinued).build();
	}

	public Computer readCpuToCreate() {
		System.out.println("Nom de l'ordinateur ?");
		String name = readString();
		System.out.println("Date de mise sur le marché ?");
		Date introduced = readDate();
		System.out.println("Date de retrait du marché ?");
		Date discontinued = readDate();
		System.out.println("ID de l'entreprise ?");
		int comp_id = readInt();
		Company company = new Company.Builder(comp_id).build();
		return new Computer.Builder(name).withCompany(company).withIntroduced(introduced).withDiscontinued(discontinued)
				.build();
	}

}
