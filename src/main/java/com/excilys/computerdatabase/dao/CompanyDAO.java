package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.mapper.CompanyMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Company;

@Repository("companyDAO")
public class CompanyDAO {
    
    @Autowired
    private DataSource dataSource;
    
    public CompanyDAO (DataSource pdataSource) {
        dataSource = pdataSource;
    }

    private static final int COMPANIES_PER_PAGE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private static final String FIND_ALL = "SELECT * FROM company";
    private static final String FIND_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id=?";

    /**
     * Gets the list of all companies.
     * @param offset the offset
     * @return the company page
     */
    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = QueryMapper.executeQuery(connection, FIND_ALL);
            while (rs.next()) {
                Company company = CompanyMapper.createCompany(rs);
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

        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = QueryMapper.executeQuery(connection, FIND_PAGE, COMPANIES_PER_PAGE,
                    offset * COMPANIES_PER_PAGE);
            while (rs.next()) {
                Company company = CompanyMapper.createCompany(rs);
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
        boolean result = false;
        if (id < 0) {
            throw new InvalidIdException();
        }
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY,
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            try (PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_COMPUTERS,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                preparedStatement2.setLong(1, id);
                preparedStatement2.executeUpdate();
                preparedStatement.setLong(1, id);

                if (preparedStatement.executeUpdate() == 1) {
                    result = true;
                }
                connection.commit();
            }

            
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            result = false;
        }

        return result;
    }
}
