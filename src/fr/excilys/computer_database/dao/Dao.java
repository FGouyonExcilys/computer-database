package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

	private String url;
	private String username;
	private String password;

	public Dao(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static Dao getInstance() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		Dao instance = new Dao(
				"jdbc:mysql://localhost:3306/computer-database-db?useSSL=false", "admincdb", "qwerty1234");
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}


}