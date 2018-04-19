package com.excilys.computerdatabase.main.java.ui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.service.ComputerService;

public enum CLI {
	INSTANCE;

	private static ComputerService cs;
	private static Scanner scan;

	public static void start() {
		cs = new ComputerService();
		scan = new Scanner(System.in);
	}

	public static void actionLoop() {
		do {
			showActions();
		} while (readAction());
	}

	public static void stop() {
		scan.close();
	}

	public static void main(String[] args) {
		start();/*
		int test = readInt();
		System.out.println(test);

		String test2 = readString();
		System.out.println(test2);*/
		actionLoop();
		stop();
	}

	public static void showActions() {
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

	public static boolean readAction() {
		return handleAction(readInt());
	}

	public static boolean handleAction(int action) {
		switch (action) {
		case 0:
			return false;
		case 1:
			findAll();
			break;
		case 2:
			System.out.println("ID de l'ordinateur à chercher ?");
			find(readInt());
			break;
		case 3:
			System.out.println("ID de l'ordinateur à supprimer ?");
			delete(readInt());
			break;
		case 4:
			update(readCpuToUpdate());
			break;
		case 5:
			create(readCpuToCreate());
			break;
		default:
			System.out.println("Veuillez entrer un nombre entre 0 et 5 svp.");
			break;
		}
		return true;
	}

	/*** METHODS TO READ DATA ***/
	public static Computer readCpuToUpdate() {
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

	public static Computer readCpuToCreate() {
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

	public static int readInt() {
		System.out.print("> ");
		boolean isValid = false;
		int n = 0;
		while (!isValid) {
			try {
				n = Integer.parseInt(scan.nextLine());
				isValid = true;
			} catch (Exception e) {
				System.out.println("Entrez un nombre svp");
			}
		}

		return n;
	}

	public static String readString() {
		System.out.print("> ");
		String str = scan.nextLine();
		return str;
	}

	public static Date readDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		boolean isValid = false;
		while (!isValid) {
			String dateString = readString();

			try {
				date = new Date(format.parse(dateString).getTime());
				isValid = true;
			} catch (ParseException e) {
				System.out.println("Erreur dans le format de la date, veuillez réessayer : dd/MM/yyyy");
			}
		}

		return date;
	}

	/*** Appels au service ***/
	public static void findAll() {
		List<Computer> cpuList = cs.getComputerList();
		for (Computer cpu : cpuList) {
			System.out.println(cpu);
		}
	}

	public static void find(int id) {
		Computer cpu = cs.getComputer(id);
		System.out.println(cpu);
	}

	public static void delete(int id) {
		cs.deleteComputer(id);
	}

	public static void update(Computer cpu) {
		cs.updateComputer(cpu);
	}

	public static void create(Computer cpu) {
		cs.createComputer(cpu);
	}
}
