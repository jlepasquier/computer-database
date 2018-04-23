package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.mapper.CompanyMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.persistence.Database;

public enum CompanyDAO {
	INSTANCE;
	
	private final Database db;

	private static final String FIND_ALL = "SELECT * from company LIMIT ? OFFSET ?";


	private static final int COMPANIES_PER_PAGE = 10;
	
	private CompanyDAO() {
		this.db = Database.INSTANCE;
	}

	private Connection getConnection() throws SQLException {
		return db.getConnection();
	}

	private void closeConnection(Connection conn) throws SQLException {
		db.closeConnection(conn);
	}
	
	
	public Page<Company> getCompanyPage(int offset) throws Exception {
		List<Company> companyList = new ArrayList<>();
		Connection connection = null;
		
		try {
			connection = getConnection();
			ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_ALL, COMPANIES_PER_PAGE, offset*COMPANIES_PER_PAGE);
			while (rs.next()) {
				Company company = CompanyMapper.INSTANCE.createCompany(rs);
				companyList.add(company);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}

		return new Page<Company>(COMPANIES_PER_PAGE, offset, companyList);
	}

	
}
