package fr.excilys.computer_database.dao;

import java.util.List;
import fr.excilys.computer_database.dto.Company;

public interface CompanyDao {

    void ajouter( Company company );

    List<Company> lister();

}