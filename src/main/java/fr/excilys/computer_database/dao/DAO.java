package fr.excilys.computer_database.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;

public class DAO {

	private String url;
	private String username;
	private String password;

	private static final String FICHIER_PROPERTIES = "dao.properties";
	private static final String URL = "url";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String DRIVER = "driver";

	private static final String FICHIER_PROPERTIES_H2 = "daoH2.properties";
	private static final String URL_H2 = "urlH2";
	private static final String USERNAME_H2 = "usernameH2";
	private static final String PASSWORD_H2 = "passwordH2";
	private static final String DRIVER_H2 = "driverH2";

	private static DAO instanceDAO;

	public DAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAO getInstance() throws DAOConfigurationException {
		if (instanceDAO == null) {
			Properties properties = new Properties();
			String driver;
			String url;
			String nomUtilisateur;
			String motDePasse;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);
			if (fichierProperties == null) {
				throw new DAOConfigurationException();
			}
			try {
				properties.load(fichierProperties);
				url = properties.getProperty(URL);
				driver = properties.getProperty(DRIVER);
				nomUtilisateur = properties.getProperty(USERNAME);
				motDePasse = properties.getProperty(PASSWORD);
				try {
					Class.forName(driver);
					instanceDAO = new DAO(url, nomUtilisateur, motDePasse);
				} catch (ClassNotFoundException classNotFoundException) {
					throw new DAOConfigurationException();
				}

			} catch (IOException ioException) {
				throw new DAOConfigurationException();
			} finally {
				return instanceDAO;
			}
		}
		return instanceDAO;

	}

	public static DAO getInstanceH2() throws DAOConfigurationException {
		if (instanceDAO == null) {
			Properties properties = new Properties();
			String driver;
			String url;
			String nomUtilisateur;
			String motDePasse;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES_H2);
			if (fichierProperties == null) {
				throw new DAOConfigurationException();
			}
			try {
				properties.load(fichierProperties);
				url = properties.getProperty(URL_H2);
				driver = properties.getProperty(DRIVER_H2);
				nomUtilisateur = properties.getProperty(USERNAME_H2);
				motDePasse = properties.getProperty(PASSWORD_H2);
				try {
					Class.forName(driver);
					instanceDAO = new DAO(url, nomUtilisateur, motDePasse);
				} catch (ClassNotFoundException classNotFoundException) {
					throw new DAOConfigurationException();
				}
			} catch (IOException ioException) {
				throw new DAOConfigurationException();
			} finally {
				return instanceDAO;
			}
		}
		return instanceDAO;

	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
}