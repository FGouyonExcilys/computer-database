package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.computer_database.logging.Loggers;

public class DAOHikari {

	private static HikariConfig config = new HikariConfig("hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
	
	private static Connection connect;
	private static DAOHikari instance;

	private DAOHikari() {
	}

	public Connection getConnection() {
		try {
			connect = ds.getConnection();
		} catch (SQLException sqlException) {
			for (Throwable e : sqlException) {
				Loggers.afficherMessageError("Problèmes rencontrés: " + e);
			}

		}
		return connect;
	}

	public static DAOHikari getInstance() {
		if (instance == null) {
			instance = new DAOHikari();
		}
		return instance;
	}

	public static Connection disconnect() {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException sqlException) {
				for (Throwable e : sqlException) {
					Loggers.afficherMessageError("Problèmes rencontrés: " + e);
				}
			}
		}
		return connect;
	}
}
