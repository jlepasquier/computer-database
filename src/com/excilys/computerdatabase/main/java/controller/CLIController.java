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
		do {
			List<Computer> cpuList = cs.getComputerList(0);
			for (Computer cpu : cpuList) {
				System.out.println(cpu);
			}
		} while (!cli.readString().equals("m"));
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
