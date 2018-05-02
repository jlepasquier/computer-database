package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import main.java.com.excilys.computerdatabase.exception.InvalidDateFormatException;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

/**
 * The ComputerMapper singleton.
 */
public enum ComputerMapper {

    /** The singleton instance. */
    INSTANCE;

    /**
     * Creates a computer from a result set.
     * @param rs the result set
     * @return the computer
     * @throws SQLException the SQL exception
     */
    public Computer createComputer(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("cpuname");
        Date introducedDate = rs.getDate("introduced");
        LocalDate introduced = (introducedDate == null) ? null : introducedDate.toLocalDate();
        Date discontinuedDate = rs.getDate("discontinued");
        LocalDate discontinued = (discontinuedDate == null) ? null : discontinuedDate.toLocalDate();
        String companyName = rs.getString("companyname");
        int companyId = rs.getInt("companyid");
        Company company = null;
        if (companyId != 0) {
            company = new Company.Builder(companyId).withName(companyName).build();
        }

        return new Computer.Builder(name).withCompany(company).withIntroduced(introduced).withDiscontinued(discontinued)
                .withId(id).build();
    }

    /**
     * Creates a computer from a result set.
     * @param pcomputerName the name of the computer
     * @param pintroduced the date the computer was introduced
     * @param pdiscontinued the date the computer was discontinued
     * @param pcompanyId the company ID
     * @return the computer
     * @throws IllegalArgumentException the exception
     * @throws InvalidDateFormatException the exception
     * @throws NumberFormatException the exception
     */
    public Computer createComputer(String pcomputerName, String pintroduced, String pdiscontinued, String pcompanyId)
            throws IllegalArgumentException, InvalidDateFormatException, NumberFormatException {

        LocalDate introduced = null;
        LocalDate discontinued = null;
        int companyId;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (pcomputerName == null) {
            throw new IllegalArgumentException();
        }
        
        if (pintroduced != null) {
            try {
                introduced = LocalDate.parse(pintroduced, format);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException();
            }
        }
        
        if (pdiscontinued != null) {
            try {
                introduced = LocalDate.parse(pintroduced, format);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException();
            }
        }
        
        if (pcompanyId == null) {
            throw new IllegalArgumentException();
        } else {
            try {
               companyId = Integer.parseInt(pcompanyId); 
            } catch (NumberFormatException e) {
                throw e;
            }
        }

       
        Company company = null;
        if (companyId != 0) {
            company = new Company.Builder(companyId).build();
        }

        return new Computer.Builder(pcomputerName).withCompany(company).withIntroduced(introduced).withDiscontinued(discontinued).build();
    }

}
