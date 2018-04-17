package com.excilys.computerdatabase.main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.Database;

public class ComputerDAO {

	private final Database db;

	public ComputerDAO(Database db) {
		this.db = db;
	}

	private Connection getConnection() throws SQLException {
		return db.getConnection();
	}

	private void closeConnection(Connection conn) throws SQLException {
		db.closeConnection(conn);
	}

	public List<Computer> getComputerList() throws Exception {
		String sql = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname\n"
				+ "FROM computer as cpu\n" + "INNER JOIN company as cpy\n" + "ON cpy.id = cpu.company_id";

		List<Computer> cpuList = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("cpuname");
				Timestamp introduced = rs.getTimestamp("introduced");
				Timestamp discontinued = rs.getTimestamp("discontinued");
				String company = rs.getString("companyname");

				cpuList.add(new Computer
						.Builder(name)
						.withCompany(company)
						.withIntroduced(introduced)
						.withDiscontinued(discontinued)
						.withId(id).build()
						);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}

		return cpuList;
	}

	public Computer getComputer(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void createComputer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void updateComputer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void deleteComputer(int id) throws Exception {
		// TODO Auto-generated method stub

	}

}
