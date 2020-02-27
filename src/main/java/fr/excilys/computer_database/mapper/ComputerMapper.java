package fr.excilys.computer_database.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import fr.excilys.computer_database.dto.CompanyDTO;
import fr.excilys.computer_database.dto.ComputerDTO;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;


public class ComputerMapper {
	
	public static Computer mapComputer(ResultSet resultat) throws SQLException {

		int id = resultat.getInt("computer.id");
		String name = resultat.getString("computer.name");
		LocalDate introduced = resultat.getTimestamp("introduced")!=null?resultat.getTimestamp("introduced").toLocalDateTime().toLocalDate():null;
		LocalDate discontinued = resultat.getTimestamp("discontinued")!=null?resultat.getTimestamp("discontinued").toLocalDateTime().toLocalDate():null;
		int company_id = resultat.getInt("company.id");
		String company_name = resultat.getString("company.name");
		
		Company company = new Company.CompanyBuilder().setId(company_id).setName(company_name).build();

		Computer computer = new Computer.ComputerBuilder(name)
										.setIntroduced(introduced)
										.setDiscontinued(discontinued)
										.setCompany(company).build();
		computer.setId(id);
		
		return computer;
	}
	
	public static int mapPreparedStatement(PreparedStatement preparedStatement, Computer computer) {
		
		try {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, computer.getIntroduced()!=null?Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setTimestamp(3, computer.getDiscontinued()!=null?Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)):null);
			preparedStatement.setInt(4, computer.getCompany().getId());
			
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
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
