package fr.excilys.computer_database.exceptions;

import java.time.LocalDate;

import fr.excilys.computer_database.model.Computer;

public class ComputerValidator {
	
	
	public static void validateDate(LocalDate introduced, LocalDate discontinued) throws DateException {
		
		if (introduced != null && discontinued != null) {
			if (introduced.isAfter(discontinued)) {
				throw new DateException("Dates incorrectes");
			}
			
		}
	}
	
	public static void validateName(String name) throws NameException {
		
		if (name == null || name.isEmpty()) {
			throw new NameException("Nom vide");
		}
	}
	
	public static void validateComputer(Computer computer) throws DateException, NameException {
		
		validateDate(computer.getIntroduced(), computer.getDiscontinued());
		validateName(computer.getName());
	}
	
}
