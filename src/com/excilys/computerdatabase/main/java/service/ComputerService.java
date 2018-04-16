package com.excilys.computerdatabase.main.java.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.DaoSingletonFactory;
import com.excilys.computerdatabase.main.java.persistence.IDao;

public class ComputerService {
	public ComputerService() {
		
	}
	
	public List<Computer> getComputerList() {
		List<Computer> l = new ArrayList<>();
		try {
			String sql = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname\n" + 
					"FROM computer as cpu\n" + 
					"INNER JOIN company as cpy\n" + 
					"ON cpy.id = cpu.company_id";
			
			IDao dao = DaoSingletonFactory.getInstance();
			dao.getConnection();
			ResultSet rs = dao.execute(sql);
		
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("cpuname");
				Date introduced = rs.getDate("introduced");
				Date discontinued = rs.getDate("discontinued");
				String company = rs.getString("companyname");
				
				Computer cpu = new Computer(name, introduced, discontinued, company);
				cpu.setId(id);
				l.add(cpu);
			};
			
		}
		catch (SQLException e) {
			System.out.println("Error : query failed.");
			System.out.println(e.getMessage());
		}
		
		return l;
	}
	
	
	
	
}
