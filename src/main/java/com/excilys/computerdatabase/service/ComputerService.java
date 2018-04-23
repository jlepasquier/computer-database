package main.java.com.excilys.computerdatabase.service;

import java.util.List;

import main.java.com.excilys.computerdatabase.dao.ComputerDAO;
import main.java.com.excilys.computerdatabase.model.Computer;


/**
 * The Class ComputerService.
 */
public class ComputerService {

    /**
     * Instantiates a new computer service.
     */
    public ComputerService() {

    }

    /**
     * Gets the computer list from database.
     *
     * @param page the page number
     * @return the computer list
     */
    public List<Computer> getComputerList(int page) {
        try {
            return ComputerDAO.INSTANCE.getComputerPage(page).getElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a computer from its id.
     *
     * @param id the id
     * @return the computer
     */
    public Computer getComputer(int id) {
        try {
            return ComputerDAO.INSTANCE.getComputer(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new computer to store in the database.
     *
     * @param cpu the cpu
     * @return the int
     */
    public int createComputer(Computer cpu) {
        try {
            return ComputerDAO.INSTANCE.createComputer(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates a computer in the database.
     *
     * @param cpu the cpu
     * @return the int
     */
    public int updateComputer(Computer cpu) {
        try {
            return ComputerDAO.INSTANCE.updateComputer(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Deletes a computer from its id.
     *
     * @param id the id
     * @return the int
     */
    public int deleteComputer(int id) {
        try {
            return ComputerDAO.INSTANCE.deleteComputer(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
