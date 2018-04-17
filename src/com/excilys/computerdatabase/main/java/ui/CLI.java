package com.excilys.computerdatabase.main.java.ui;

import java.util.List;

import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.service.ComputerService;

public final class CLI {

	public static void main(String[] args) {

		ComputerService cs = new ComputerService();
		List<Computer> cpuList = cs.getComputerList();
		for (Computer cpu : cpuList) {
			System.out.println(cpu);
		}
		
		for (int i=25 ; i<30 ; i++) {
			System.out.println("--------------");
			Computer cpu = cs.getComputer(i);
			System.out.println(cpu);
		}
		
		
		

		
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
