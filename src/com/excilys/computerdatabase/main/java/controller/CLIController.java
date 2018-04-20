package com.excilys.computerdatabase.main.java.controller;

import java.util.List;

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
			List<Computer> cpuList = cs.getComputerList(currentPage);
			if (cpuList.isEmpty() && (currentPage >0)) {
				currentPage -= 1;
				cpuList = cs.getComputerList(currentPage);
			}
			cli.printComputerList(cpuList);
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

	public void find(int id) {
		Computer cpu = cs.getComputer(id);
		cli.printComputer(cpu);
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
