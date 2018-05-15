package test.java.com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.dao.Page;
import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

/**
 * @author Julien Lepasquier
 */
public class ComputerDAOTest {

    private ComputerDAO computerDAO;

    @Before
    public void setUp() {
        computerDAO = ComputerDAO.INSTANCE;
    }

    @After
    public void tearDown() {
        computerDAO = null;
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputerPage(int)}.
     * @throws SQLException the exception
     * @throws IllegalArgumentException the exception
     */
    @Test
    public void testGetComputerPage() throws IllegalArgumentException, SQLException {
        Page<Computer> page = computerDAO.getComputerPage(0);
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
        Page<Computer> page = computerDAO.getComputerPage(-1);
        assertEquals(page.getElements().size(), 0);
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws InvalidIdException
     */
    @Test
    public void testGetComputer() throws InvalidIdException {
        assertNotNull(computerDAO.getComputer(2));
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws InvalidIdException
     * @throws SQLException the exception
     */

    @Test(expected = InvalidIdException.class)
    public void testGetComputerNegativeId() throws InvalidIdException {
        long id = -1l;
        Optional<Computer> cpu = computerDAO.getComputer(id);
        assertFalse(cpu.isPresent());
    }

    /**
     * Test method for
     * {@link main.java.com.excilys.computerdatabase.dao.ComputerDAO#getComputer(int)}.
     * @throws SQLException the exception
     */
    @Test
    public void testGetComputerInvalidId() throws InvalidIdException {
        int id = 10000;
        Optional<Computer> cpu = computerDAO.getComputer(id);
        assertFalse(cpu.isPresent());
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the CreateComputer method.
     * @throws InvalidIdException
     */
    @Test
    public void testCreateComputer() throws InvalidIdException {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Company company = new Company.Builder(2L).withName("Thinking Machines").build();
        LocalDate introduced = LocalDate.parse("17/05/1995", format);
        LocalDate discontinued = LocalDate.parse("17/05/1996", format);

        Computer cpu = new Computer.Builder("CreateEmptyCpu").withId(10L).withCompany(company)
                .withIntroduced(introduced).withDiscontinued(discontinued).build();

        Optional<Long> id = computerDAO.createComputer(cpu);
        assertTrue(id.isPresent());

        Optional<Computer> cpuFromDatabase = computerDAO.getComputer(id.get());
        assertTrue(cpuFromDatabase.isPresent());

        assertEquals(cpu, cpuFromDatabase.get());
    }

    /**
     * Test for the CreateComputer method.
     * @throws InvalidIdException exception
     */
    @Test
    public void testCreateComputerIncompleteComputer() throws InvalidIdException {

        Computer cpu = new Computer.Builder("CreateEmptyCpu2").build();
        Optional<Long> id = computerDAO.createComputer(cpu);
        assertTrue(id.isPresent());

        Optional<Computer> cpuFromDatabase = computerDAO.getComputer(id.get());
        assertTrue(cpuFromDatabase.isPresent());

        assertEquals(cpu, cpuFromDatabase.get());
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the UpdateComputer method.
     * @throws SQLException exception
     * @throws IllegalArgumentException exception
     * @throws InvalidComputerIdException exception
     */

    @Test
    public void testUpdateComputer() throws InvalidIdException {
        long id = 2;
        Computer cpu = new Computer.Builder("TestUpdate").withId(id).build();

        boolean result = computerDAO.updateComputer(cpu);
        assertTrue(result);

        Optional<Computer> cpuFromDatabase = computerDAO.getComputer(id);
        assertTrue(cpuFromDatabase.isPresent());
        assertEquals(cpu, cpuFromDatabase.get());
    }

    /**
     * Test for the UpdateComputer method. Should fail because we didn't specify the
     * id.
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */

    @Test(expected = InvalidIdException.class)
    public void testUpdateComputerWithoutId() throws InvalidIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").build();
        assertFalse(computerDAO.updateComputer(cpu));
    }

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */

    @Test
    public void testUpdateComputerInvalidId() throws InvalidIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").withId(9999999L).build();
        assertFalse(computerDAO.updateComputer(cpu));
    }

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */

    @Test(expected = InvalidIdException.class)
    public void testUpdateComputerNegativeId() throws SQLException, InvalidIdException {
        Computer cpu = new Computer.Builder("TestUpdateFail").withId(-10L).build();
        assertFalse(computerDAO.updateComputer(cpu));
    }

    /*******************************************************************************/
    /*******************************************************************************/

    /**
     * Test for the DeleteComputer method.
     * @throws InvalidComputerIdException exception
     * @throws SQLException exception
     */
    /*
     * @Test public void testDeleteComputer() throws SQLException,
     * InvalidComputerIdException {
     * //assertTrue(ComputerDAO.INSTANCE.deleteComputer(1)); }
     */

    /**
     * Test for the DeleteComputer method.
     * @throws InvalidComputerIdException exception
     * @throws SQLException exception
     */
    /*
     * @Test(expected = InvalidComputerIdException.class) public void
     * testDeleteComputerNegativeId() throws SQLException,
     * InvalidComputerIdException {
     * //assertFalse(ComputerDAO.INSTANCE.deleteComputer(-10000)); }
     */

    /**
     * Test for the UpdateComputer method. Should fail because id isnt in DB
     * @throws SQLException exception
     * @throws InvalidComputerIdException exception
     */
    /*
     * @Test public void testDeleteComputerInvalidId() throws SQLException,
     * InvalidComputerIdException {
     * //assertFalse(ComputerDAO.INSTANCE.deleteComputer(10000)); }
     */
}
