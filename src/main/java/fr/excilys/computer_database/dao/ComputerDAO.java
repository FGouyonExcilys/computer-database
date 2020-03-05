package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.mapper.ComputerMapper;
import fr.excilys.computer_database.model.Computer;

public class ComputerDAO {

	private final static String AJOUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) "
			+ "VALUES(?, ?, ?, ?);";

	private final static String MODIFIER = "UPDATE computer "
			+ "SET name= ?, introduced= ?, discontinued= ?, company_id= ? " + "WHERE id = ?;";

	private final static String SUPPRIMER = "DELETE FROM computer WHERE id = ?;";

	private final static String LISTER = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id";

	private final static String LIMIT = " LIMIT ?, ?;";

	private final static String ORDER_BY_COMPUTER_NAME = " ORDER BY computer.name";

	private final static String DESC = " DESC";

	private final static String LISTER_SEARCH = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer "
			+ "LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?";

	private final static String DETAILS_ORDI = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON company_id = company.id " + "WHERE computer.id = ?;";
	
	private final static String DELETECOMPANY = "DELETE FROM computer WHERE company_id = ?;";
	
	
	private static volatile ComputerDAO INSTANCE = null;

	/**
	 * Point d'accès pour l'instance unique du singleton
	 * 
	 * 
	 */

	public final static ComputerDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}

	private ComputerDAO() {
	}

	public int ajouter(Computer computer) throws DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(AJOUTER);) {

			executePS = ComputerMapper.mapPreparedStatement(preparedStatement, computer);

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

	public int modifier(Computer computer) throws DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(MODIFIER);) {

			preparedStatement.setInt(5, computer.getId());
			executePS = ComputerMapper.mapPreparedStatement(preparedStatement, computer);

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode modifier n'a pas abouti \n" + e.getMessage());
		}

		return executePS;

	}

	public int supprimer(int id) throws DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(SUPPRIMER);) {

			preparedStatement.setInt(1, id);
			executePS = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode supprimer n'a pas abouti\n" + e.getMessage());
		}

		return executePS;
	}

	public ArrayList<Computer> lister() throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER + ";");
				ResultSet resultat = preparedStatement.executeQuery();) {

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));
			}

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode lister n'a pas abouti\n" + e.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> lister(String orderBy, int offset, int pas) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		
		int i = testOrderBy(orderBy);

		String requete = LISTER + LIMIT;

		if (i == 1) {
			requete = LISTER + ORDER_BY_COMPUTER_NAME + LIMIT;
		} else if (i == -1) {
			requete = LISTER + ORDER_BY_COMPUTER_NAME + DESC + LIMIT;
		}

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(requete);) {

			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode lister avec limit n'a pas abouti\n" + e.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> listSearch(String search) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER_SEARCH +";");) {

			preparedStatement.setString(1, '%' + search + '%');
			preparedStatement.setString(2, '%' + search + '%');

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode liste recherchée n'a pas abouti\n" + e.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> listSearch(String orderBy, String search, int offset, int pas)
			throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		int i = testOrderBy(orderBy);

		String requete = LISTER_SEARCH + LIMIT;

		if (i == 1) {
			requete = LISTER_SEARCH + ORDER_BY_COMPUTER_NAME + LIMIT;
		} else if (i == -1) {
			requete = LISTER_SEARCH + ORDER_BY_COMPUTER_NAME + DESC + LIMIT;
		}

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(requete);) {

			preparedStatement.setString(1, '%' + search + '%');
			preparedStatement.setString(2, '%' + search + '%');
			preparedStatement.setInt(3, offset);
			preparedStatement.setInt(4, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException e) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode liste recherchée avec limit n'a pas abouti\n"
							+ e.getMessage());
		}

		return computers;
	}

	public Computer getComputerById(int id) throws DAOConfigurationException {

		Computer computer = new Computer.ComputerBuilder("nom_build").build();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(DETAILS_ORDI);) {

			preparedStatement.setInt(1, id);
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computer = ComputerMapper.mapComputer(resultat);

			}

		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode afficherInfoComputer n'a pas abouti");
		}

		return computer;
	}

	private int testOrderBy(String orderBy) {
		
		if(orderBy == null) {
			return 0;
		}
		if (orderBy.equals("asc")) {
			return 1;
		} else if (orderBy.equals("desc")) {
			return -1;
		} else {
			return 0;
		}
	}
	
	private int columnIndexOrderBy(String columnName) {
		
		switch(columnName) {
			case "cpName":
				return 1;
			case "introduced":
				return 2;
			case "discontinued":
				return 3;
			case "companyName":
				return 4;
			default:
				return 0;
		}
		
	}

}
