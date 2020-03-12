package fr.excilys.computer_database.dao;

import java.util.ArrayList;

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
	
	private ComputerDAO computerDao;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CompanyDAO(DataSource dataSource,ComputerDAO computerDao) {
		namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		this.computerDao=computerDao;
	}

	
	@Transactional
	public int deleteCompany(int companyId) throws DAOConfigurationException {
		
		computerDao.deleteComputerByCompany(companyId);
		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",companyId);
		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPANY.getMessage(),namedParameters);
		
	}
	
	public ArrayList<Company> lister() throws DAOConfigurationException {

		return (ArrayList<Company>) namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+";",new CompanyMapper());

	}
	
	public ArrayList<Company> lister(int offset, int step) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("offset",offset)
				.addValue("step",step);

		return (ArrayList<Company>)
				namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+Requete.LIMIT.getMessage(),
						namedParameters, new CompanyMapper());

	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
		return namedParameterJdbcTemplate.queryForObject(Requete.GET_COMPANY_BY_ID.getMessage(), namedParameters, new CompanyMapper());
	
	}
	

}
