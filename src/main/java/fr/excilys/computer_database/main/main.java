package fr.excilys.computer_database.main;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.services.ComputerService;
import fr.excilys.computer_database.utilisateur.CLI;

public class main {

	public static void main(String[] args) {

		CLI.affichage();
		
		ComputerService.getInstance(ComputerDAO.getInstance());
	}
}
