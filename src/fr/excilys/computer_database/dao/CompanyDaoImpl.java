package fr.excilys.computer_database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.dto.Company;

public class CompanyDaoImpl implements CompanyDao {

	private static CompanyDaoImpl INSTANCE = null;

	/** Point d'accès pour l'instance unique du singleton */
	public static CompanyDaoImpl getInstance(Dao dao) {
		if (INSTANCE == null) {
			INSTANCE = new CompanyDaoImpl(dao);
		}
		return INSTANCE;
	}

	private Dao dao;

	public CompanyDaoImpl(Dao dao) {
		this.dao = dao;
	}

	@Override
	public List<Company> lister() {

		List<Company> companies = new ArrayList<Company>();
		Statement statement = null;
		ResultSet resultat = null;

		try {

			Connection connexion = dao.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT * FROM company;");

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");

				Company company = new Company();
				company.setId(id);
				company.setName(name);

				companies.add(company);
				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return companies;
	}
	
	@Override
	public List<Company> lister(int offset, int pas) {

		List<Company> companies = new ArrayList<Company>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("SELECT * FROM company LIMIT ?,?;");
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");

				Company company = new Company();
				company.setId(id);
				company.setName(name);

				companies.add(company);

				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;
	}

}
