package fr.excilys.computer_database.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;

public final class ComputerDAO {

	
	private final static String AJOUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);";
	private final static String MODIFIER = "UPDATE computer SET name= ?, introduced= ?, discontinued= ?, company_id= ? WHERE id = ?;";
	private final static String SUPPRIMER = "DELETE FROM computer WHERE id = ?;";

	private final static String LISTER = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			 						   + "FROM computer "
			 						   + "LEFT JOIN company ON computer.company_id = company.id;";
	
	private final static String LISTER_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
											 + "FROM computer "
											 + "LEFT JOIN company ON computer.company_id = company.id "
											 + "LIMIT ?,?;";
	
	private final static String TROUVERID = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE computer.id=:id";
	private final static String TROUVERNOM = "SELECT  computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE LOWER(computer.name) LIKE :recherche OR LOWER(company.name) LIKE :recherche OR introduced LIKE :recherche OR discontinued LIKE :recherche;";
	private final static String EFFACER = "DELETE FROM computer WHERE id = :id";
	private final static String EFFACERPARCOMPA = "DELETE FROM computer WHERE company_id = :id";
	private final static String SELECTION = "SELECT computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id";
	private final static String ASCENDANT = " ASC";
	private final static String DESCENDANT = " DESC";
	private final static String ORDER = " ORDER BY ";
	
	
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

	private DAO dao;

	private ComputerDAO(DAO dao) {
		this.dao = dao;
	}

	public void ajouter(Computer computer) throws SQLException {

		Connection connexion = dao.getConnection();
		
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(AJOUTER);
			
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().toString()));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().toString()));
			preparedStatement.setInt(4, computer.getCompany().getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			//TODO Loggers
		}
		dao.closeConnection(connexion);
	}

	/**
	 * @param Computer computer
	 * @throws SQLException 
	 * 
	 */
	
	public void modifier(Computer computer) throws SQLException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement(MODIFIER);

			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().toString()));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().toString()));
			preparedStatement.setObject(4, computer.getCompany().getId());
			preparedStatement.setInt(5, computer.getId());    

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			//TODO Loggers
		}
		
		dao.closeConnection(connexion);

	}

	public void supprimer(int id) throws SQLException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement(SUPPRIMER);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			//TODO Loggers
		}
		dao.closeConnection(connexion);
	}

	public ArrayList<Computer> lister() throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {

			connexion = dao.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery(LISTER);

			while (resultat.next()) {
				int id = resultat.getInt("computer.id");
				String name = resultat.getString("computer.name");
				LocalDate introduced = resultat.getDate("introduced").toLocalDate();
				LocalDate discontinued = resultat.getDate("discontinued").toLocalDate();
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
			//TODO Loggers
		}
		
		dao.closeConnection(connexion);
		
		return computers;
	}

	public ArrayList<Computer> lister(int offset, int pas) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
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
				LocalDate introduced = resultat.getDate("introduced").toLocalDate();
				LocalDate discontinued = resultat.getDate("discontinued").toLocalDate();
				int company_id = resultat.getInt("company_id");
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
			//TODO Loggers
		}
		
		dao.closeConnection(connexion);
		
		return computers;
	}

	public String afficherInfoComputer(int id) {

		Computer computer = null;
		PreparedStatement preparedStatement = null;

		try {

			Connection connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("SELECT * FROM computer "
							+ "LEFT JOIN company ON company_id = company.id WHERE computer.id = " + id + ";");
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String name = resultat.getString("name");
				LocalDate introduced = resultat.getDate("introduced").toLocalDate();
				LocalDate discontinued = resultat.getDate("discontinued").toLocalDate();
				int company_id = resultat.getInt("company_id");
				String company_name = resultat.getString("company.name");
				
				Company company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();
				
				computer = new Computer.ComputerBuilder(name)
						.setIntroduced(introduced)
						.setDiscontinued(discontinued)
						.setCompany(company).build();
				
				computer.setId(id);
				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computer.toString();
	}

}
