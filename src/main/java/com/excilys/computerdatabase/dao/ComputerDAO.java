package main.java.com.excilys.computerdatabase.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Computer;

@Repository("computerDAO")
public class ComputerDAO {

    private JdbcTemplate jdbcTemplate;

    private static final int COMPUTERS_PER_PAGE = 25;

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
    private static final String FIND_PAGE = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";
    private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
    private static final String DELETE = "DELETE FROM `computer` WHERE id IN %s";
    private static final String COUNT = "SELECT COUNT(*) FROM `computer`";
    private static final String SEARCH = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ? ORDER BY cpu.name LIMIT ? OFFSET ? ";
    private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ?";

    ComputerDAO(DataSource pDataSource) {
        jdbcTemplate = new JdbcTemplate(pDataSource);
    }

    /**
     * Gets the computer page.
     * @param offset the offset
     * @return the computer page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Page<Computer> getComputerPage(int offset) {
        List<Computer> computerList;
        try {
            computerList = jdbcTemplate.query(FIND_PAGE, preparedStatement -> {
                QueryMapper.prepareStatement(preparedStatement, COMPUTERS_PER_PAGE, (offset-1) * COMPUTERS_PER_PAGE);
            }, (resultSet, rowNum) -> {
                return ComputerMapper.createComputer(resultSet);
            });
        } catch (Exception e) {
            computerList = new ArrayList<>();
        }
        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, computerList);
    }

    /**
     * Gets the computer.
     * @param id the id
     * @return the computer
     * @throws SQLException the exception
     * @throws InvalidIdException the exception
     */
    public Optional<Computer> getComputer(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, (resultSet, rowNum) -> {
                return Optional.of(ComputerMapper.createComputer(resultSet));
            });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Creates the computer.
     * @param cpu the computer
     * @return the number of lines updated
     */
    public Optional<Long> createComputer(Computer cpu) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            return QueryMapper.prepareStatement(connection, CREATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId());
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey().longValue());
    }

    /**
     * Updates a computer.
     * @param cpu the computer
     * @return boolean if the update was successful or not
     * @throws InvalidComputerIdException exception
     */
    public boolean updateComputer(Computer cpu) {

        long updatedRows = jdbcTemplate.update(connection -> {
            return QueryMapper.prepareStatement(connection, UPDATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId(), cpu.getId());
        });

        return (updatedRows > 0);
    }

    /**
     * Deletes a computer.
     * @param id the id of the computer to remove
     * @throws InvalidComputerIdException exception
     * @return boolean for query success or failure
     */
    public boolean deleteComputers(String ids) throws InvalidIdException {
        long updatedRows = jdbcTemplate.update(connection -> {
            return QueryMapper.prepareStatement(connection, String.format(DELETE, ids));
        });
        return (updatedRows > 0);
    }

    /**
     * Searches a computer.
     * @param search the string containing the word to look for
     * @param offset
     * @throws InvalidComputerIdException exception
     * @return the page of computers
     */
    public Page<Computer> searchComputer(String search, int offset) {
        List<Computer> computerList;
        try {
            String searchString = "%" + search + "%";
            computerList = jdbcTemplate.query(SEARCH, preparedStatement -> {
                QueryMapper.prepareStatement(preparedStatement, searchString, searchString, COMPUTERS_PER_PAGE,
                        (offset -1) * COMPUTERS_PER_PAGE);
            }, (resultSet, rowNum) -> {
                return ComputerMapper.createComputer(resultSet);
            });
        } catch (Exception e) {
            computerList = new ArrayList<>();
        }
        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, computerList);
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getComputerCount() {
        return jdbcTemplate.queryForObject(COUNT, (resultSet, rowNum) -> {
            return Optional.of(resultSet.getLong(1));
        });
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getComputerPageCount() {

        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getComputerCount();

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
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
    public Optional<Long> getSearchComputerCount(String search) {
        String searchString = "%" + search + "%";
        return jdbcTemplate.queryForObject(SEARCH_COUNT, new Object[] { searchString, searchString },
                (resultSet, rowNum) -> {
                    return Optional.of(resultSet.getLong(1));
                });
    }

    /**
     * Gets the number of computers in the database.
     * @throws InvalidComputerIdException exception
     * @return the number of computers in the database
     */
    public Optional<Long> getSearchComputerPageCount(String search) {
        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getSearchComputerCount(search);

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
        }

        return Optional.ofNullable(numberOfPages);
    }
}
