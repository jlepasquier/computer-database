package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.mapper.CompanyMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.persistence.DataSource;

/**
 * The CompanyDAO singleton.
 */
public enum CompanyDAO {
    INSTANCE;

    private final QueryMapper queryMapper;
    private final CompanyMapper companyMapper;

    private static final int COMPANIES_PER_PAGE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private static final String FIND_ALL = "SELECT * FROM company";
    private static final String FIND_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id=?";

    /**
     * Instantiates a new company DAO.
     */
    private CompanyDAO() {
        this.queryMapper = QueryMapper.INSTANCE;
        this.companyMapper = CompanyMapper.INSTANCE;
    }

    /**
     * Gets the list of all companies.
     * @param offset the offset
     * @return the company page
     */
    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            ResultSet rs = queryMapper.executeQuery(connection, FIND_ALL);
            while (rs.next()) {
                Company company = companyMapper.createCompany(rs);
                companyList.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return companyList;
    }

    /**
     * Gets the company page.
     * @param offset the offset
     * @return the company page
     */
    public Page<Company> getCompanyPage(int offset) {
        List<Company> companyList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection()) {
            ResultSet rs = queryMapper.executeQuery(connection, FIND_PAGE, COMPANIES_PER_PAGE,
                    offset * COMPANIES_PER_PAGE);
            while (rs.next()) {
                Company company = companyMapper.createCompany(rs);
                companyList.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return new Page<Company>(COMPANIES_PER_PAGE, offset, companyList);
    }

    /**
     * Deletes a company
     * @param id the id of the company to delete
     * @return whether the deletion was succesful
     */
    public boolean deleteCompany(Long id) throws InvalidIdException {
        if (id<0) {
            throw new InvalidIdException();
        }
        try (Connection connection = DataSource.getConnection()) {
            queryMapper.executeUpdate(connection, DELETE_COMPUTERS, id);
            return queryMapper.executeUpdate(connection, DELETE_COMPANY, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}
