package fr.excilys.computer_database.services;

import java.util.ArrayList;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.model.Company;

public class CompanyService {
	
	private CompanyDAO companyDao;

	private static volatile CompanyService INSTANCE = null;
	
	private CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	
	public final static CompanyService getInstance(CompanyDAO companyDao) {
		if (INSTANCE == null) {
			INSTANCE = new CompanyService(companyDao);
		}
		return INSTANCE;
	}
	
	public ArrayList<Company> getCompanyList() throws ClassNotFoundException {
		return companyDao.lister();
	}


}