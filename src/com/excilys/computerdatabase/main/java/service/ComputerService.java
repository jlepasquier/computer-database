package com.excilys.computerdatabase.main.java.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.main.java.dao.ComputerDAO;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.Database;

public class ComputerService {
	public ComputerService() {

	}

	public List<Computer> getComputerList() {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.getComputerList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Computer>();
	}

	public Computer getComputer(int id) {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.getComputer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int createComputer(Computer cpu) {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.createComputer(cpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int updateComputer(Computer cpu) {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.updateComputer(cpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteComputer(int id) {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.deleteComputer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
