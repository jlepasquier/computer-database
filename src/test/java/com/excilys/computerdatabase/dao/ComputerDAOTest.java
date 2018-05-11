package test.java.com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.dao.Page;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

/**
 * @author Julien Lepasquier
 */
public class ComputerDAOTest {

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputerPage(int)}.
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    @Test
    public void testGetComputerPage() throws IllegalArgumentException, SQLException {
        Page<Computer> page = ComputerDAO.INSTANCE.getComputerPage(0);
        assertTrue(page.getElements().size() > 0);
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputerPage(int)}.
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerPageNegativeOffset() throws IllegalArgumentException, SQLException {
        Page<Computer> page = ComputerDAO.INSTANCE.getComputerPage(-1);
        assertEquals(page.getElements().size(), 0);
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws SQLException the exception
     */
    @Test
    public void testGetComputer() throws SQLException {
        assertNotNull(ComputerDAO.INSTANCE.getComputer(2));
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws SQLException the exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerNegativeId() throws SQLException {
        long id = -1l;
        //Computer cpu = ComputerDAO.INSTANCE.getComputer(id);
        //assertNull(cpu);
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws SQLException the exception
     */
    @Test
    public void testGetComputerInvalidId() throws SQLException {
        int id = 10000;
        //Computer cpu = ComputerDAO.INSTANCE.getComputer(id);
        //assertNull(cpu);
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the CreateComputer method.
     * @throws SQLException exception
     */
    @Test
    public void testCreateComputer() throws SQLException {
        /*DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Company company = new Company.Builder(1).withName("Apple Inc.").build();
        LocalDate introduced = LocalDate.parse("17/05/1995", format);
        LocalDate discontinued = LocalDate.parse("17/05/1996", format);

        Computer cpu = new Computer.Builder("CreateEmptyCpu").withId(10).withCompany(company).withIntroduced(introduced)
                .withDiscontinued(discontinued).build();

        long id = ComputerDAO.INSTANCE.createComputer(cpu);
        Computer cpuFromDatabase = ComputerDAO.INSTANCE.getComputer(id);

        assertEquals(cpuFromDatabase, cpu);*/
    }

    /**
     * Test for the CreateComputer method.
     * @throws SQLException exception
     */
    @Test
    public void testCreateComputerIncompleteComputer() throws SQLException {
       /* Computer cpu = new Computer.Builder("CreateEmptyCpu2").build();
        long id = ComputerDAO.INSTANCE.createComputer(cpu);
        Computer cpuFromDatabase = ComputerDAO.INSTANCE.getComputer(id);

        assertEquals(cpu, cpuFromDatabase);*/
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the UpdateComputer method.
     * @throws SQLException exception
     * @throws IllegalArgumentException exception
     * @throws InvalidComputerIdException exception
     */
    /*@Test
    public void testUpdateComputer() throws IllegalArgumentException, SQLException, InvalidComputerIdException {
        long id = 2;
        Computer cpu = new Computer.Builder("TestUpdate").withId(id).build();
        boolean result = ComputerDAO.INSTANCE.updateComputer(cpu);
        assertTrue(result);
        Computer cpuFromDatabase = ComputerDAO.INSTANCE.getComputer(id);

        assertEquals(cpu, cpuFromDatabase);
    }*/

    /**
     * Test for the UpdateComputer method. Should fail because we didn't specify the
     * id.
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */
    /*@Test(expected = InvalidComputerIdException.class)
    public void testUpdateComputerWithoutId() throws SQLException, InvalidComputerIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").build();
        assertFalse(ComputerDAO.INSTANCE.updateComputer(cpu));
    }*/

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */
    /*@Test
   public void testUpdateComputerInvalidId() throws SQLException, InvalidComputerIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").withId(9999999).build();
        assertFalse(ComputerDAO.INSTANCE.updateComputer(cpu));
    }*/

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */
    /*@Test(expected = InvalidComputerIdException.class)
    public void testUpdateComputerNegativeId() throws SQLException, InvalidComputerIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").withId(-10).build();
        assertFalse(ComputerDAO.INSTANCE.updateComputer(cpu));
    }*/

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the DeleteComputer method.
     * @throws InvalidComputerIdException exception
     * @throws SQLException exception
     */
    /*@Test
    public void testDeleteComputer() throws SQLException, InvalidComputerIdException {
        //assertTrue(ComputerDAO.INSTANCE.deleteComputer(1));
    }*/

    /**
     * Test for the DeleteComputer method.
     * @throws InvalidComputerIdException exception
     * @throws SQLException exception
     */
    /*@Test(expected = InvalidComputerIdException.class)
    public void testDeleteComputerNegativeId() throws SQLException, InvalidComputerIdException {
        //assertFalse(ComputerDAO.INSTANCE.deleteComputer(-10000));
    }*/

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */
    /*@Test
    public void testDeleteComputerInvalidId() throws SQLException, InvalidComputerIdException {
        //assertFalse(ComputerDAO.INSTANCE.deleteComputer(10000));
    }*/
}
