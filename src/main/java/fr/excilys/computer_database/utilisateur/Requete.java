package fr.excilys.computer_database.utilisateur;

public enum Requete {
	
	ADD_COMPUTER ("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);"),
	
	EDIT_COMPUTER ("UPDATE computer SET name= ?, introduced= ?, discontinued= ?, company_id= ? WHERE id = ?;"),
	
	DELETE_COMPUTER ("DELETE FROM computer WHERE id = ?;"),
	
	LIST_COMPUTER ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id"),
	
	LIST_SEARCH ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?"),
	
	LIMIT (" LIMIT ?, ?;"),
	
	DESC (" DESC"),
	
	ORDER_BY_COMPUTER_NAME (" ORDER BY computer.name"),
	ORDER_BY_INTRODUCED (" ORDER BY introduced"),
	ORDER_BY_DISCONTINUED (" ORDER BY discontinued"),
	ORDER_BY_COMPANY_NAME (" ORDER BY company.name"),
	
	DETAILS_ORDI ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON company_id = company.id " + "WHERE computer.id = ?;"),
	
	LIST_COMPANY ("SELECT * FROM company"),
	
	DETAILS_COMPANY ("SELECT * FROM company WHERE id = ?;"),
	
	COMPUTER_LIST_BY_COMPANY_ID("SELECT * FROM computer WHERE company_id = ?;"),
	
	DELETE_COMPUTER_FOR_DELETE_COMPANY ("DELETE FROM computer WHERE company_id = ?;"),
	
	DELETE_COMPANY ("DELETE FROM company WHERE id = ?;");

	private String message;

	Requete(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
