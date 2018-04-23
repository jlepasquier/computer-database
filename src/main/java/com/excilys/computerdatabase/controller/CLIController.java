package main.java.com.excilys.computerdatabase.controller;

import java.util.List;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;
import main.java.com.excilys.computerdatabase.ui.CLI;

public class CLIController {

	private final CLI cli;
	private final ComputerService computerService;
	private final CompanyService companyService;

	public CLIController(CLI cli, ComputerService computerService, CompanyService companyService) {
		this.cli = cli;
		this.computerService = computerService;
		this.companyService = companyService;
	}

	public void start() {
		cli.initView();
		do {
			cli.showActions();
		} while (handleAction(cli.readInt()));
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
			find(cli.readInt());
			break;
		case 3:
			delete(cli.readInt());
			break;
		case 4:
			update(cli.readCpuToUpdate());
			break;
		case 5:
			create(cli.readCpuToCreate());
			break;
		case 6:
			findEveryCompany();
			break;
		default:
			break;
		}
		return true;
	}

	/*** Service calls ***/
	public void findAll() {
		int currentPage = 0;
		boolean exit = false;
		do {
			List<Computer> cpuList = computerService.getComputerList(currentPage);
			if (cpuList.isEmpty() && (currentPage >0)) {
				currentPage -= 1;
				cpuList = computerService.getComputerList(currentPage);
			}
			cli.printList(cpuList);
			cli.printPageNavigationIndication();

			String action = cli.readString();

			if (action.equals("m")) {
				exit = true;
			} else if (action.equals("s") && !cpuList.isEmpty()) {
				currentPage += 1;
			} else if (action.equals("p") && (currentPage > 0)) {
				currentPage -= 1;
			}
		} while (!exit);
	}
	
	public void findEveryCompany() {
		int currentPage = 0;
		boolean exit = false;
		do {
			List<Company> companyList = companyService.getCompanyList(currentPage);
			if (companyList.isEmpty() && (currentPage >0)) {
				currentPage -= 1;
				companyList = companyService.getCompanyList(currentPage);
			}
			cli.printList(companyList);
			cli.printPageNavigationIndication();

			String action = cli.readString();

			if (action.equals("m")) {
				exit = true;
			} else if (action.equals("s") && !companyList.isEmpty()) {
				currentPage += 1;
			} else if (action.equals("p") && (currentPage > 0)) {
				currentPage -= 1;
			}
		} while (!exit);
	}

	public void find(int id) {
		Computer cpu = computerService.getComputer(id);
		cli.printComputer(cpu);
	}

	public void delete(int id) {
		computerService.deleteComputer(id);
	}

	public void update(Computer cpu) {
		computerService.updateComputer(cpu);
	}

	public void create(Computer cpu) {
		computerService.createComputer(cpu);
	}

}
