package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final String FIND_PAGE = "SELECT * from company LIMIT ? OFFSET ?";
    private static final String FIND_ALL = "SELECT * from company";

    /**
     * Instantiates a new company DAO.
     */
    CompanyDAO() {
        this.queryMapper = QueryMapper.INSTANCE;
        this.companyMapper = CompanyMapper.INSTANCE;
    }


    /**
     * Gets the company page.
     * @param offset the offset
     * @return the company page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public Page<Company> getCompanyPage(int offset) throws SQLException, IllegalArgumentException {
        List<Company> companyList = new ArrayList<>();
        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try (Connection connection = DataSource.getConnection()) {
                ResultSet rs = queryMapper.executeQuery(connection, FIND_PAGE, COMPANIES_PER_PAGE,
                        offset * COMPANIES_PER_PAGE);
                while (rs.next()) {
                    Company company = companyMapper.createCompany(rs);
                    companyList.add(company);
                }
            } catch (SQLException e) {
                LOGGER.error("SQL error");
                e.printStackTrace();
            }
        }

        return new Page<Company>(COMPANIES_PER_PAGE, offset, companyList);
    }

    /**
     * Gets the list of all companies.
     * @param offset the offset
     * @return the company page
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    public List<Company> getCompanyList() throws SQLException, IllegalArgumentException {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            ResultSet rs = queryMapper.executeQuery(connection, FIND_ALL);
            while (rs.next()) {
                Company company = companyMapper.createCompany(rs);
                companyList.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyList;
    }

}
