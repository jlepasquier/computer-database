package main.java.com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.model.Company;

/**
 * The Class CompanyService.
 */
public class CompanyService {

    public CompanyService() {
    }

    /**
     * Gets a company page.
     * @param page the page
     * @return the company page
     */
    public List<Company> getCompanyList(int page) {
        try {
            return CompanyDAO.INSTANCE.getCompanyPage(page).getElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Company>();
    }

    /**
     * Gets the company list.
     * @return the company list
     */
    public List<Company> getCompanyList() {
        try {
            return CompanyDAO.INSTANCE.getCompanyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Company>();
    }
}
