package com.excilys.computerdatabase.main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.main.java.mapper.ComputerMapper;
import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.Database;

public class ComputerDAO {

	private final Database db;

	private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
	private static final String FIND_ALL = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id";
	private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
	private static final String DELETE = "DELETE FROM `computer` WHERE id=?";

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
		List<Computer> cpuList = new ArrayList<>();
		Connection connection = null;
		
		try {
			connection = getConnection();
			PreparedStatement st = connection.prepareStatement(FIND_ALL);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Computer cpu = ComputerMapper.INSTANCE.createComputer(rs);
				cpuList.add(cpu);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}

		return cpuList;
	}

	public Computer getComputer(int id) throws Exception {

		Connection connection = null;
		Computer cpu = null;
		try {
			connection = getConnection();
			PreparedStatement st = connection.prepareStatement(FIND_BY_ID);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				cpu = ComputerMapper.INSTANCE.createComputer(rs);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}

		return cpu;
	}

	public int createComputer(Computer cpu) throws Exception {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement st = connection.prepareStatement(CREATE);
			st.setString(1, cpu.getName());
			st.setDate(2, cpu.getIntroduced());
			st.setDate(3, cpu.getDiscontinued());
			if (cpu.getCompany() == null) {
				st.setNull(4, Types.NULL);
			} else {
				st.setInt(4, cpu.getCompany().getId());
			}
			return st.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

	public int updateComputer(Computer cpu) throws Exception {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement st = connection.prepareStatement(UPDATE);
			st.setString(1, cpu.getName());
			st.setDate(2, cpu.getIntroduced());
			st.setDate(3, cpu.getDiscontinued());
			if (cpu.getCompany() == null) {
				st.setNull(4, Types.NULL);
			} else {
				st.setInt(4, cpu.getCompany().getId());
			}
			st.setInt(5, cpu.getId());
			return st.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

	public int deleteComputer(int id) throws Exception {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement st = connection.prepareStatement(DELETE);
			st.setInt(1, id);
			return st.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

}
