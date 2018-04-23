package main.java.com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.computerdatabase.model.Company;

public enum CompanyMapper {
	INSTANCE;
	
	public Company createCompany(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		return new Company.Builder(id).withName(name).build();
	}
}
