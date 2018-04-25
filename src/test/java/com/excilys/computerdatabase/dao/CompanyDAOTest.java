package test.java.com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.dao.Page;
import main.java.com.excilys.computerdatabase.model.Company;

/**
 * @author Julien Lepasquier
 *
 */
public class CompanyDAOTest {

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.CompanyDAO#getCompanyPage(int)}.
     *
     * @throws SQLException
     *             the exception
     * @throws IllegalArgumentException
     *             the exception
     */
    @Test
    public void testGetCompanyPage() throws IllegalArgumentException, SQLException {
        Page<Company> page = CompanyDAO.INSTANCE.getCompanyPage(0);
        assertTrue(page.getElements().size() > 0);

    }

    /**
     * Test method for negative ints
     * {@link main.java.com.excilys.computerdatabase.dao.CompanyDAO#getCompanyPage(int)}.
     *
     * @throws SQLException
     *             the exception
     * @throws IllegalArgumentException
     *             the exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCompanyPageNegative() throws IllegalArgumentException, SQLException {
        Page<Company> page = CompanyDAO.INSTANCE.getCompanyPage(-1);
        assertEquals(page.getElements().size(), 0);
    }

}
