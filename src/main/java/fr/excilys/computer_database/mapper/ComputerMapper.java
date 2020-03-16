package fr.excilys.computer_database.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.computer_database.dto.CompanyDTO;
import fr.excilys.computer_database.dto.ComputerDTO;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.utilisateur.Requete;

@Component
public class ComputerMapper implements RowMapper<Computer> {
	
	public Computer mapRow(ResultSet resultat, int i) throws SQLException {

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
			
			if(computer.getCompany() != null) {
				preparedStatement.setInt(4, computer.getCompany().getId());
			}
			else {
				preparedStatement.setNull(4, java.sql.Types.BIGINT);
			}
			
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
	

	public static int orderByMapper(String orderBy, String columnName) {

		if (orderBy == null || columnName == null) {
			return 0;
		}

		if (orderBy.equals("asc")) {
			
			switch (columnName) {

			case "computerName":
				return 1;
			case "introduced":
				return 2;
			case "discontinued":
				return 3;
			case "companyName":
				return 4;
			default:
				return 0;
			}

		} else if (orderBy.equals("desc")) {
			
			switch (columnName) {

			case "computerName":
				return -1;
			case "introduced":
				return -2;
			case "discontinued":
				return -3;
			case "companyName":
				return -4;
			default:
				return 0;
			}

		} else {
			return 0;
		}
	}
	
	public static String requestMapper(int type, String search) {
		
		if(search == "search") {
			switch(type) {
			case 1:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_COMPUTER_NAME.getMessage() + Requete.LIMIT.getMessage();
			case 2:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_INTRODUCED.getMessage() + Requete.LIMIT.getMessage();
			case 3:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_DISCONTINUED.getMessage() + Requete.LIMIT.getMessage();
			case 4:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_COMPANY_NAME.getMessage() + Requete.LIMIT.getMessage();
			case -1:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_COMPUTER_NAME.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -2:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_INTRODUCED.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -3:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_DISCONTINUED.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -4:
				return Requete.LIST_SEARCH.getMessage() + Requete.ORDER_BY_COMPANY_NAME.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			default:
				return Requete.LIST_SEARCH.getMessage() + Requete.LIMIT.getMessage();
			}
		}else {
			switch(type) {
			case 1:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_COMPUTER_NAME.getMessage() + Requete.LIMIT.getMessage();
			case 2:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_INTRODUCED.getMessage() + Requete.LIMIT.getMessage();
			case 3:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_DISCONTINUED.getMessage() + Requete.LIMIT.getMessage();
			case 4:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_COMPANY_NAME.getMessage() + Requete.LIMIT.getMessage();
			case -1:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_COMPUTER_NAME.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -2:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_INTRODUCED.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -3:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_DISCONTINUED.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			case -4:
				return Requete.LIST_COMPUTER.getMessage() + Requete.ORDER_BY_COMPANY_NAME.getMessage() + Requete.DESC.getMessage() + Requete.LIMIT.getMessage();
			default:
				return Requete.LIST_COMPUTER.getMessage() + Requete.LIMIT.getMessage();
			}
		}
		
	}
	

}
