package com.excilys.computerdatabase.main.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.main.java.model.Company;

public enum CompanyMapper {
	INSTANCE;
	
	public Company createCompany(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		return new Company.Builder(id).withName(name).build();
	}
}
