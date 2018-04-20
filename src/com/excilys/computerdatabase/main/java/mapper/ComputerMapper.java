package com.excilys.computerdatabase.main.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;

public enum ComputerMapper {
	INSTANCE;

	
	public Computer createComputer(ResultSet rs) throws SQLException {
		
		int id = rs.getInt("id");
		String name = rs.getString("cpuname");
		LocalDate introduced = rs.getDate("introduced").toLocalDate();
		LocalDate discontinued = rs.getDate("discontinued").toLocalDate();
		String companyName = rs.getString("companyname");
		int companyId = rs.getInt("companyid");
		Company company = new Company.Builder(companyId).withName(companyName).build();

		return new Computer.Builder(name).withCompany(company).withIntroduced(introduced)
				.withDiscontinued(discontinued).withId(id).build();
	}
}
