package fr.excilys.computer_database.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;

@Service
public class CompanyService {
	
	private CompanyDAO companyDao;
	
	@Autowired
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
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