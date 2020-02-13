package fr.excilys.computer_database.utilisateur;

import java.sql.Date;
import java.util.Scanner;

import fr.excilys.computer_database.dao.CompanyDaoImpl;
import fr.excilys.computer_database.dao.ComputerDaoImpl;
import fr.excilys.computer_database.dao.Dao;
import fr.excilys.computer_database.dto.Company;
import fr.excilys.computer_database.dto.Computer;

public class CLI {

	public static void affichage() {

		try {

			Dao dao = Dao.getInstance();

			ComputerDaoImpl computerDao = ComputerDaoImpl.getInstance(dao);
			CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance(dao);

			int menu = 0;

			Scanner choix = new Scanner(System.in);

			while (menu != 1) {

				System.out.println("////// Menu //////\n");
				System.out.println("////// 1 - Afficher la liste des ordinateurs //////\n");
				System.out.println("////// 2 - Afficher la liste des entreprises //////\n");
				System.out.println("////// 3 - Afficher les détails d'un ordinateurs //////\n");
				System.out.println("////// 4 - Ajouter un ordinateur //////\n");
				System.out.println("////// 5 - Modifier un ordinateur //////\n");
				System.out.println("////// 6 - Supprimer un ordinateur //////\n");
				System.out.println("////// 7 - Quitter //////\n");

				System.out.println("Que voulez-vous faire : ");
				String str = choix.next();
				choix.nextLine();

				switch (str) {

				case "1":		// Pagination de la liste de tous les ordinateurs
					System.out.println("Entrez un pas de pagination : ");
					String strPasComputer = choix.nextLine();
					int pasComputer = Integer.parseInt(strPasComputer);

					for (int i = 0; i < computerDao.lister().size(); i += pasComputer) {
						for (Computer c : computerDao.lister(i, pasComputer)) {
							System.out.println(c.toString());
						}
						System.in.read();
					}
					break;
				case "2":		// Pagination de la liste de toutes les entreprises
					System.out.println("Entrez un pas de pagination : ");
					String strPasCompany = choix.nextLine();
					int pasCompany = Integer.parseInt(strPasCompany);

					for (int i = 0; i < companyDao.lister().size(); i += pasCompany) {
						for (Company c : companyDao.lister(i, pasCompany)) {
							System.out.println(c.toString());
						}
						System.in.read();
					}
					System.out.println("");
					break;
				case "3":		// Infos détaillées d'un ordinateur

					System.out.println("Entrez l'id d'un ordinateur : ");
					String strDetails = choix.nextLine();
					int details = Integer.parseInt(strDetails);

					if ( details <= computerDao.lister().size() && details > 0) {
						System.out.println("\n" + computerDao.afficherInfoComputer(details) + "\n");
					}
					else {
						System.out.println("\nAucun ordinateur n'a été trouvé \n");
					}
					break;

				case "4": 		// Ajouter un ordinateur

					System.out.println("Entrez le nom de l'ordinateur: ");
					String strName = choix.nextLine();

					System.out.println("Entrez la date d'introduction de l'ordinateur (aaaa-mm-jj): ");
					String strIntroduced = choix.nextLine();

					System.out.println("Entrez la date d'arrêt de production du produit (aaaa-mm-jj): ");
					String strDiscontinued = choix.nextLine();

					System.out.println("Entrez l'id de l'entreprise de l'ordinateur : ");
					String strCompanyId = choix.nextLine();

					computerDao.ajouter(new Computer(1, strName, Date.valueOf(strIntroduced),
							Date.valueOf(strDiscontinued), new Company(Integer.parseInt(strCompanyId), "")));

					break;

				case "5":

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

					computerDao.modifier(new Computer(Integer.parseInt(strIdModif), strNameModif,
							Date.valueOf(strIntroducedModif), Date.valueOf(strDiscontinuedModif),
							new Company(Integer.parseInt(strCompanyIdModif), "")));

					break;

				case "6":

					System.out.println("Entrez l'id d'un ordinateur à supprimer: ");
					String strIdDel = choix.nextLine();

					computerDao.supprimer(Integer.parseInt(strIdDel));

					break;
				case "7":
					menu = 1;
					break;
				default:
					System.out.println("\nVeuillez choisir un input valide\n");
					break;
				}

			}
			choix.close();
			Runtime.getRuntime().exec("clear");

		} catch (Exception e) {
			// gestion des exceptions
		}

	}
}
