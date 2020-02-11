package fr.excilys.computer_database.dto;

import java.sql.*;

public class main {

	public static void main(String[] args) {

		try {
			
			// chargement de la classe par son nom
			Class c = Class.forName("com.mysql.jdbc.Driver");
			Driver pilote = (Driver) c.newInstance();
			
			// enregistrement du pilote auprès du DriverManager
			DriverManager.registerDriver(pilote);
			
			// Protocole de connexion
			String protocole = "jdbc:mysql:";
			
			// Adresse IP de l’hôte de la base et port
			String ip = "localhost"; // dépend du contexte
			String port = "3306"; // port MySQL par défaut
			
			// Nom de la base ;
			String nomBase = "computer-database-db"; // dépend du contexte
			
			// Chaîne de connexion
			String conString = protocole + "//" + ip + ":" + port + "/" + nomBase;
			
			// Identifiants de connexion et mot de passe
			String nomConnexion = "admincdb"; // dépend du contexte
			String motDePasse = "qwerty1234"; // dépend du contexte
			// Connexion
			
			Connection con = DriverManager.getConnection(conString, nomConnexion, motDePasse);

			// Envoi d’un requête générique
			String sql = "select * from company";
			Statement smt = con.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (Exception e) {
			// gestion des exceptions
		}
	}

}
