package fr.excilys.computer_database.services;

import java.util.ArrayList;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;

public class CompanyService {
	
	private CompanyDAO companyDao;

	private static volatile CompanyService INSTANCE = null;
	
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	
	public final static CompanyService getInstance(CompanyDAO companyDao) {
		if (INSTANCE == null) {
			INSTANCE = new CompanyService(companyDao);
		}
		return INSTANCE;
	}
	
	public void deleteCompany() {
		
	}
	
	public ArrayList<Company> getCompanyList() throws DAOConfigurationException {
		return companyDao.lister();
	}
	
	public ArrayList<Company> getCompanyListPaginer(int offset, int pas) throws DAOConfigurationException {
		return companyDao.lister(offset, pas);
	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {
		return companyDao.getCompanyById(id);
	}

}