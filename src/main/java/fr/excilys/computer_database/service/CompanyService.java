package fr.excilys.computer_database.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;

@Service
public class CompanyService {
	
	private CompanyDAO companyDao;
	
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	
	public void deleteCompany() {
		
	}
	
	public List<Company> getCompanyList() throws DAOConfigurationException {
		return companyDao.lister();
	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {
		return companyDao.getCompanyById(id).get();
	}

}