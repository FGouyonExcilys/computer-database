package fr.excilys.computer_database.dao;

import java.sql.*;
import java.util.ArrayList;

import fr.excilys.computer_database.model.Company;

public final class CompanyDAO {

	private final static String LISTER = "SELECT * FROM company;";
	private final static String LISTER_LIMIT = "SELECT * FROM company LIMIT ?,?;";
	
	
	private static volatile CompanyDAO INSTANCE = null;

	/** Point d'accès pour l'instance unique du singleton */
	public final static CompanyDAO getInstance(DAO dao) {
		if (INSTANCE == null) {
			INSTANCE = new CompanyDAO(dao);
		}
		return INSTANCE;
	}

	private DAO dao;

	public CompanyDAO(DAO dao) {
		this.dao = dao;
	}

	public ArrayList<Company> lister() {

		ArrayList<Company> companies = new ArrayList<Company>();
		Statement statement = null;
		ResultSet resultat = null;

		try {

			Connection connexion = dao.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery(LISTER);

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");

				Company company = new Company.CompanyBuilder().setId(id).setName(name).build();

				companies.add(company);
				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return companies;
	}
	
	public ArrayList<Company> lister(int offset, int pas) {

		ArrayList<Company> companies = new ArrayList<Company>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement(LISTER_LIMIT);
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				
				Company company = new Company.CompanyBuilder().setId(id).setName(name).build();

				companies.add(company);

				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;
	}

}
