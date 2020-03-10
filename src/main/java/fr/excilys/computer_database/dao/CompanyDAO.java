package fr.excilys.computer_database.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.utilisateur.Requete;

@Repository
public class CompanyDAO {
	
//	private static volatile CompanyDAO INSTANCE = null;
//
//	/** Point d'accès pour l'instance unique du singleton */
//	public final static CompanyDAO getInstance() {
//		if (INSTANCE == null) {
//			INSTANCE = new CompanyDAO();
//		}
//		return INSTANCE;
//	}

	public CompanyDAO() {
		super();
	}

	

	public void deleteCompany(int companyId) {
		
		Connection connection = DAOHikari.getInstance().getConnection();
		
		try (PreparedStatement statementComputerListByCompanyId = connection.prepareStatement(Requete.COMPUTER_LIST_BY_COMPANY_ID.getMessage());
			 PreparedStatement statementDeleteCompany = connection.prepareStatement(Requete.DELETE_COMPANY.getMessage());
			 PreparedStatement statementDeleteComputer = connection.prepareStatement(Requete.DELETE_COMPUTER_FOR_DELETE_COMPANY.getMessage())) {
			
			connection.setAutoCommit(false);
			
			statementComputerListByCompanyId.setInt(1, companyId);
			
			if(statementComputerListByCompanyId.executeQuery().next()) {
				statementDeleteComputer.setInt(1, companyId);
				statementDeleteComputer.executeUpdate();
			} 
			
			statementDeleteCompany.setInt(1, companyId);
			statementDeleteCompany.executeUpdate();
			
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException eSQL) {
			try {
				connection.rollback();
			} catch (SQLException eSQL1) {
				Loggers.afficherMessageError("In connection " + eSQL1.getMessage());
			}
			Loggers.afficherMessageError("Error SQL - "+ eSQL.getMessage());
		}
	}
	
	public ArrayList<Company> lister() throws DAOConfigurationException {

		ArrayList<Company> companies = new ArrayList<Company>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.LIST_COMPANY.getMessage() + ";");){
			
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");

				Company company = new Company.CompanyBuilder().setId(id).setName(name).build();

				companies.add(company);
				
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL CompanyDAO, la méthode lister n'a pas abouti");
		}
		
		
		return companies;
	}
	
	public ArrayList<Company> lister(int offset, int pas) throws DAOConfigurationException {

		ArrayList<Company> companies = new ArrayList<Company>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.LIST_COMPANY.getMessage() + Requete.LIMIT.getMessage());){

			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				
				Company company = new Company.CompanyBuilder().setId(id).setName(name).build();

				companies.add(company);

				
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL CompanyDAO, la méthode lister avec limit n'a pas abouti");
		}

		return companies;
	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {

		Company company = new Company.CompanyBuilder().build();

		try (Connection connexion = DAO.getInstance().getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(Requete.DETAILS_COMPANY.getMessage());){
			
			preparedStatement.setInt(1, id);
			
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				
				int company_id = resultat.getInt("id");
				String company_name = resultat.getString("name");
				
				company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();
				
				
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL CompanyDAO, la méthode getCompanyById n'a pas abouti");
		}

		return company;
	}
	

}
