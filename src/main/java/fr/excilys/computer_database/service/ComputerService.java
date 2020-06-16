package fr.excilys.computer_database.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;

@Service
public class ComputerService {
	
	private ComputerDAO computerDao;
	
	public ComputerService(ComputerDAO computerDao) {
		this.computerDao = computerDao;
	}

	public List<Computer> getComputerList(Paginer paginer) throws DAOConfigurationException {
		return computerDao.computerList(paginer);
	}
	
	public List<Computer> getComputerListInfo(Paginer paginer) throws DAOConfigurationException {
		return computerDao.computerListInfo(paginer);
	}
	
	public void addComputer(Computer computer) throws DAOConfigurationException {
		computerDao.ajouter(computer);
	}

	public void editComputer(Computer computer) throws DAOConfigurationException {
		computerDao.modifier(computer);
	}
	
	public void deleteComputer(int id) throws DAOConfigurationException {
		computerDao.supprimer(id);
	}

	public Computer getComputerById(int id) throws DAOConfigurationException {
		return computerDao.getComputerById(id).get();
	}

	

}
