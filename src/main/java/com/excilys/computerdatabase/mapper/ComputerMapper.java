package main.java.com.excilys.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import main.java.com.excilys.computerdatabase.exception.InvalidDateFormatException;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

/**
 * The ComputerMapper singleton.
 */
public class ComputerMapper {

    /**
     * Creates a computer from a result set.
     * @param rs the result set
     * @return the computer
     * @throws SQLException the SQL exception
     */
    public static Computer createComputer(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        String name = rs.getString("cpuname");
        Date introducedDate = rs.getDate("introduced");
        LocalDate introduced = (introducedDate == null) ? null : introducedDate.toLocalDate();
        Date discontinuedDate = rs.getDate("discontinued");
        LocalDate discontinued = (discontinuedDate == null) ? null : discontinuedDate.toLocalDate();
        String companyName = rs.getString("companyname");
        Long companyId = rs.getLong("companyid");
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
    public static Computer createComputer(String pcomputerName, String pintroduced, String pdiscontinued,
            String pcompanyId) throws IllegalArgumentException, InvalidDateFormatException, NumberFormatException {

        LocalDate introduced = null;
        LocalDate discontinued = null;
        Long companyId;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (pcomputerName == null) {
            throw new IllegalArgumentException();
        }

        if (StringUtils.isNotBlank(pintroduced)) {
            try {
                introduced = LocalDate.parse(pintroduced, format);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException();
            }
        }

        if (StringUtils.isNotBlank(pdiscontinued)) {
            try {
                discontinued = LocalDate.parse(pdiscontinued, format);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException();
            }
        }

        if (pcompanyId == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                companyId = Long.parseLong(pcompanyId);
            } catch (NumberFormatException e) {
                throw e;
            }
        }

        Company company = null;
        if (companyId != 0) {
            company = new Company.Builder(companyId).build();
        }

        return new Computer.Builder(pcomputerName).withCompany(company).withIntroduced(introduced)
                .withDiscontinued(discontinued).build();
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
    public static Computer createComputer(String pid, String pcomputerName, String pintroduced, String pdiscontinued,
            String pcompanyId) throws IllegalArgumentException, InvalidDateFormatException, NumberFormatException {

        Long id;
        if (pid == null) {
            throw new IllegalArgumentException();
        }

        Computer computer = createComputer(pcomputerName, pintroduced, pdiscontinued, pcompanyId);
        try {
            id = Long.parseLong(pid);
            computer.setId(id);
        } catch (NumberFormatException e) {
            throw e;
        }

        return computer;
    }

}
