package fr.excilys.computer_database.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;

public class CompanyDAO {

	private final static String LISTER = "SELECT * FROM company;";
	private final static String LISTER_LIMIT = "SELECT * FROM company LIMIT ?,?;";
	private final static String DETAILS_COMPANY = "SELECT * FROM company WHERE id = ?;";
	private final static String DELETE_COMPANY = "DELETE FROM company WHERE id = ?;";
	
	
	private static volatile CompanyDAO INSTANCE = null;

	/** Point d'accès pour l'instance unique du singleton */
	public final static CompanyDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CompanyDAO();
		}
		return INSTANCE;
	}

	public CompanyDAO() {
	}

	
	public int deleteCompany(int idCompany) throws DAOConfigurationException{
		Connection connection = null; 
		try{ 
		   connection = DAOHikari.getInstance().getConnection();;  
		   connection.setAutoCommit(false); 
		  
		   //traitement des différentes instructions composant la transaction 
		  
		   if(idCompany != 0){ 
		      connection.commit();// c'est ici que l'on valide la transaction 
		      connection.setAutoCommit(true); 
		   }else{ 
		      connection.rollback(); 
		   } 
		}catch(SQLException sqle){ 
		   try{connection.rollback();}catch(Exception e){} 
		}catch(Exception e){ 
		   try{connection.rollback();}catch(Exception e1){} 
		}finally{ 
		   try{connection.close();}catch(Exception e){} 
		}
		return idCompany;
	}
	
	public ArrayList<Company> lister() throws DAOConfigurationException {

		ArrayList<Company> companies = new ArrayList<Company>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER);){
			
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
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER_LIMIT);){

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
			PreparedStatement preparedStatement = connexion.prepareStatement(DETAILS_COMPANY);){
			
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
