package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.exception.InvalidComputerIdException;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.persistence.Database;

/**
 * The singleton ComputerDAO.
 */
public enum ComputerDAO {

    /** The singleton instance. */
    INSTANCE;

    /** The database. */
    private final Database db;

    /** The query CREATE. */
    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";

    /** The query UPDATE. */
    private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";

    /** The query FIND_ALL. */
    private static final String FIND_ALL = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";

    /** The query FIND_BY_ID. */
    private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";

    /** The query DELETE. */
    private static final String DELETE = "DELETE FROM `computer` WHERE id=?";

    /** The query COUNT. */
    private static final String COUNT = "SELECT COUNT(*) FROM `computer`";

    /** The Constant COMPUTERS_PER_PAGE. */
    private static final int COMPUTERS_PER_PAGE = 25;

    /**
     * Instantiates a new computer DAO.
     */
    ComputerDAO() {
        this.db = Database.INSTANCE;
    }

    /**
     * Gets the connection.
     * @return the connection
     * @throws SQLException the SQL exception
     */
    private Connection getConnection() throws SQLException {
        return db.getConnection();
    }

    /**
     * Closes connection.
     * @param conn the connection
     * @throws SQLException the SQL exception
     */
    private void closeConnection(Connection conn) throws SQLException {
        db.closeConnection(conn);
    }

    /**
     * Gets the computer page.
     * @param offset the offset
     * @return the computer page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Page<Computer> getComputerPage(int offset) throws SQLException, IllegalArgumentException {
        List<Computer> cpuList = new ArrayList<>();
        Connection connection = null;

        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try {
                connection = getConnection();
                ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_ALL, COMPUTERS_PER_PAGE,
                        offset * COMPUTERS_PER_PAGE);
                while (rs.next()) {
                    Computer cpu = ComputerMapper.INSTANCE.createComputer(rs);
                    cpuList.add(cpu);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }

        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, cpuList);
    }

    /**
     * Gets the computer.
     * @param id the id
     * @return the computer
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Computer getComputer(long id) throws SQLException, IllegalArgumentException {

        Connection connection = null;
        Computer cpu = null;
        if (id < 1) {
            throw new IllegalArgumentException("id=" + id + ". Wrong id, must be > 0");
        } else {
            try {
                connection = getConnection();
                ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_BY_ID, id);

                while (rs.next()) {
                    cpu = ComputerMapper.INSTANCE.createComputer(rs);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                closeConnection(connection);
            }
        }

        return cpu;
    }

    /**
     * Creates the computer.
     * @param cpu the computer
     * @return the number of lines updated
     * @throws SQLException the exception
     */
    public long createComputer(Computer cpu) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return QueryMapper.INSTANCE.executeCreate(connection, CREATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId());
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Updates a computer.
     * @param cpu the computer
     * @return boolean if the update was successful or not
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     * @throws InvalidComputerIdException exception
     */
    public boolean updateComputer(Computer cpu) throws SQLException, InvalidComputerIdException {
        Connection connection = null;
        if (cpu.getId() <= 0) {
            throw new InvalidComputerIdException();
        } else {
            try {
                connection = getConnection();

                return QueryMapper.INSTANCE.executeUpdate(connection, UPDATE, cpu.getName(),
                        cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                        cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                        cpu.getCompany() == null ? null : cpu.getCompany().getId(), cpu.getId());
            } catch (SQLException e) {
                throw e;
            } finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * Deletes a computer.
     * @param id the id of the computer to remove
     * @throws SQLException the exception
     * @throws InvalidComputerIdException exception
     * @return boolean for query success or failure
     */
    public boolean deleteComputer(long id) throws SQLException, InvalidComputerIdException {
        Connection connection = null;
        if (id <= 0) {
            throw new InvalidComputerIdException();
        } else {
            try {
                connection = getConnection();
                return QueryMapper.INSTANCE.executeUpdate(connection, DELETE, id);
            } catch (SQLException e) {
                throw e;
            } finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * Gets the number of computers in the database.
     * @throws SQLException the exception
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public int getComputerCount() throws SQLException, InvalidComputerIdException {
        Connection connection = null;
        try {
            connection = getConnection();
            int count = -1;

            ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, COUNT);
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection);
        }

    }

}
