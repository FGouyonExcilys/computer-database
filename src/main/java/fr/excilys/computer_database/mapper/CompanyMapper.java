package fr.excilys.computer_database.mapper;

import java.sql.*;

import fr.excilys.computer_database.dto.CompanyDTO;
import fr.excilys.computer_database.model.Company;

public class CompanyMapper {

	public Company mapRow(ResultSet resultat, int i) throws SQLException

	{
		Company company = new Company.CompanyBuilder().setId(resultat.getInt("id")).setName(resultat.getString("name"))
				.build();
		return company;
	}

	public static Company convertCompanyDTOtoCompany(CompanyDTO companyDTO) {
		int id = companyDTO.getId();
		Company company = new Company.CompanyBuilder().setId(id).build();
		return company;
	}

	public static CompanyDTO convertCompanytoCompanyDTO(Company company) {
		int id = company.getId();
		String name = company.getName();
		CompanyDTO companyDTO = new CompanyDTO(id, name);
		return companyDTO;
	}

}
