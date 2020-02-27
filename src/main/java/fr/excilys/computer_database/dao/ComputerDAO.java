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
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id;";

	private final static String LISTER_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id " + "LIMIT ?, ?;";

	private final static String DETAILS_ORDI = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON company_id = company.id " + "WHERE computer.id = ?;";
	/*
	 * private final static String TROUVERID =
	 * "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE computer.id=:id"
	 * ; private final static String TROUVERNOM =
	 * "SELECT  computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE LOWER(computer.name) LIKE :recherche OR LOWER(company.name) LIKE :recherche OR introduced LIKE :recherche OR discontinued LIKE :recherche;"
	 * ; private final static String EFFACER =
	 * "DELETE FROM computer WHERE id = :id"; private final static String
	 * EFFACERPARCOMPA = "DELETE FROM computer WHERE company_id = :id"; private
	 * final static String SELECTION =
	 * "SELECT computer.name as computer_name, computer.id as computer_id, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id"
	 * ; private final static String ASCENDANT = " ASC"; private final static String
	 * DESCENDANT = " DESC"; private final static String ORDER = " ORDER BY ";
	 */

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
				PreparedStatement preparedStatement = connexion.prepareStatement(MODIFIER);){
			
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
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER);
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

	public ArrayList<Computer> lister(int offset, int pas) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(LISTER_LIMIT);) {

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

}
