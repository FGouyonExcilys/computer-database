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
		ArrayList<Computer> listComput = computerDao.lister();
		return listComput;
	}

	public ArrayList<Computer> getComputerListPaginer(int offset, int pas)
			throws ClassNotFoundException, SQLException {
		ArrayList<Computer> listComput = computerDao.lister(offset, pas);
		return listComput;
	}

	public void editComputer(Computer comp) throws ClassNotFoundException, SQLException {
		computerDao.modifier(comp);
	}

	public String findComputerById(int id) throws ClassNotFoundException {
		String affichage = computerDao.afficherInfoComputer(id);
		return affichage;
	}

	public void deleteComputer(int id) throws ClassNotFoundException, SQLException {
		computerDao.supprimer(id);
	}

}
