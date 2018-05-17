package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Computer;

@Repository("computerDAO")
public class ComputerDAO {

    private DataSource dataSource;

    ComputerDAO(DataSource pdataSource) {
        dataSource = pdataSource;
    }
    
    private static final int COMPUTERS_PER_PAGE = 25;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
    private static final String FIND_ALL = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";
    private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
    private static final String DELETE = "DELETE FROM `computer` WHERE id IN %s";
    private static final String COUNT = "SELECT COUNT(*) FROM `computer`";
    private static final String SEARCH = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ? ORDER BY cpu.name LIMIT ? OFFSET ? ";
    private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ?";

    /**
     * Gets the computer page.
     * @param offset the offset
     * @return the computer page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Page<Computer> getComputerPage(int offset) throws IllegalArgumentException {
        List<Computer> cpuList = new ArrayList<>();

        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try (Connection connection = dataSource.getConnection()) {
                ResultSet rs = QueryMapper.executeQuery(connection, FIND_ALL, COMPUTERS_PER_PAGE,
                        offset * COMPUTERS_PER_PAGE);
                while (rs.next()) {
                    Computer cpu = ComputerMapper.createComputer(rs);
                    cpuList.add(cpu);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }

        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, cpuList);
    }

    /**
     * Gets the computer.
     * @param id the id
     * @return the computer
     * @throws SQLException the exception
     * @throws InvalidIdException the exception
     */
    public Optional<Computer> getComputer(long id) throws InvalidIdException {

        Computer cpu = null;

        if (id < 1) {
            throw new InvalidIdException();
        } else {
            try (Connection connection = dataSource.getConnection()) {
                ResultSet rs = QueryMapper.executeQuery(connection, FIND_BY_ID, id);

                while (rs.next()) {
                    cpu = ComputerMapper.createComputer(rs);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }

        return Optional.ofNullable(cpu);
    }

    /**
     * Creates the computer.
     * @param cpu the computer
     * @return the number of lines updated
     */
    public Optional<Long> createComputer(Computer cpu) {
        Long cpuId = null;
        try (Connection connection = dataSource.getConnection()) {
            cpuId = QueryMapper.executeCreate(connection, CREATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.ofNullable(cpuId);
    }

    /**
     * Updates a computer.
     * @param cpu the computer
     * @return boolean if the update was successful or not
     * @throws InvalidComputerIdException exception
     */
    public boolean updateComputer(Computer cpu) throws InvalidIdException {
        if (cpu.getId() <= 0) {
            throw new InvalidIdException();
        } else {
            try (Connection connection = dataSource.getConnection()) {
                return QueryMapper.executeUpdate(connection, UPDATE, cpu.getName(),
                        cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                        cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                        cpu.getCompany() == null ? null : cpu.getCompany().getId(), cpu.getId());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Deletes a computer.
     * @param id the id of the computer to remove
     * @throws InvalidComputerIdException exception
     * @return boolean for query success or failure
     */
    public boolean deleteComputers(String ids) throws InvalidIdException {
        try (Connection connection = dataSource.getConnection()) {
            return QueryMapper.executeUpdate(connection, String.format(DELETE, ids));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

    /**
     * Searches a computer.
     * @param search the string containing the word to look for
     * @param offset
     * @throws InvalidComputerIdException exception
     * @return the page of computers
     */
    public Page<Computer> searchComputer(String search, int offset) {

        List<Computer> cpuList = new ArrayList<>();

        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try (Connection connection = dataSource.getConnection()) {
                String searchString = "%" + search + "%";
                ResultSet rs = QueryMapper.executeQuery(connection, SEARCH, searchString, searchString,
                        COMPUTERS_PER_PAGE, offset * COMPUTERS_PER_PAGE);
                while (rs.next()) {
                    Computer cpu = ComputerMapper.createComputer(rs);
                    cpuList.add(cpu);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }

        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, cpuList);
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getComputerCount() throws InvalidIdException {
        Long count = null;
        try (Connection connection = dataSource.getConnection()) {

            ResultSet rs = QueryMapper.executeQuery(connection, COUNT);
            while (rs.next()) {
                count = rs.getLong(1);
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(count);
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getComputerPageCount() throws InvalidIdException {
        Long numberOfComputers = null;
        Long numberOfPages = null;
        try (Connection connection = dataSource.getConnection()) {

            ResultSet rs = QueryMapper.executeQuery(connection, COUNT);
            while (rs.next()) {
                numberOfComputers = rs.getLong(1);
            }
            numberOfPages = (long) Math.ceil(numberOfComputers / COMPUTERS_PER_PAGE);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.ofNullable(numberOfPages);
    }

    /**
     * Gets the number of computers returned by the search query
     * @param search the string containing the word to look for
     * @param offset
     * @throws InvalidComputerIdException exception
     * @return the number of computers
     */
    public Optional<Long> getSearchComputerCount(String search, int offset) {
        Long count = null;
        try (Connection connection = dataSource.getConnection()) {

            String searchString = "%" + search + "%";
            ResultSet rs = QueryMapper.executeQuery(connection, SEARCH_COUNT, searchString, searchString);
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(count);
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getSearchComputerPageCount(String search) throws InvalidIdException {
        Long numberOfComputers = null;
        Long numberOfPages = null;
        try (Connection connection = dataSource.getConnection()) {

            String searchString = "%" + search + "%";
            ResultSet rs = QueryMapper.executeQuery(connection, SEARCH_COUNT, searchString, searchString);
            while (rs.next()) {
                numberOfComputers = rs.getLong(1);
            }
            numberOfPages = (long) Math.ceil(numberOfComputers / COMPUTERS_PER_PAGE);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.ofNullable(numberOfPages);
    }
}
