package fr.excilys.computer_database.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;

@Repository
public class ComputerDAO {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private CriteriaBuilder criteriaBuilder;

	/**
	 * 
	 * @param computer
	 * @return Optional<Computer>
	 * @throws DAOConfigurationException
	 */
	@SuppressWarnings("finally")
	@Transactional
	public Optional<Computer> ajouter(Computer computer) throws DAOConfigurationException {

		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		entityManager.joinTransaction();
		try {
			entityManager.persist(computer);
			System.out.println(computer);
		} catch (Exception persistanceException) {
			persistanceException.printStackTrace();
			Loggers.afficherMessageError("Error when Creating Entity");
		} finally {
			return Optional.ofNullable(computer);
		}
		
	}

	/**
	 * 
	 * @param computer
	 * @return int
	 * @throws DAOConfigurationException
	 */
	@Transactional
	public void modifier(Computer computer) throws DAOConfigurationException {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.joinTransaction();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);

		criteriaUpdate.set("name", computer.getName()).set("introduced", computer.getIntroduced())
				.set("discontinued", computer.getDiscontinued()).set("company", computer.getCompany().getId())
				.where(criteriaBuilder.equal(root.get("id"), computer.getId()));

		entityManager.createQuery(criteriaUpdate).executeUpdate();

	}

	/**
	 * @param id
	 * @return void
	 * @throws DAOConfigurationException
	 */
	@Transactional
	public void supprimer(int id) throws DAOConfigurationException {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.joinTransaction();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = criteriaBuilder.createCriteriaDelete(Computer.class);

		Root<Computer> root = criteriaDelete.from(Computer.class);
		Predicate computerIdPredicate = criteriaBuilder.equal(root.get("id"), id);
		criteriaDelete.where(computerIdPredicate);

		entityManager.createQuery(criteriaDelete).executeUpdate();

	}

	/**
	 * @param paginer
	 * @return List<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> computerList(Paginer paginer) throws DAOConfigurationException {

		int step = paginer.getStep();
		int offset = (paginer.getOffset() <= 0) ? 0 : paginer.getOffset();
		String search = paginer.getSearch();
		String orderBy = paginer.getOrderBy();

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);

		Join<Company, Computer> companyParty = root.join("company", JoinType.LEFT);
		if (!"".equals(search)) {
			search = "%" + search.toLowerCase() + "%";
			Predicate byComputerName = criteriaBuilder.like(root.get("name"), search);
			Predicate byCompanyName = criteriaBuilder.like(companyParty.get("name"), search);
			Predicate orSearch = criteriaBuilder.or(byComputerName, byCompanyName);
			criteriaQuery.where(orSearch);
		}

		TypedQuery<Computer> computerList = null;

		if ("".equals(orderBy)) {
			computerList = entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(step);
		} else {
			criteriaQuery = getOrderBy(criteriaQuery, criteriaBuilder, root, paginer.getOrderBy());
			computerList = entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(step);
		}

		return computerList.getResultList();
	}

	/**
	 * @param paginer
	 * @return List<Computer>
	 * @throws DAOConfigurationException
	 */
	public List<Computer> computerListInfo(Paginer paginer) throws DAOConfigurationException {

		String search = paginer.getSearch();
		String orderBy = paginer.getOrderBy();

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);

		Join<Company, Computer> companyParty = root.join("company", JoinType.LEFT);
		if (!"".equals(search)) {
			search = "%" + search.toLowerCase() + "%";
			Predicate byComputerName = criteriaBuilder.like(root.get("name"), search);
			Predicate byCompanyName = criteriaBuilder.like(companyParty.get("name"), search);
			Predicate orSearch = criteriaBuilder.or(byComputerName, byCompanyName);
			criteriaQuery.where(orSearch);
		}

		TypedQuery<Computer> computerList = null;

		if ("".equals(orderBy)) {
			computerList = entityManager.createQuery(criteriaQuery);
		} else {
			criteriaQuery = getOrderBy(criteriaQuery, criteriaBuilder, root, paginer.getOrderBy());
			computerList = entityManager.createQuery(criteriaQuery);
		}

		return computerList.getResultList();
	}

	/**
	 * @param id
	 * @return Computer
	 * @throws DAOConfigurationException
	 */
	public Optional<Computer> getComputerById(int id) throws DAOConfigurationException {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Predicate byId = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.select(root);
		criteriaQuery.where(byId);

		TypedQuery<Computer> computerQuery = entityManager.createQuery(criteriaQuery);
		Computer computer = computerQuery.getSingleResult();
		if (computer != null) {
			return Optional.of(computer);
		}
		return Optional.empty();
	}

	private CriteriaQuery<Computer> getOrderBy(CriteriaQuery<Computer> criteriaQuery, CriteriaBuilder criteriaBuilder,
			Root<Computer> root, String orderBy) {

		if (orderBy.endsWith(" asc")) {
			orderBy = orderBy.split(" ")[0];
			if (orderBy.startsWith("company")) {
				String[] splitted = orderBy.split("\\.");
				return criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(splitted[0]).get(splitted[1])));
			}
			return criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(orderBy)));
		}
		orderBy = orderBy.split(" ")[0];
		if (orderBy.startsWith("company")) {
			String[] splitted = orderBy.split("\\.");
			return criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(splitted[0]).get(splitted[1])));
		}

		return criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get(orderBy)));
	}

}
