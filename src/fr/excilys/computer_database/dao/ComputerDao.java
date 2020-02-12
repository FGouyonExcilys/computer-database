package fr.excilys.computer_database.dao;

import java.sql.SQLException;
import java.util.List;
import fr.excilys.computer_database.dto.Computer;

public interface ComputerDao {

	void ajouter(Computer computer) throws SQLException;

	void modifier(Computer computer) throws SQLException;

	void supprimer(int id) throws SQLException;

	List<Computer> lister() throws SQLException;

	List<Computer> lister(int début, int fin) throws SQLException;

	String afficherInfoComputer(int id);

}
