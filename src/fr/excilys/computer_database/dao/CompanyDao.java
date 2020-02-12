package fr.excilys.computer_database.dao;

import java.util.List;
import fr.excilys.computer_database.dto.Company;
import fr.excilys.computer_database.dto.Computer;

public interface CompanyDao {

	List<Company> lister();

	List<Company> lister(int offset, int pas);

}