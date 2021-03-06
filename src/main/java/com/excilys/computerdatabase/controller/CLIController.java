package main.java.com.excilys.computerdatabase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;
import main.java.com.excilys.computerdatabase.ui.CLI;

@Controller
public class CLIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLIController.class);

    private CLI cli;
    private ComputerService computerService;
    private CompanyService companyService;

    public CLIController(CLI cli, ComputerService computerService, CompanyService companyService) {
        this.cli = cli;
        this.computerService = computerService;
        this.companyService = companyService;
    }

    /**
     * Start, entry point.
     */
    public void start() {
        cli.initView();
        do {
            cli.showActions();
        } while (handleAction(cli.readInt()));
        cli.stop();
    }

    /**
     * Handles user action.
     * @param action the action
     * @return true, if successful
     */
    public boolean handleAction(int action) {
        switch (action) {
        case 0:
            return false;
        case 1:
            findEveryComputer();
            break;
        case 2:
            find(cli.readLong());
            break;
        case 3:
            delete(cli.readLong());
            break;
        case 4:
            update(cli.readCpuToUpdate());
            break;
        case 5:
            create(cli.readCpuToCreate());
            break;
        case 6:
            findEveryCompany();
            break;
        case 7:
            deleteCompany(cli.readLong());
            break;
        default:
            break;
        }
        return true;
    }

    /**
     * * Service calls **.
     */
    public void findEveryComputer() {
        int currentPage = 0;
        boolean exit = false;
        do {
            List<Computer> cpuList = computerService.getComputerList(currentPage);
            if (cpuList.isEmpty() && (currentPage > 0)) {
                currentPage -= 1;
                cpuList = computerService.getComputerList(currentPage);
            }
            cli.printList(cpuList);
            cli.printPageNavigationIndication();

            String action = cli.readString();

            if (action.equals("m")) {
                exit = true;
            } else if (action.equals("s") && !cpuList.isEmpty()) {
                currentPage += 1;
            } else if (action.equals("p") && (currentPage > 0)) {
                currentPage -= 1;
            }
        } while (!exit);
    }

    /**
     * Finds every company.
     */
    public void findEveryCompany() {
        int currentPage = 0;
        boolean exit = false;
        do {
            List<Company> companyList = companyService.getCompanyList(currentPage);
            if (companyList.isEmpty() && (currentPage > 0)) {
                currentPage -= 1;
                companyList = companyService.getCompanyList(currentPage);
            }
            cli.printList(companyList);
            cli.printPageNavigationIndication();

            String action = cli.readString();

            if (action.equals("m")) {
                exit = true;
            } else if (action.equals("s") && !companyList.isEmpty()) {
                currentPage += 1;
            } else if (action.equals("p") && (currentPage > 0)) {
                currentPage -= 1;
            }
        } while (!exit);
    }

    /**
     * Deletes a company.
     * @param id the id
     */
    public void deleteCompany(Long id) {
        companyService.deleteCompany(id);
    }

    /**
     * Find a computer.
     * @param id the id
     * @throws CDBException
     */
    public void find(Long id) {
        try {
            Computer cpu = computerService.getComputer(id);
            cli.printComputer(cpu);
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Deletes a computer.
     * @param id the id
     */
    public void delete(Long id) {
        try {
            computerService.deleteComputers(id.toString());
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Updates a computer.
     * @param cpu the computer
     * @throws CDBException
     */
    public void update(Computer cpu) {
        try {
            computerService.updateComputer(cpu);
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Creates a new computer.
     * @param cpu the computer
     */
    public void create(Computer cpu) {
        try {
            computerService.createComputer(cpu);
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
