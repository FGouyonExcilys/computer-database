package fr.excilys.computer_database.dao;

import java.util.List;
import fr.excilys.computer_database.dto.Computer;

public interface ComputerDao {

	void ajouter( Computer computer );
	
	void modifier( Computer computer );
	
	void supprimer(int id);
	
    List<Computer> lister();

	String afficherInfoComputer(int id);

	

	
}
