package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

public enum ComputerMapper {
	INSTANCE;

	
	public Computer createComputer(ResultSet rs) throws SQLException {
		
		int id = rs.getInt("id");
		String name = rs.getString("cpuname");
		Date introducedDate = rs.getDate("introduced");
		LocalDate introduced = (introducedDate == null) ? null : introducedDate.toLocalDate();
		Date discontinuedDate = rs.getDate("discontinued");
		LocalDate discontinued = (discontinuedDate == null) ? null : discontinuedDate.toLocalDate();
		String companyName = rs.getString("companyname");
		int companyId = rs.getInt("companyid");
		Company company = new Company.Builder(companyId).withName(companyName).build();

		return new Computer.Builder(name).withCompany(company).withIntroduced(introduced)
				.withDiscontinued(discontinued).withId(id).build();
	}
}
