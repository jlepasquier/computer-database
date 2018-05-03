package main.java.com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.mapper.CompanyMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.persistence.Database;

/**
 * The CompanyDAO singleton.
 */
public enum CompanyDAO {
    INSTANCE;

    private final Database db;
    private final QueryMapper queryMapper;
    private final CompanyMapper companyMapper;
    
    private static final int COMPANIES_PER_PAGE = 10;
    
    private static final String FIND_PAGE = "SELECT * from company LIMIT ? OFFSET ?";
    private static final String FIND_ALL = "SELECT * from company";


    /**
     * Instantiates a new company DAO.
     */
    CompanyDAO() {
        this.db = Database.INSTANCE;
        this.queryMapper = QueryMapper.INSTANCE;
        this.companyMapper = CompanyMapper.INSTANCE;
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
     * Close connection.
     * @param connection the connection
     * @throws SQLException the SQL exception
     */
    private void closeConnection(Connection connection) throws SQLException {
        db.closeConnection(connection);
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
        Connection connection = null;
        if (offset < 0) {
            throw new IllegalArgumentException();
        } else {
            try {
                connection = getConnection();
                ResultSet rs = queryMapper.executeQuery(connection, FIND_PAGE, COMPANIES_PER_PAGE,
                        offset * COMPANIES_PER_PAGE);
                while (rs.next()) {
                    Company company = companyMapper.createCompany(rs);
                    companyList.add(company);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
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
        Connection connection = null;
        try {
            connection = getConnection();
            ResultSet rs = queryMapper.executeQuery(connection, FIND_ALL);
            while (rs.next()) {
                Company company = companyMapper.createCompany(rs);
                companyList.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return companyList;
    }

}
