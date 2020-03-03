package fr.excilys.computer_database.services;

import java.util.ArrayList;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Computer;

public class ComputerService {
	
	private ComputerDAO computerDao;

	private static volatile ComputerService INSTANCE = null;
	
	public ComputerService(ComputerDAO computerDao) {
		this.computerDao = computerDao;
	}
	
	public final static ComputerService getInstance(ComputerDAO computerDao) {
		if (INSTANCE == null) {
			INSTANCE = new ComputerService(computerDao);
		}
		return INSTANCE;
	}

	public ArrayList<Computer> getComputerList() throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.lister();
	}

	public ArrayList<Computer> getComputerListPaginer(String orderBy, int offset, int pas) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.lister(orderBy,offset, pas);
	}
	
	public ArrayList<Computer> getComputerListSearched(String search) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.listSearch(search);
	}
	
	public ArrayList<Computer> getComputerListSearchedPaginer(String orderBy, String search, int offset, int pas) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.listSearch(orderBy, search, offset, pas);
	}
	
	public int addComputer(Computer computer) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.ajouter(computer);
	}

	public int editComputer(Computer computer) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.modifier(computer);
	}
	
	public int deleteComputer(int id) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.supprimer(id);
	}

	public Computer getComputerById(int id) throws ClassNotFoundException, DAOConfigurationException {
		return computerDao.getComputerById(id);
	}

	

}
