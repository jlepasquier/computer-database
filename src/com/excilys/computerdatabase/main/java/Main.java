package com.excilys.computerdatabase.main.java;

import com.excilys.computerdatabase.main.java.controller.CLIController;
import com.excilys.computerdatabase.main.java.service.ComputerService;
import com.excilys.computerdatabase.main.java.ui.CLI;

public class Main {
	public static void main(String[] args) {
		CLI cli = new CLI();
		ComputerService cs = new ComputerService();
		CLIController controller = new CLIController(cli, cs);
		
		controller.start();
	}
}
