package fr.excilys.computer_database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.dto.Company;
import fr.excilys.computer_database.dto.Computer;

public final class ComputerDaoImpl implements ComputerDao {

	private static volatile ComputerDaoImpl INSTANCE = null;

	/**
	 * Point d'accès pour l'instance unique du singleton
	 * 
	 */
	public final static ComputerDaoImpl getInstance(Dao dao) {
		if (INSTANCE == null) {
			INSTANCE = new ComputerDaoImpl(dao);
		}
		return INSTANCE;
	}

	private Dao dao;

	private ComputerDaoImpl(Dao dao) {
		this.dao = dao;
	}

	@Override
	public void ajouter(Computer computer) throws SQLException {

		try {
			Connection connexion = dao.getConnection();
			
			PreparedStatement preparedStatement = connexion.prepareStatement(
					"INSERT INTO computer(name, introduced, discontinued, company_id) " + "VALUES(?, ?, ?, ?);");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, computer.getIntroduced());
			preparedStatement.setDate(3, computer.getDiscontinued());
			preparedStatement.setInt(4, computer.getCompany().getId());

			preparedStatement.executeUpdate();
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param Computer computer
	 * @throws SQLException 
	 * 
	 */
	@Override
	public void modifier(Computer computer) throws SQLException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("UPDATE computer "
					+ "SET name= ?, introduced= ?, discontinued= ?, company_id= ? " + "WHERE id = ?;");

			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, computer.getIntroduced());
			preparedStatement.setDate(3, computer.getDiscontinued());
			preparedStatement.setObject(4, computer.getCompany().getId());
			preparedStatement.setInt(5, computer.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dao.closeConnection(connexion);

	}

	@Override
	public void supprimer(int id) throws SQLException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("DELETE FROM computer WHERE id = ?;");
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dao.closeConnection(connexion);
	}

	@Override
	public List<Computer> lister() throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {

			connexion = dao.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id;");

			while (resultat.next()) {
				int id = resultat.getInt("computer.id");
				String name = resultat.getString("computer.name");
				Date introduced = resultat.getDate("introduced");
				Date discontinued = resultat.getDate("discontinued");
				int company_id = resultat.getInt("company.id");
				String company_name = resultat.getString("company.name");

				Computer computer = new Computer();
				computer.setId(id);
				computer.setName(name);
				computer.setIntroduced(introduced);
				computer.setDiscontinued(discontinued);
				
				Company company = new Company(company_id, company_name);
				computer.setCompany(company);

				computers.add(computer);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dao.closeConnection(connexion);
		
		return computers;
	}

	@Override
	public List<Computer> lister(int offset, int pas) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("SELECT * FROM computer LEFT JOIN company ON company_id = company.id LIMIT ?,?;");
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pas);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				Date introduced = resultat.getDate("introduced");
				Date discontinued = resultat.getDate("discontinued");
				int company_id = resultat.getInt("company_id");
				String company_name = resultat.getString("company.name");

				Computer computer = new Computer();
				computer.setId(id);
				computer.setName(name);
				computer.setIntroduced(introduced);
				computer.setDiscontinued(discontinued);
				
				Company company = new Company(company_id, company_name);
				computer.setCompany(company);

				computers.add(computer);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dao.closeConnection(connexion);
		
		return computers;
	}

	@Override
	public String afficherInfoComputer(int id) {

		Computer computer = new Computer();
		PreparedStatement preparedStatement = null;

		try {

			Connection connexion = dao.getConnection();
			preparedStatement = connexion.prepareStatement("SELECT * FROM computer "
							+ "LEFT JOIN company ON company_id = company.id WHERE computer.id = " + id + ";");
			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				String name = resultat.getString("name");
				Date introduced = resultat.getDate("introduced");
				Date discontinued = resultat.getDate("discontinued");
				
				Company company = new Company(resultat.getInt("company_id"), resultat.getString("company.name"));

				computer.setId(id);
				computer.setName(name);
				computer.setIntroduced(introduced);
				computer.setDiscontinued(discontinued);
				computer.setCompany(company);
				
			}
			
			dao.closeConnection(connexion);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computer.toString();
	}

}
