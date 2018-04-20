package com.excilys.computerdatabase.main.java.service;

import java.util.List;

import com.excilys.computerdatabase.main.java.dao.CompanyDAO;
import com.excilys.computerdatabase.main.java.model.Company;

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
