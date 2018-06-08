package com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.CDBException;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.validator.IdValidator;
import com.excilys.computerdatabase.validator.InvalidIdException;

/**
 * The Class ComputerService.
 */
@Service("computerService")
public class ComputerService {

    private ComputerDAO computerDAO;

    public ComputerService(ComputerDAO pcomputerDAO) {
        computerDAO = pcomputerDAO;
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
            throw new InvalidIdException();
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
    public boolean deleteComputers(String ids) throws CDBException {
        return computerDAO.deleteComputers(IdValidator.checkList(ids));
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
    
    /**
     * Gets the number of computers in the search query.
     * @return the number of computers in the search query
     * @throws CDBException
     */
    public Long getSearchComputerCount(String search) throws CDBException {
        Optional<Long> count = computerDAO.getSearchComputerCount(search);
        if (count.isPresent()) {
            return count.get();
        } else {
            throw new CDBException();
        }
    }
    
    /**
     * Gets the number of computers in the database.
     * @return the number of computers in the database
     * @throws CDBException
     */
    public Long getSearchComputerPageCount(String search) throws CDBException {
        Optional<Long> count = computerDAO.getSearchComputerPageCount(search);
        if (count.isPresent()) {
            return count.get();
        } else {
            throw new CDBException();
        }
    }

}
