package fr.excilys.computer_database.dao;

import java.util.List;
import fr.excilys.computer_database.dto.Computer;

public interface ComputerDao {

	void ajouter( Computer computer );

    List<Computer> lister();
}
