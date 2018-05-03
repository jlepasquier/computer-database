package main.java.com.excilys.computerdatabase.controller;

import java.util.List;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;
import main.java.com.excilys.computerdatabase.ui.CLI;

/**
 * The Class CLIController.
 */
public class CLIController {

    private final CLI cli;
    private final ComputerService computerService;
    private final CompanyService companyService;

    /**
     * Instantiates a new CLI controller.
     * @param cli the command line interface
     * @param computerService the computer service
     * @param companyService the company service
     */
    public CLIController(CLI cli) {
        this.cli = cli;
        this.computerService = new ComputerService();
        this.companyService = new CompanyService();
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
            find(cli.readInt());
            break;
        case 3:
            delete(cli.readInt());
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
     * Find a computer.
     * @param id the id
     */
    public void find(int id) {
        Computer cpu = computerService.getComputer(id);
        cli.printComputer(cpu);
    }

    /**
     * Deletes a computer.
     * @param id the id
     */
    public void delete(int id) {
        computerService.deleteComputer(id);
    }

    /**
     * Updates a computer.
     * @param cpu the computer
     */
    public void update(Computer cpu) {
        computerService.updateComputer(cpu);
    }

    /**
     * Creates a new computer.
     * @param cpu the computer
     */
    public void create(Computer cpu) {
        computerService.createComputer(cpu);
    }

}
