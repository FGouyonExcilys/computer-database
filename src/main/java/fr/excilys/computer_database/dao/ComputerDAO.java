package fr.excilys.computer_database.dao;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.mapper.ComputerMapper;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.utilisateur.Requete;

@Repository
public class ComputerDAO {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ComputerDAO(DataSource dataSource) {
		namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 
	 * @param computer
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int ajouter(Computer computer) throws DAOConfigurationException {
		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("name",computer.getName())
				.addValue("introduced",computer.getIntroduced())
				.addValue("discontinued",computer.getDiscontinued())
				.addValue("company.id", 
							(computer.getCompany() != null) ? computer.getCompany().getId() : null );
		
		return namedParameterJdbcTemplate.update(Requete.ADD_COMPUTER.getMessage(), namedParameters);
	}
	/**
	 * 
	 * @param computer
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int modifier(Computer computer) throws DAOConfigurationException {
		
		Object introduced = computer.getIntroduced() != null ? computer.getIntroduced() : null;
		Object discontinued = computer.getDiscontinued() != null ? computer.getDiscontinued() : null;
		Object companyId = (computer.getCompany() != null) ? computer.getCompany().getId() : null;
		
		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("computer.id", computer.getId())
				.addValue("name",computer.getName())
				.addValue("introduced", introduced)
				.addValue("discontinued", discontinued)
				.addValue("company.id", companyId );
		
		return namedParameterJdbcTemplate.update(Requete.EDIT_COMPUTER.getMessage(), namedParameters);

	}

	/**
	 * @param id
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int supprimer(int id) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPUTER.getMessage(), namedParameters);
	}
	
	/**
	 * @param id
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int deleteComputerByCompany(int id) throws DAOConfigurationException{
		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPUTER_FOR_DELETE_COMPANY.getMessage(), namedParameters);
	}
	
	/**
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public ArrayList<Computer> lister() throws DAOConfigurationException {
		
		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(Requete.LIST_COMPUTER.getMessage()+";",new ComputerMapper());

	}
	
	/**
	 * @param paginer
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public ArrayList<Computer> lister(Paginer paginer) throws DAOConfigurationException {
		
		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("offset",paginer.getOffset())
				.addValue("step",paginer.getStep());

		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());

		String requete = ComputerMapper.requestMapper(testOrderBy, "noSearch");

		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters, new ComputerMapper());

	}

	/**
	 * @param search
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public ArrayList<Computer> listSearch(String search) throws DAOConfigurationException {
		
		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("search", '%' + search + '%');

		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(Requete.LIST_SEARCH.getMessage(), namedParameters, new ComputerMapper());
	}
	
	/**
	 * @param paginer
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public ArrayList<Computer> listSearch(Paginer paginer) throws DAOConfigurationException {
		
		SqlParameterSource namedParameters  = new MapSqlParameterSource()
				.addValue("offset",paginer.getOffset())
				.addValue("step",paginer.getStep())
				.addValue("search", '%' + paginer.getSearch().toUpperCase() + '%');

		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());

		String requete = ComputerMapper.requestMapper(testOrderBy, "search");

		return (ArrayList<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters, new ComputerMapper());
	}
	
	/**
	 * @param id
	 * @return Computer
	 * @throws DAOConfigurationException
	 */
	public Computer getComputerById(int id) throws DAOConfigurationException {

		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
		return namedParameterJdbcTemplate.queryForObject(Requete.GET_COMPUTER_BY_ID.getMessage(), namedParameters, new ComputerMapper());
	}

}
