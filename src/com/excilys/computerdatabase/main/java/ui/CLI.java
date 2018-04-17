package com.excilys.computerdatabase.main.java.ui;

import java.sql.Date;
import java.util.List;

import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.service.ComputerService;

public final class CLI {

	public static void main(String[] args) {

		/* Creation of ComputerService*/
		ComputerService cs = new ComputerService();
		
		/* GetComputerList*/
		List<Computer> cpuList = cs.getComputerList();
		for (Computer cpu : cpuList) {
			System.out.println(cpu);
		}
		
		/* GetComputer by id*/
		for (int i=25 ; i<30 ; i++) {
			System.out.println("--------------");
			Computer cpu = cs.getComputer(i);
			System.out.println(cpu);
		}
		
		/* Create computer */
		Computer cpu = new Computer.Builder("Microsoft").build();
		//cs.createComputer(cpu);
		
		/* Update computer */
		Company company = new Company(1,"Apple");
		Computer cpuToUpdate = new Computer.Builder("SuperBG3000").withId(578).withDiscontinued(new Date(System.currentTimeMillis())).withCompany(company).build();
		cs.updateComputer(cpuToUpdate);
		
		/* Delete computer*/
		cs.deleteComputer(579);
		

		
		/*
		 * if (args.length == 0) { System.out.
		 * println("Aucun argument détecté. Utilisez 'help' pour voir la liste des commandes disponibles."
		 * ); } switch (args[0]) { case "cpulist": ComputerService cs = new
		 * ComputerService(); List<Computer> cpulist = cs.getComputerList(); for
		 * (Computer cpu : cpulist) { System.out.println(cpu.getName()); } break;
		 * default: System.out.
		 * println("Argument inconnu. Utilisez 'help' pour voir la liste des commandes disponibles."
		 * ); break; }
		 */

	}

}
