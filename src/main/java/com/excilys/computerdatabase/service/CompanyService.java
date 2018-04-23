package main.java.com.excilys.computerdatabase.service;

import java.util.List;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.model.Company;

public class CompanyService {
	public CompanyService() {
		
	}
	
	public List<Company> getCompanyList(int offset) {
		try {
			return CompanyDAO.INSTANCE.getCompanyPage(offset).getElements();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
