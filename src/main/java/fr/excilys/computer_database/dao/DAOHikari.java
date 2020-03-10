package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import fr.excilys.computer_database.logging.Loggers;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Component
public class DAOHikari {

	private static HikariConfig config ;
	private static HikariDataSource ds;
	
	private static Connection connect;
	private static DAOHikari instance;

	static {
		config = new HikariConfig("/hikari.properties");
		ds = new HikariDataSource(config);
	}
	
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
