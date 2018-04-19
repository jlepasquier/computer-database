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
			return ComputerDAO.INSTANCE.getComputerList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Computer>();
	}

	public Computer getComputer(int id) {
		try {
			return ComputerDAO.INSTANCE.getComputer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int createComputer(Computer cpu) {
		try {
			return ComputerDAO.INSTANCE.createComputer(cpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int updateComputer(Computer cpu) {
		try {
			return ComputerDAO.INSTANCE.updateComputer(cpu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteComputer(int id) {
		try {
			return ComputerDAO.INSTANCE.deleteComputer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
