package main.java.com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.computerdatabase.model.Company;

/**
 * The CompanyMapper singleton.
 */
public class CompanyMapper {

    /**
     * Creates a company from a result set.
     * @param rs the result set
     * @return the company
     * @throws SQLException the SQL exception
     */
    public static Company createCompany(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Company.Builder(id).withName(name).build();
    }
}
