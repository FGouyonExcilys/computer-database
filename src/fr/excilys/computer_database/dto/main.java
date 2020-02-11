package fr.excilys.computer_database.dto;

import java.sql.*;
import java.time.LocalDate;

import fr.excilys.computer_database.dao.*;

public class main {

	public static void main(String[] args){

		try {
			
			Dao dao = Dao.getInstance();
			Connection con = dao.getConnection();
			
			ComputerDaoImpl computerDao = new ComputerDaoImpl(dao);
			CompanyDaoImpl companyDao = new CompanyDaoImpl(dao);
			
			Computer monPC = new Computer(575, "mon PC", null, null, 40);
			
			
			/*
			for (Company c : companyDao.lister()) {
				System.out.println(c.toString() + "\n");
			}
			
			for (Computer c : computerDao.lister()) {
				System.out.println(c.toString() + "\n");
			}
			
			/* System.out.println(computerDao.afficherInfoComputer(10)); */
			
			/* computerDao.ajouter(monPC); */
			
			/* computerDao.modifier(monPC); */
			
			/* computerDao.supprimer(monPC); */
			
			
		} catch (Exception e) {
			// gestion des exceptions
		}
	}

}
