package fr.excilys.computer_database.service;

import java.util.ArrayList;

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

	public ArrayList<Computer> getComputerList() throws DAOConfigurationException {
		return computerDao.lister();
	}

	public ArrayList<Computer> getComputerListPaginer(Paginer paginer) throws DAOConfigurationException {
		return computerDao.lister(paginer);
	}
	
	public ArrayList<Computer> getComputerListSearched(String search) throws DAOConfigurationException {
		return computerDao.listSearch(search);
	}
	
	public ArrayList<Computer> getComputerListSearchedPaginer(Paginer paginer) throws DAOConfigurationException {
		return computerDao.listSearch(paginer);
	}
	
	public int addComputer(Computer computer) throws DAOConfigurationException {
		return computerDao.ajouter(computer);
	}

	public int editComputer(Computer computer) throws DAOConfigurationException {
		return computerDao.modifier(computer);
	}
	
	public int deleteComputer(int id) throws DAOConfigurationException {
		return computerDao.supprimer(id);
	}

	public Computer getComputerById(int id) throws DAOConfigurationException {
		return computerDao.getComputerById(id);
	}

	

}
