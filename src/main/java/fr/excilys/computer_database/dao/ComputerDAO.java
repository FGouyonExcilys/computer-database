package fr.excilys.computer_database.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;

public class ComputerDAO {

	
	private final static String AJOUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) "
										+ "VALUES(?, ?, ?, ?);";
	
	private final static String MODIFIER = "UPDATE computer "
										 + "SET name= ?, introduced= ?, discontinued= ?, company_id= ? "
										 + "WHERE id = ?;";
	
	private final static String SUPPRIMER = "DELETE FROM computer WHERE id = ?;";

	private final static String LISTER = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			 						   + "FROM computer "
			 						   + "LEFT JOIN company ON computer.company_id = company.id;";
	
	private final static String LISTER_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
											 + "FROM computer "
											 + "LEFT JOIN company ON computer.company_id = company.id "
											 + "LIMIT ?, ?;";
	
	private final static String DETAILS_ORDI = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
											 + "FROM computer "
											 + "LEFT JOIN company ON company_id = company.id "
											 + "WHERE computer.id = ?;";
	/*
	private final static String TROUVERID = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE computer.id=:id";
	private final static String TROUVERNOM = "SELECT  computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE LOWER(computer.name) LIKE :recherche OR LOWER(company.name) LIKE :recherche OR introduced LIKE :recherche OR discontinued LIKE :recherche;";
	private final static String EFFACER = "DELETE FROM computer WHERE id = :id";
	private final static String EFFACERPARCOMPA = "DELETE FROM computer WHERE company_id = :id";
	private final static String SELECTION = "SELECT computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id";
	private final static String ASCENDANT = " ASC";
	private final static String DESCENDANT = " DESC";
	private final static String ORDER = " ORDER BY ";
	*/
	
	private static volatile ComputerDAO INSTANCE = null;

	/**
	 * Point d'accès pour l'instance unique du singleton
	 * 
	 */
	public final static ComputerDAO getInstance(DAO dao) {
		if (INSTANCE == null) {
			INSTANCE = new ComputerDAO(dao);
		}
		return INSTANCE;
	}

	@SuppressWarnings("unused")
	private DAO dao;

	private ComputerDAO(DAO dao) {
		this.dao = dao;
	}

	public int ajouter(Computer computer) throws SQLException, DAOConfigurationException {

		
		int executePS = 0;
		
		try (Connection connexion = DAO.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(AJOUTER);){
			
			
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, computer.getIntroduced()!=null?Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setInt(4, computer.getCompany().getId());

			executePS = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode ajouter n'a pas abouti");
		}
		
		return executePS;
	}

	/**
	 * @param Computer computer
	 * @throws SQLException 
	 * @throws DAOConfigurationException 
	 * 
	 */
	
	public int modifier(Computer computer) throws SQLException, DAOConfigurationException {

		int executePS = 0;
		Connection connexion = null;
		
		try {
			connexion = DAO.getInstance().getConnection();
			
			PreparedStatement preparedStatement = connexion.prepareStatement(MODIFIER);
			
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, computer.getIntroduced()!=null?Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setInt(4, computer.getCompany().getId());
			preparedStatement.setInt(5, computer.getId());
			
			executePS = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode modifier n'a pas abouti \n" + e.getMessage());
		}
		
		return executePS;

	}

	public int supprimer(int id) throws SQLException, DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAO.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(SUPPRIMER);){
			
			preparedStatement.setInt(1, id);

			executePS = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode supprimer n'a pas abouti\n" + e.getMessage());
		}
		
		return executePS;
	}

	public ArrayList<Computer> lister() throws SQLException, DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAO.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER);
				ResultSet resultat = preparedStatement.executeQuery();){

			while (resultat.next()) {
				int id = resultat.getInt("computer.id");
				String name = resultat.getString("computer.name");
				LocalDate introduced = resultat.getTimestamp("introduced")!=null?resultat.getTimestamp("introduced").toLocalDateTime().toLocalDate():null;
				LocalDate discontinued = resultat.getTimestamp("discontinued")!=null?resultat.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null;
				int company_id = resultat.getInt("company.id");
				String company_name = resultat.getString("company.name");
				
				Company company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();

				Computer computer = new Computer.ComputerBuilder(name)
												.setIntroduced(introduced)
												.setDiscontinued(discontinued)
												.setCompany(company).build();
				computer.setId(id);
				computers.add(computer);
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode lister n'a pas abouti\n" + e.getMessage());
		}
		
		return computers;
	}

	public ArrayList<Computer> lister(int offset, int pas) throws SQLException, DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAO.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER_LIMIT);){

			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				LocalDate introduced = resultat.getTimestamp("introduced")!=null?resultat.getTimestamp("introduced").toLocalDateTime().toLocalDate():null;
				LocalDate discontinued = resultat.getTimestamp("discontinued")!=null?resultat.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null;
				int company_id = resultat.getInt("company.id");
				String company_name = resultat.getString("company.name");
				
				Company company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();

				Computer computer = new Computer.ComputerBuilder(name)
						.setIntroduced(introduced)
						.setDiscontinued(discontinued)
						.setCompany(company).build();
				computer.setId(id);
				
				computers.add(computer);
				
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode lister avec limit n'a pas abouti\n" + e.getMessage());
		}
		
		return computers;
	}

	public Computer getComputerById(int id) throws DAOConfigurationException {

		Computer computer = new Computer.ComputerBuilder("nom_build").build();

		try (Connection connexion = DAO.getInstance().getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DETAILS_ORDI);){
			
			preparedStatement.setInt(1, id);
			
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String name = resultat.getString("name");
				LocalDate introduced = resultat.getTimestamp("introduced")!=null?resultat.getTimestamp("introduced").toLocalDateTime().toLocalDate():null;
				LocalDate discontinued = resultat.getTimestamp("discontinued")!=null?resultat.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null;
				int company_id = resultat.getInt("company.id");
				String company_name = resultat.getString("company.name");
				
				Company company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();
				
				computer = new Computer.ComputerBuilder(name)
						.setIntroduced(introduced)
						.setDiscontinued(discontinued)
						.setCompany(company).build();
				computer.setId(id);
				
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode afficherInfoComputer n'a pas abouti");
		}

		return computer;
	}

}
