package fr.excilys.computer_database.utilisateur;

public enum Requete {
	
	ADD_COMPUTER ("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(:name, :introduced, :discontinued, :company.id);"),
	
	EDIT_COMPUTER ("UPDATE computer SET name= :name, introduced= :introduced, discontinued= :discontinued, company_id= :company.id WHERE id = :computer.id;"),
	
	DELETE_COMPUTER ("DELETE FROM computer WHERE id = :id;"),
	
	LIST_COMPUTER ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON computer.company_id = company.id"),
	
	LIST_SEARCH ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE LOWER(computer.name) LIKE :search OR LOWER(company.name) LIKE :search"),
	
	LIMIT (" LIMIT :offset, :step;"),
	
	DESC (" DESC"),
	
	ORDER_BY_COMPUTER_NAME (" ORDER BY computer.name"),
	ORDER_BY_INTRODUCED (" ORDER BY introduced"),
	ORDER_BY_DISCONTINUED (" ORDER BY discontinued"),
	ORDER_BY_COMPANY_NAME (" ORDER BY company.name"),
	
	GET_COMPUTER_BY_ID ("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
			+ "FROM computer " + "LEFT JOIN company ON company_id = company.id " + "WHERE computer.id = :id;"),
	
	LIST_COMPANY ("SELECT * FROM company"),
	
	GET_COMPANY_BY_ID ("SELECT * FROM company WHERE id = :id;"),
	
	DELETE_COMPUTER_FOR_DELETE_COMPANY ("DELETE FROM computer WHERE company_id = :id;"),
	
	DELETE_COMPANY ("DELETE FROM company WHERE id = :id;");

	private String message;

	Requete(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
