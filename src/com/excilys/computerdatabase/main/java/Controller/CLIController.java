package com.excilys.computerdatabase.main.java.Controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.service.ComputerService;
import com.excilys.computerdatabase.main.java.ui.CLI;

public class CLIController {

	private final CLI cli;
	private final ComputerService cs;

	public CLIController(CLI cli, ComputerService cs) {
		this.cli = cli;
		this.cs = cs;
	}

	public void start() {
		cli.initView();
		do {
			cli.showActions();
		} while (handleAction(readInt()));
		cli.stop();
	}

	public boolean handleAction(int action) {
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
	public int readInt() {
		try {
			return Integer.parseInt(cli.readData());
		} catch (Exception e) {
			System.out.println("Entrez un nombre entier svp.");
			return readInt();
		}
	}

	public String readString() {
		return cli.readData();
	}

	public Date readDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return new Date(format.parse(readString()).getTime());
		} catch (ParseException e) {System.out.println("Erreur dans le format de la date, veuillez réessayer : dd/MM/yyyy");
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
	
	
	
	/*** Appels au service ***/
	public void findAll() {
		List<Computer> cpuList = cs.getComputerList();
		for (Computer cpu : cpuList) {
			System.out.println(cpu);
		}
	}

	public void find(int id) {
		Computer cpu = cs.getComputer(id);
		System.out.println(cpu);
	}

	public void delete(int id) {
		cs.deleteComputer(id);
	}

	public void update(Computer cpu) {
		cs.updateComputer(cpu);
	}

	public void create(Computer cpu) {
		cs.createComputer(cpu);
	}

}
