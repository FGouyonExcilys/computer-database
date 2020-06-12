package fr.excilys.computer_database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.mapper.ComputerMapper;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;

@Repository
public class ComputerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public ComputerDAO(EntityManagerFactory entityManagerFactory) {
		entityManager = entityManagerFactory.createEntityManager();
	}

	public ComputerDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Computer> findComputerByName(String name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);

		Root<Computer> computer = criteriaQuery.from(Computer.class);
		Predicate computerNamePredicate = criteriaBuilder.like(computer.get("name"), "%name%");
		criteriaQuery.where(computerNamePredicate);

		TypedQuery<Computer> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	private DataSource dataSource;
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	ComputerMapper computerMapper;

	public ComputerDAO(DataSource dataSource) {
//		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.dataSource = dataSource;
		computerMapper = new ComputerMapper();
	}

	/**
	 * 
	 * @param computer
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int ajouter(Computer computer) throws DAOConfigurationException {
//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("name",computer.getName())
//				.addValue("introduced",computer.getIntroduced())
//				.addValue("discontinued",computer.getDiscontinued())
//				.addValue("company.id", 
//							(computer.getCompany() != null) ? computer.getCompany().getId() : null );
//		
//		return namedParameterJdbcTemplate.update(Requete.ADD_COMPUTER.getMessage(), namedParameters);
		return 0;
	}

	/**
	 * 
	 * @param computer
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int modifier(Computer computer) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("computer.id", computer.getId())
//				.addValue("name",computer.getName())
//				.addValue("introduced", computer.getIntroduced())
//				.addValue("discontinued", computer.getDiscontinued())
//				.addValue("company.id", computer.getCompany().getId());
//		
//		return namedParameterJdbcTemplate.update(Requete.EDIT_COMPUTER.getMessage(), namedParameters);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = cb.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);

		criteriaUpdate.set("name", computer.getName());
		criteriaUpdate.set("introduced", computer.getIntroduced());
		criteriaUpdate.set("discontinued", computer.getDiscontinued());
		criteriaUpdate.set("company_id", computer.getCompany().getId());

		criteriaUpdate.where(cb.equal(root.get("id"), computer.getId()));

		entityManager.createQuery(criteriaUpdate).executeUpdate();

		return 0;

	}

	/**
	 * @param id
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int supprimer(int id) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
//		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPUTER.getMessage(), namedParameters);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = cb.createCriteriaDelete(Computer.class);

		Root<Computer> root = criteriaDelete.from(Computer.class);
		Predicate computerIdPredicate = cb.equal(root.get("id"), id);
		criteriaDelete.where(computerIdPredicate);

		entityManager.createQuery(criteriaDelete).executeUpdate();

		return 0;

	}

	/**
	 * @param id
	 * @return int
	 * @throws DAOConfigurationException
	 */
	public int deleteComputerByCompany(int id) throws DAOConfigurationException {
//		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
//		return namedParameterJdbcTemplate.update(Requete.DELETE_COMPUTER_FOR_DELETE_COMPANY.getMessage(), namedParameters);
		return 0;

	}

	/**
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> lister() throws DAOConfigurationException {

//		return (List<Computer>) namedParameterJdbcTemplate.query(Requete.LIST_COMPUTER.getMessage()+";",new ComputerMapper());

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);

		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);

		TypedQuery<Computer> computerList = entityManager.createQuery(criteriaQuery);
		return (List<Computer>) computerList.getResultList();
	}

	/**
	 * @param paginer
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> lister(Paginer paginer) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("offset",paginer.getOffset())
//				.addValue("step",paginer.getStep());
//
//		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());
//
//		String requete = ComputerMapper.requestMapper(testOrderBy, "noSearch");
//
//		return (List<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters, new ComputerMapper());

		return null;
	}

	/**
	 * @param search
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> listSearch(String search) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("search", '%' + search + '%');
//
//		return (List<Computer>) namedParameterJdbcTemplate.query(Requete.LIST_SEARCH.getMessage(), namedParameters, new ComputerMapper());
		return null;
	}

	/**
	 * @param paginer
	 * @return ArrayList<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> listSearch(Paginer paginer) throws DAOConfigurationException {
//		
//		SqlParameterSource namedParameters  = new MapSqlParameterSource()
//				.addValue("offset",paginer.getOffset())
//				.addValue("step",paginer.getStep())
//				.addValue("search", '%' + paginer.getSearch().toUpperCase() + '%');
//
//		int testOrderBy = ComputerMapper.orderByMapper(paginer.getOrderBy(), paginer.getColumnName());
//
//		String requete = ComputerMapper.requestMapper(testOrderBy, "search");
//
//		return (List<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters, new ComputerMapper());
		return null;
	}

	/**
	 * @param id
	 * @return Computer
	 * @throws DAOConfigurationException
	 */
	public Computer getComputerById(int id) throws DAOConfigurationException {

//		SqlParameterSource namedParameters  = new MapSqlParameterSource().addValue("id",id);
//		return namedParameterJdbcTemplate.queryForObject(Requete.GET_COMPUTER_BY_ID.getMessage(), namedParameters, new ComputerMapper());
		return null;
	}

}
