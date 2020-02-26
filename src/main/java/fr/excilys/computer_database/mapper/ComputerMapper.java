package fr.excilys.computer_database.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.excilys.computer_database.dto.CompanyDTO;
import fr.excilys.computer_database.dto.ComputerDTO;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;


public class ComputerMapper {
	
	public Computer mapRow(ResultSet resultat, int i) throws SQLException {

		Computer computer = new Computer.ComputerBuilder(resultat.getString("computer_name")).build();
		computer.setId(resultat.getInt("computer_id"));
		Date intro = resultat.getDate("introduced");
		Date disco = resultat.getDate("discontinued");
		LocalDate introduced = null;
		if (intro != null) {
			introduced = intro.toLocalDate();
		}
		LocalDate discontinued = null;
		computer.setIntroduced(introduced);
		
		if (disco != null) {
			discontinued = disco.toLocalDate();
		}
		
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		Company company = new Company.CompanyBuilder().setId(resultat.getInt("company_id"))
													.setName(resultat.getString("company_name"))
													.build();
		computer.setCompany(company);
		return computer;
	}

	public static Computer convertComputerDTOtoComputer(ComputerDTO computerDTO) {
		int id = computerDTO.getId();
		String name = computerDTO.getName();
		CompanyDTO companyDTO = computerDTO.getCompanyDTO();
		LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
		LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
		Company company = CompanyMapper.convertCompanyDTOtoCompany(companyDTO);
		Computer computer = new Computer.ComputerBuilder(name).setIntroduced(introduced).setDiscontinued(discontinued)
				.setCompany(company).build();
		computer.setId(id);
		return computer;
	}

	public static ComputerDTO convertComputertoComputerDTO(Computer computer) {
		int id = computer.getId();
		String name = computer.getName();
		String introduced = null;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		String discontinued = null;
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		Company compa = computer.getCompany();

		CompanyDTO compaDTO = CompanyMapper.convertCompanytoCompanyDTO(compa);
		ComputerDTO compDTO = new ComputerDTO(name, introduced, discontinued, compaDTO);
		compDTO.setId(id);
		return compDTO;
	}

}
