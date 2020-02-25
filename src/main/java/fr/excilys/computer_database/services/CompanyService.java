package fr.excilys.computer_database.services;

import java.util.ArrayList;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;

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
	
	public ArrayList<Company> getCompanyList() throws ClassNotFoundException, DAOConfigurationException {
		return companyDao.lister();
	}
	
	public ArrayList<Company> getCompanyListPaginer(int offset, int pas) throws ClassNotFoundException, DAOConfigurationException {
		return companyDao.lister(offset, pas);
	}
	
	public Company getCompanyById(int id) throws ClassNotFoundException, DAOConfigurationException {
		return companyDao.getCompanyById(id);
	}

}