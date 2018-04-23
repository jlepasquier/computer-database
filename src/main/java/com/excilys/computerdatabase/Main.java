package main.java.com.excilys.computerdatabase;

import main.java.com.excilys.computerdatabase.controller.CLIController;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;
import main.java.com.excilys.computerdatabase.ui.CLI;

public class Main {
	public static void main(String[] args) {
		CLI cli = new CLI();
		ComputerService computerService = new ComputerService();
		CompanyService companyService = new CompanyService();
		CLIController controller = new CLIController(cli, computerService, companyService);
		
		controller.start();
	}
}
