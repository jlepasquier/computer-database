package test.java.com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.dao.Page;
import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.spring.AppConfig;

/**
 * @author Julien Lepasquier
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class, loader=AnnotationConfigContextLoader.class)
public class CompanyDAOTest {
    
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ComputerDAO computerDAO;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        companyDAO = null;
        computerDAO = null;
    }

    /*** GetCompanyList ***/
    @Test
    public void testGetCompanyList() {
        assertTrue(true);
        List<Company> list = companyDAO.getCompanyList();
        assertTrue(list.size() > 0);
    }

    /*** GetCompanyPage ***/
    @Test
    public void testGetCompanyPage() {
        Page<Company> page = companyDAO.getCompanyPage(0);
        assertTrue(page.getElements().size() > 0);
    }

    @Test
    public void testGetCompanyPageNegativeOffset() {
        int offset = -1;
        Page<Company> page = companyDAO.getCompanyPage(offset);
        assertTrue(page.getElements().size() == 0);
    }

    @Test
    public void testGetCompanyPageNegativeBigOffset() {
        int offset = 100000;
        Page<Company> page = companyDAO.getCompanyPage(offset);
        assertTrue(page.getElements().size() == 0);
    }

    /*** deleteCompany ***/
    @Test
    public void testDeleteCompany() throws InvalidIdException {
        Long id = 1L;
        Optional<Long> computersBefore = computerDAO.getComputerCount();
        int companiesBefore = companyDAO.getCompanyList().size();

        boolean result = companyDAO.deleteCompany(id);
        assertTrue(result);

        Optional<Long> computersAfter = computerDAO.getComputerCount();
        int companiesAfter = companyDAO.getCompanyList().size();

        assertTrue(computersBefore.get() > computersAfter.get());
        assertTrue(companiesBefore > companiesAfter);
    }

    @Test(expected = InvalidIdException.class)
    public void testDeleteCompanyNegativeId() throws InvalidIdException {
        Long id = -1L;
        Long computersBefore = computerDAO.getComputerCount().get();
        int companiesBefore = companyDAO.getCompanyList().size();

        boolean result = companyDAO.deleteCompany(id);
        assertFalse(result);

        Long computersAfter = computerDAO.getComputerCount().get();
        int companiesAfter = companyDAO.getCompanyList().size();

        assertTrue(computersBefore == computersAfter);
        assertTrue(companiesBefore == companiesAfter);
    }

    @Test
    public void testDeleteCompanyBigId() throws InvalidIdException {
        Long id = 1000L;
        Long computersBefore = computerDAO.getComputerCount().get();
        int companiesBefore = companyDAO.getCompanyList().size();

        boolean result = companyDAO.deleteCompany(id);
        assertFalse(result);

        Long computersAfter = computerDAO.getComputerCount().get();
        int companiesAfter = companyDAO.getCompanyList().size();

        assertTrue(computersBefore == computersAfter);
        assertTrue(companiesBefore == companiesAfter);
    }

}
