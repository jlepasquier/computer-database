package com.excilys.computerdatabase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.model.Company;

@Service("companyService")
public class CompanyService {

    private CompanyDAO companyDAO;

    public CompanyService(CompanyDAO pcompanyDAO) {
        companyDAO = pcompanyDAO;
    }

    /**
     * Gets a company page.
     * @param page the page
     * @return the company page
     */
    public List<Company> getCompanyList(int page) {
        return companyDAO.getCompanyPage(page).getElements();
    }

    /**
     * Gets the company list.
     * @return the company list
     */
    public List<Company> getCompanyList() {
        return companyDAO.getCompanyList();
    }

    public boolean deleteCompany(Long id) {
        return companyDAO.deleteCompany(id);
    }
}
