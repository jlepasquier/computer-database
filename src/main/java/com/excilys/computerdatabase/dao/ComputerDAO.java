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
    INSTANCE;

    private final Database db;
    private final QueryMapper queryMapper;
    private final ComputerMapper computerMapper;

    private static final int COMPUTERS_PER_PAGE = 25;

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
    private static final String FIND_ALL = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";
    private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
    private static final String DELETE = "DELETE FROM `computer` WHERE id=?";
    private static final String COUNT = "SELECT COUNT(*) FROM `computer`";

    /**
     * Instantiates a new computer DAO.
     */
    ComputerDAO() {
        this.db = Database.INSTANCE;
        this.queryMapper = QueryMapper.INSTANCE;
        this.computerMapper = ComputerMapper.INSTANCE;
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
     * Gets the computer page.
     * @param offset the offset
     * @return the computer page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Page<Computer> getComputerPage(int offset) throws SQLException, IllegalArgumentException {
        List<Computer> cpuList = new ArrayList<>();

        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try (Connection connection = getConnection()) {
                ResultSet rs = queryMapper.executeQuery(connection, FIND_ALL, COMPUTERS_PER_PAGE,
                        offset * COMPUTERS_PER_PAGE);
                while (rs.next()) {
                    Computer cpu = computerMapper.createComputer(rs);
                    cpuList.add(cpu);
                }
            } catch (SQLException e) {
                e.printStackTrace();
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

        Computer cpu = null;
        if (id < 1) {
            throw new IllegalArgumentException("id=" + id + ". Wrong id, must be > 0");
        } else {
            try (Connection connection = getConnection()) {
                ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, FIND_BY_ID, id);

                while (rs.next()) {
                    cpu = ComputerMapper.INSTANCE.createComputer(rs);
                }
            } catch (SQLException e) {
                throw e;
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
        try (Connection connection = getConnection()) {
            return QueryMapper.INSTANCE.executeCreate(connection, CREATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId());
        } catch (SQLException e) {
            throw e;
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

        if (cpu.getId() <= 0) {
            throw new InvalidComputerIdException();
        } else {
            try (Connection connection = getConnection()) {
                return QueryMapper.INSTANCE.executeUpdate(connection, UPDATE, cpu.getName(),
                        cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                        cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                        cpu.getCompany() == null ? null : cpu.getCompany().getId(), cpu.getId());
            } catch (SQLException e) {
                throw e;
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

        if (id <= 0) {
            throw new InvalidComputerIdException();
        } else {
            try (Connection connection = getConnection()) {
                return QueryMapper.INSTANCE.executeUpdate(connection, DELETE, id);
            } catch (SQLException e) {
                throw e;
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

        try (Connection connection = getConnection()) {
            int count = -1;

            ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, COUNT);
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Gets the number of computers in the database.
     * @throws SQLException the exception
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public int getComputerPageCount() throws SQLException, InvalidComputerIdException {
        try (Connection connection = getConnection()) {
            int count = -1;

            ResultSet rs = QueryMapper.INSTANCE.executeQuery(connection, COUNT);
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return (int) Math.ceil(count / COMPUTERS_PER_PAGE);
        } catch (SQLException e) {
            throw e;
        }
    }
}
