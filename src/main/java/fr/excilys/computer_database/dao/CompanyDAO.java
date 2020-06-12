package fr.excilys.computer_database.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.mapper.CompanyMapper;
import fr.excilys.computer_database.model.Company;

@Repository
public class CompanyDAO {
	
	ComputerDAO computerDao;
	CompanyMapper companyMapper;
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private DataSource dataSource;
	
	private CompanyDAO(DataSource dataSource) {
		super();
		computerDao = new ComputerDAO(dataSource);
		this.dataSource= dataSource;
//		this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
		companyMapper=new CompanyMapper();
	}

	
	public CompanyDAO() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Transactional
	public int deleteCompany(int companyId) throws DAOConfigurationException {
		
//		computerDao.deleteComputerByCompany(companyId);
//		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",companyId);
//		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPANY.getMessage(),namedParameters);
		return 0;
		
	}
	
	public List<Company> lister() throws DAOConfigurationException {

//		return namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+";",new CompanyMapper());
		return null;
	}
	
	public List<Company> lister(int offset, int step) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("offset",offset)
//				.addValue("step",step);
//
//		return (List<Company>)
//				namedParameterJdbcTemplate.query(Requete.LIST_COMPANY.getMessage()+Requete.LIMIT.getMessage(),
//						namedParameters, new CompanyMapper());
		return null;

	}
	
	public Company getCompanyById(int id) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
//		return namedParameterJdbcTemplate.queryForObject(Requete.GET_COMPANY_BY_ID.getMessage(), namedParameters, new CompanyMapper());
//	
		return null;
	}
	

}
