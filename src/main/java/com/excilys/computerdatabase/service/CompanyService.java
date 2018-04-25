package main.java.com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.model.Company;

/**
 * The Class CompanyService.
 */
public class CompanyService {

    /**
     * Instantiates a new company service.
     */
    public CompanyService() {

    }

    /**
     * Gets the company list.
     *
     * @param page the page
     * @return the company list
     */
    public List<Company> getCompanyList(int page) {
        try {
            return CompanyDAO.INSTANCE.getCompanyPage(page).getElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Company>();
    }
}
