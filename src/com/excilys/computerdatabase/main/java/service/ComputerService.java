package com.excilys.computerdatabase.main.java.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.main.java.DAO.ComputerDAO;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.Database;

public class ComputerService {
	public ComputerService() {

	}

	public List<Computer> getComputerList() {
		try {
			ComputerDAO dao = new ComputerDAO(Database.INSTANCE);
			return dao.getComputerList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ArrayList<Computer>();
	}

}
