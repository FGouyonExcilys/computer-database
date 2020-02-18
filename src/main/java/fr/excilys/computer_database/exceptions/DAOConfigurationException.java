package fr.excilys.computer_database.exceptions;

public class DAOConfigurationException extends Exception{
	
	public DAOConfigurationException(){
	    System.out.println("Le fichier dao.properties qui contient les paramètres de connexion est introuvable");
	  }  

}
