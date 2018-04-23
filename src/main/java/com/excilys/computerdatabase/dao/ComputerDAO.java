package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.persistence.Database;

public enum ComputerDAO {
	INSTANCE;
	
	private final Database db;

	private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
	private static final String FIND_ALL = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";
	private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
	private static final String DELETE = "DELETE FROM `computer` WHERE id=?";

	private static final int COMPUTERS_PER_PAGE = 25;
	
	private ComputerDAO() {
		this.db = Database.INSTANCE;
	}

	private Connection getConnection() throws SQLException {
		return db.getConnection();
	}

	private void closeConnection(Connection conn) throws SQLException {
		db.closeConnection(conn);
	}

	public Page<Computer> getComputerPage(int offset) throws Exception {
		List<Computer> cpuList = new ArrayList<>();
		Connection connection = null;
		
		try {
			connection = getConnection();
			ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_ALL, COMPUTERS_PER_PAGE, offset*COMPUTERS_PER_PAGE);
			while (rs.next()) {
				Computer cpu = ComputerMapper.INSTANCE.createComputer(rs);
				cpuList.add(cpu);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}

		return new Page<Computer>(COMPUTERS_PER_PAGE, offset, cpuList);
	}

	public Computer getComputer(long id) throws Exception {

		Connection connection = null;
		Computer cpu = null;
		try {
			connection = getConnection();
			ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_BY_ID, id);

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
			return QueryMapper.INSTANCE.executeUpdate(connection, CREATE, 
					cpu.getName(), 
					Date.valueOf(cpu.getIntroduced()), 
					Date.valueOf(cpu.getDiscontinued()),
					cpu.getCompany() == null ? Types.NULL : cpu.getCompany().getId());
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
			return QueryMapper.INSTANCE.executeUpdate(connection, UPDATE, 
					cpu.getName(), 
					Date.valueOf(cpu.getIntroduced()), 
					Date.valueOf(cpu.getDiscontinued()),
					cpu.getCompany() == null ? Types.NULL : cpu.getCompany().getId(),
					cpu.getId());
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

	public int deleteComputer(long id) throws Exception {
		Connection connection = null;
		try {
			connection = getConnection();
			return QueryMapper.INSTANCE.executeUpdate(connection, DELETE, id);
		} catch (SQLException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

}
