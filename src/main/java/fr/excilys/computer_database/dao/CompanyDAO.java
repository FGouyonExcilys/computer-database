package fr.excilys.computer_database.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.mapper.CompanyMapper;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.utilisateur.Requete;

@Repository
public class CompanyDAO {
	
	ComputerDAO computerDao;
	CompanyMapper companyMapper;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private DataSource dataSource;
	
	private CompanyDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
		super();
		computerDao = new ComputerDAO(namedParameterJdbcTemplate, dataSource);
		this.dataSource= dataSource;
		this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
		companyMapper=new CompanyMapper();
	}

	
	@Transactional
	public int deleteCompany(int companyId) throws DAOConfigurationException {
		
		computerDao.deleteComputerByCompany(companyId);
		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",companyId);
		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPANY.getMessage(),namedParameters);
		
	}
	
	public List<Company> lister() throws DAOConfigurationException {

		return namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+";",new CompanyMapper());

	}
	
	public List<Company> lister(int offset, int step) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("offset",offset)
				.addValue("step",step);

		return (List<Company>)
				namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+Requete.LIMIT.getMessage(),
						namedParameters, new CompanyMapper());

	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
		return namedParameterJdbcTemplate.queryForObject(Requete.GET_COMPANY_BY_ID.getMessage(), namedParameters, new CompanyMapper());
	
	}
	

}
