package fr.excilys.computer_database.dto;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import fr.excilys.computer_database.dao.*;

public class main {

	public static void main(String[] args){

		
		try {
			
			Dao dao = Dao.getInstance();
			Connection connection = dao.getConnection();
			
			ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance(dao);
			CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance(dao);
			
			System.out.println("////// CLI Computer-Database //////\n");
			
			int menu = 0;
			
			while(menu == 0) {
				
				
				System.out.println("////// Menu //////\n");
				System.out.println("////// 1 - Afficher la liste des ordinateurs //////\n");
				System.out.println("////// 2 - Afficher la liste des entreprises //////\n");
				System.out.println("////// 3 - Afficher les détails d'un ordinateurs //////\n");
				System.out.println("////// 4 - Ajouter un ordinateur //////\n");
				System.out.println("////// 5 - Modifier un ordinateur //////\n");
				System.out.println("////// 6 - Supprimer un ordinateur //////\n");
				System.out.println("////// 7 - Quitter //////\n");
				
				Scanner choix = new Scanner(System.in);
				System.out.println("Que voulez-vous faire : ");
				String str = choix.nextLine();
				
				switch(Integer.parseInt(str)){
					case 1:
						for (Computer c : computerDao.lister()) {
							System.out.println(c.toString());
						}
						System.out.println("");
						break;
					case 2:
						for (Company c : companyDao.lister()) {
							System.out.println(c.toString());
						}
						System.out.println("");
						break;
					case 3:
						
						System.out.println("Entrez l'id d'un ordinateur : ");
						String strDetails = choix.nextLine();
						
						System.out.println(computerDao.afficherInfoComputer(Integer.parseInt(strDetails)) + "\n");
						break;
						
					case 4:	//Ajouter un ordinateur
						
						System.out.println("Entrez le nom de l'ordinateur: ");
						String strName = choix.nextLine();
						
						System.out.println("Entrez la date d'introduction de l'ordinateur (aaaa-mm-jj): ");
						String strIntroduced = choix.nextLine();
						
						System.out.println("Entrez la date d'arrêt de production du produit (aaaa-mm-jj): ");
						String strDiscontinued = choix.nextLine();
						
						System.out.println("Entrez l'id de l'entreprise de l'ordinateur : ");
						String strCompanyId = choix.nextLine();
						
						computerDao.ajouter(new Computer(1, strName, Date.valueOf(strIntroduced), Date.valueOf(strDiscontinued), Integer.parseInt(strCompanyId)));
						
						break;
					case 5:
						
						System.out.println("Entrez l'id d'un ordinateur à modifier: ");
						String strIdModif = choix.nextLine();
						
						System.out.println(computerDao.afficherInfoComputer(Integer.parseInt(strIdModif)));
						System.out.println("Entrez le nouveau nom de l'ordinateur: ");
						String strNameModif = choix.nextLine();
						
						System.out.println("Entrez la date d'introduction de l'ordinateur (aaaa-mm-jj): ");
						String strIntroducedModif = choix.nextLine();
						
						System.out.println("Entrez la date d'arrêt de production du produit (aaaa-mm-jj): ");
						String strDiscontinuedModif = choix.nextLine();
						
						System.out.println("Entrez l'id de l'entreprise de l'ordinateur: ");
						String strCompanyIdModif = choix.nextLine();
						
						computerDao.modifier(new Computer(Integer.parseInt(strIdModif), strNameModif, Date.valueOf(strIntroducedModif), Date.valueOf(strDiscontinuedModif), Integer.parseInt(strCompanyIdModif)));
						
						break;
					case 6:
						
						System.out.println("Entrez l'id d'un ordinateur à supprimer: ");
						String strIdDel = choix.nextLine();
						
						computerDao.supprimer(Integer.parseInt(strIdDel));
						
						break;
					case 7:
						menu = 1;
						break;
					default:
						System.out.println("Veuillez choisir un input valide");
						break;
				}
				
			}
			
			
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
			
			connection.close();
			
		} catch (Exception e) {
			// gestion des exceptions
		}
	}

}
