package fr.excilys.computer_database.dto;

import java.sql.*;

import fr.excilys.computer_database.dao.*;

public class main {

	public static void main(String[] args) {

		try {
			
			Dao dao = Dao.getInstance();
			Connection con = dao.getConnection();

			/*
			// Envoi d’un requête générique
			String sql = "select * from company";
			Statement smt = con.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
			
			*/
			
			ComputerDaoImpl computerDao = new ComputerDaoImpl(dao);
			CompanyDaoImpl companyDao = new CompanyDaoImpl(dao);
			
			for (Company c : companyDao.lister()) {
				System.out.println(c.toString() + "\n");
			}
			
			for (Computer c : computerDao.lister()) {
				System.out.println(c.toString() + "\n");
			}
			
					
		} catch (Exception e) {
			// gestion des exceptions
		}
	}

}
