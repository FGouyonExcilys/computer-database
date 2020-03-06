package fr.excilys.computer_database.utilisateur;

public enum Requete {
	
	AJOUTER ("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);"),
	
	MODIFIER ("UPDATE computer SET name= ?, introduced= ?, discontinued= ?, company_id= ? WHERE id = ?;"),
	
	SUPPRIMER ("DELETE FROM computer WHERE id = ?;"),
	
	LISTER ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id"),
	
	LISTER_SEARCH ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?"),
	
	LIMIT (" LIMIT ?, ?;"),
	
	DESC (" DESC"),
	
	ORDER_BY_COMPUTER_NAME (" ORDER BY computer.name"),
	ORDER_BY_INTRODUCED (" ORDER BY introduced"),
	ORDER_BY_DISCONTINUED (" ORDER BY discontinued"),
	ORDER_BY_COMPANY_NAME (" ORDER BY company.name"),
	
	DETAILS_ORDI ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON company_id = company.id " + "WHERE computer.id = ?;"),
	
	DELETE_COMPANY ("DELETE FROM computer WHERE company_id = ?;");

	private String message;

	Requete(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
