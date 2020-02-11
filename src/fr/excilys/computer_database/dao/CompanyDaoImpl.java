package fr.excilys.computer_database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.dto.Company;

public class CompanyDaoImpl implements CompanyDao {
    
	private Dao dao;

    CompanyDaoImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void ajouter(Company company) {
    	
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = dao.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO company(id, name) VALUES(?, ?);");
            preparedStatement.setInt(1, company.getId());
            preparedStatement.setString(2, company.getName());

            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
    @Override
    public List<Company> lister() {
    	
        List<Company> companies = new ArrayList<Company>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
        	
            connexion = dao.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT id, prenom FROM company;");

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String name = resultat.getString("name");

                Company company = new Company();
                company.setId(id);
                company.setName(name);

                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

}
