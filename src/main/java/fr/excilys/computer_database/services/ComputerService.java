package fr.excilys.computer_database.services;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.model.Computer;

public class ComputerService {
	
	private ComputerDAO computerDao;

	private static volatile ComputerService INSTANCE = null;
	
	private ComputerService(ComputerDAO computerDao) {
		this.computerDao = computerDao;
	}
	
	public final static ComputerService getInstance(ComputerDAO computerDao) {
		if (INSTANCE == null) {
			INSTANCE = new ComputerService(computerDao);
		}
		return INSTANCE;
	}

	public ArrayList<Computer> getComputerList() throws ClassNotFoundException, SQLException {
		return computerDao.lister();
	}

	public ArrayList<Computer> getComputerListPaginer(int offset, int pas) throws ClassNotFoundException, SQLException {
		return computerDao.lister(offset, pas);
	}

	public void editComputer(Computer computer) throws ClassNotFoundException, SQLException {
		computerDao.modifier(computer);
	}

	public String findComputerById(int id) throws ClassNotFoundException {
		return computerDao.afficherInfoComputer(id);
	}

	public void deleteComputer(int id) throws ClassNotFoundException, SQLException {
		computerDao.supprimer(id);
	}

}
