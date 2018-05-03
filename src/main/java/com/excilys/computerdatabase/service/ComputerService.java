package main.java.com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.model.Computer;

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
        try {
            return computerDAO.getComputerPage(page).getElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public boolean updateComputer(Computer cpu) {
        try {
            return computerDAO.updateComputer(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a computer from its id.
     * @param id the id
     */
    public void deleteComputer(long id) {
        try {
            computerDAO.deleteComputer(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
