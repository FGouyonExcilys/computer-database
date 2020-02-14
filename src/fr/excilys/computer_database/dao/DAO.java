package fr.excilys.computer_database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

	private String url;
	private String username;
	private String password;

	public DAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAO getInstance() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		DAO instance = new DAO("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false", "admincdb",
				"qwerty1234");
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	public void closeConnection(Connection con) throws SQLException {
		con.close();
	}
}