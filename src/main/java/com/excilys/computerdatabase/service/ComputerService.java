package main.java.com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.validator.ComputerValidator;
import main.java.com.excilys.computerdatabase.validator.IdValidator;

/**
 * The Class ComputerService.
 */
public class ComputerService {

    private final ComputerDAO computerDAO;

    public ComputerService() {
        this.computerDAO = ComputerDAO.INSTANCE;
    }

    /**
     * Gets the computer list from database.
     * @param page the page number
     * @return the computer list
     */
    public List<Computer> getComputerList(int page) {
        return computerDAO.getComputerPage(page).getElements();
    }
    
    /**
     * Gets the computer list from database.
     * @param page the page number
     * @return the computer list
     */
    public List<Computer> searchComputer(String research, int offset) {
        return computerDAO.searchComputer(research, offset).getElements();
    }

    /**
     * Gets a computer from its id.
     * @param id the id
     * @return the computer
     * @throws CDBException
     */
    public Computer getComputer(long id) throws CDBException {
        Optional<Computer> cpu = computerDAO.getComputer(id);
        if (cpu.isPresent()) {
            return cpu.get();
        } else {
            throw new CDBException();
        }
    }

    /**
     * Creates a new computer to store in the database.
     * @param cpu the cpu
     * @return the id of the computer we created
     * @throws CDBException
     */
    public long createComputer(Computer cpu) throws CDBException {

        ComputerValidator.check(cpu);

        Optional<Long> id = computerDAO.createComputer(cpu);
        if (id.isPresent()) {
            return id.get();
        } else {
            throw new CDBException();
        }

    }

    /**
     * Updates a computer in the database.
     * @param cpu the cpu
     * @return the id of the computer we updated
     */
    public boolean updateComputer(Computer cpu) throws CDBException {
        ComputerValidator.check(cpu);
        return computerDAO.updateComputer(cpu);
    }

    /**
     * Deletes a computer from its id.
     * @param id the id
     * @throws CDBException
     */
    public void deleteComputer(long id) throws CDBException {
        IdValidator.check(id);
        computerDAO.deleteComputer(id);
    }

    /**
     * Gets the number of computers in the database.
     * @return the number of computers in the database
     * @throws CDBException
     */
    public Long getComputerCount() throws CDBException {
        Optional<Long> count = computerDAO.getComputerCount();
        if (count.isPresent()) {
            return count.get();
        } else {
            throw new CDBException();
        }
    }

    /**
     * Gets the number of computer pages.
     * @return Gets the number of pages.
     * @throws CDBException
     */
    public Long getComputerPageCount() throws CDBException {
        Optional<Long> count = computerDAO.getComputerPageCount();
        if (count.isPresent()) {
            return count.get();
        } else {
            throw new CDBException();
        }

    }

}
