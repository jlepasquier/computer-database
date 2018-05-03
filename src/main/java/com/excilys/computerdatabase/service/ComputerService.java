package main.java.com.excilys.computerdatabase.service;

import java.util.List;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
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
     */
    public Computer getComputer(long id) {
        try {
            return computerDAO.getComputer(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new computer to store in the database.
     * @param cpu the cpu
     * @return the id of the computer we created
     */
    public long createComputer(Computer cpu) {
        try {
            return computerDAO.createComputer(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
     */
    public int getComputerCount() {
        try {
            return computerDAO.getComputerCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the number of computer pages.
     * @return Gets the number of pages.
     */
    public int getComputerPageCount() {
        try {
            return computerDAO.getComputerPageCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
