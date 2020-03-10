package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.mapper.ComputerMapper;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.utilisateur.Requete;

@Repository
public class ComputerDAO {

	/**
	 * Point d'accès pour l'instance unique du singleton
	 * 
	 * 
	 */
	
//	private static volatile ComputerDAO INSTANCE = null;
//	
//	public final static ComputerDAO getInstance() {
//		if (INSTANCE == null) {
//			INSTANCE = new ComputerDAO();
//		}
//		return INSTANCE;
//	}
	
	public ComputerDAO() {
		super();
	}

	public int ajouter(Computer computer) throws DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.ADD_COMPUTER.getMessage());) {

			executePS = ComputerMapper.mapPreparedStatement(preparedStatement, computer);

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode ajouter n'a pas abouti \n" + eSQL.getMessage());
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
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.EDIT_COMPUTER.getMessage());) {

			preparedStatement.setInt(5, computer.getId());
			executePS = ComputerMapper.mapPreparedStatement(preparedStatement, computer);

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode modifier n'a pas abouti \n" + eSQL.getMessage());
		}

		return executePS;

	}

	public int supprimer(int id) throws DAOConfigurationException {

		int executePS = 0;

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.DELETE_COMPUTER.getMessage());) {

			preparedStatement.setInt(1, id);
			executePS = preparedStatement.executeUpdate();

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode supprimer n'a pas abouti\n" + eSQL.getMessage());
		}

		return executePS;
	}

	public ArrayList<Computer> lister() throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.LIST_COMPUTER.getMessage() + ";");
				ResultSet resultat = preparedStatement.executeQuery();) {

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));
			}

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode lister n'a pas abouti\n" + eSQL.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> lister(Paginer paginer) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());

		String requete = ComputerMapper.requestMapper(testOrderBy, "noSearch");

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(requete);) {

			preparedStatement.setInt(1, paginer.getOffset());
			preparedStatement.setInt(2, paginer.getStep());

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode lister avec limit n'a pas abouti\n" + eSQL.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> listSearch(String search) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.LIST_SEARCH.getMessage() + ";");) {

			preparedStatement.setString(1, '%' + search + '%');
			preparedStatement.setString(2, '%' + search + '%');

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode liste recherchée n'a pas abouti\n" + eSQL.getMessage());
		}

		return computers;
	}

	public ArrayList<Computer> listSearch(Paginer paginer) throws DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());

		String requete = ComputerMapper.requestMapper(testOrderBy, "search");

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(requete);) {

			preparedStatement.setString(1, '%' + paginer.getSearch().toUpperCase() + '%');
			preparedStatement.setString(2, '%' + paginer.getSearch().toUpperCase() + '%');
			preparedStatement.setInt(3, paginer.getOffset());
			preparedStatement.setInt(4, paginer.getStep());

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computers.add(ComputerMapper.mapComputer(resultat));

			}

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError(
					"Exception SQL ComputerDAO, la méthode liste recherchée avec limit n'a pas abouti\n"
							+ eSQL.getMessage());
		}

		return computers;
	}

	public Computer getComputerById(int id) throws DAOConfigurationException {

		Computer computer = new Computer.ComputerBuilder("nom_build").build();

		try (Connection connexion = DAOHikari.getInstance().getConnection();
				PreparedStatement preparedStatement = connexion.prepareStatement(Requete.DETAILS_ORDI.getMessage());) {

			preparedStatement.setInt(1, id);
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				computer = ComputerMapper.mapComputer(resultat);

			}

		} catch (SQLException eSQL) {
			Loggers.afficherMessageError("Exception SQL ComputerDAO, la méthode afficherInfoComputer n'a pas abouti\n" + eSQL.getMessage());
		}

		return computer;
	}

}
