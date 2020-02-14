package fr.excilys.computer_database.utilisateur;

public enum Action {

	LISTER_COMPUTER ("1"), 
	LISTER_COMPANY ("2"), 
	LISTER_DETAILS_ORDI ("3"), 
	AJOUT ("4"), 
	MODIF ("5"), 
	SUPPR ("6"), 
	QUITTER ("7");

	private String nombre = "";

	// Constructeur
	Action(String nombre) {
		this.nombre = nombre;
	}

	public static Action valueOf(int i) {
		
		switch(i) {
		case 1:
			return LISTER_COMPUTER;
		case 2:
			return LISTER_COMPANY;
		case 3:
			return LISTER_DETAILS_ORDI;
		case 4:
			return AJOUT;
		case 5:
			return MODIF;
		case 6:
			return SUPPR;
		case 7:
			return QUITTER;
		default:
			return null;
		}
		
	}

}
