package fr.excilys.computer_database.utilisateur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import fr.excilys.computer_database.utilisateur.Action;
import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.dao.DAO;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;

public class CLI {

	public static void affichage() {

		try {

			DAO dao = DAO.getInstance();

			ComputerDAO computerDao = ComputerDAO.getInstance(dao);
			CompanyDAO companyDao = CompanyDAO.getInstance(dao);

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
				
				Action action = Action.valueOf(Integer.parseInt(str));
				
				switch (action) {
                     
				case LISTER_COMPUTER:
					listerComputer(choix, computerDao);
					break;
					
				case LISTER_COMPANY:		
					listerCompany(choix, companyDao);
					break;
					
				case LISTER_DETAILS_ORDI:		
					listerDetailsOrdi(choix, computerDao);
					break;

				case AJOUT: 
					System.out.println("Entrez le nom de l'ordinateur: ");
					String strName = choix.nextLine();

					System.out.println("Entrez la date d'introduction de l'ordinateur (aaaa-mm-jj): ");
					String strIntroduced = choix.nextLine();

					System.out.println("Entrez la date d'arrêt de production du produit (aaaa-mm-jj): ");
					String strDiscontinued = choix.nextLine();

					System.out.println("Entrez l'id de l'entreprise de l'ordinateur : ");
					String strCompanyId = choix.nextLine();

					computerDao.ajouter(new Computer.ComputerBuilder(strName)
										.setIntroduced(strIntroduced.equals("null")?null:LocalDate.parse(strIntroduced))
										.setDiscontinued(strDiscontinued.equals("null")?null:LocalDate.parse(strDiscontinued))
										.setCompany(new Company.CompanyBuilder()
														.setId(Integer.parseInt(strCompanyId))
														.build())
										.build());
					break;

				case MODIF:

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
					
					Computer computer = new Computer.ComputerBuilder(strNameModif)
							.setIntroduced(strIntroducedModif.equals("null")?null:LocalDate.parse(strIntroducedModif))
							.setDiscontinued(strDiscontinuedModif.equals("null")?null:LocalDate.parse(strDiscontinuedModif))
							.setCompany(new Company.CompanyBuilder()
											.setId(Integer.parseInt(strCompanyIdModif))
											.build())
							.build();
					computer.setId(Integer.parseInt(strIdModif));

					computerDao.modifier(computer);

					break;

				case SUPPR:

					System.out.println("Entrez l'id d'un ordinateur à supprimer: ");
					String strIdDel = choix.nextLine();

					computerDao.supprimer(Integer.parseInt(strIdDel));

					break;
					
				case QUITTER:
					menu = 1;
					break;
					
				default:
					System.out.println("\nVeuillez choisir un input valide (PRESSEZ ENTRER POUR CONTINUER)\n");
					System.in.read();
					break;
				}

			}
			choix.close();
			Runtime.getRuntime().exec("clear");

		} catch (Exception e) {
			Loggers.afficherMessageError("CLI crash");
		}
		
		

	}
	
	public static void listerComputer(Scanner choix, ComputerDAO computerDao) {
		System.out.println("Entrez un pas de pagination : ");
		String strPasComputer = choix.nextLine();
		int pasComputer = Integer.parseInt(strPasComputer);

		try {
			for (int i = 0; i < computerDao.lister().size(); i += pasComputer) {
				computerDao.lister(i, pasComputer).stream().forEach(listePCDetails->System.out.println(listePCDetails));
				System.in.read();
			}
		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL CLI, la méthode listerComputer n'a pas abouti");
		} catch (IOException e){
			Loggers.afficherMessageError("Exception IO CLI, la méthode listerComputer n'a pas abouti");
		}
	}
	
	public static void listerCompany(Scanner choix, CompanyDAO companyDao) throws IOException {
		System.out.println("Entrez un pas de pagination : ");
		String strPasCompany = choix.next();
		int pasCompany = Integer.parseInt(strPasCompany);
		
		for (int i = 0; i < companyDao.lister().size(); i += pasCompany) {
			companyDao.lister(i, pasCompany).stream().forEach(listeCompanyDetails->System.out.println(listeCompanyDetails));
			System.in.read();
		}
	}
	public static void listerDetailsOrdi(Scanner choix, ComputerDAO computerDao) {
		System.out.println("Entrez l'id d'un ordinateur : ");
		String strDetails = choix.nextLine();
		int details = Integer.parseInt(strDetails);

		try {
			if ( details <= computerDao.lister().size() && details > 0) {
				System.out.println("\n" + computerDao.afficherInfoComputer(details) + "\n");
			}
			else {
				Loggers.afficherMessageInfo("Aucun ordinateur n'a été trouvé\n");
			}
		} catch (SQLException e) {
			Loggers.afficherMessageError("Exception SQL CLI, la méthode listerDetailsOrdi n'a pas abouti");
		}
	}
	
}
