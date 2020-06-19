package fr.excilys.computer_database.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import fr.excilys.computer_database.model.Role;
import fr.excilys.computer_database.model.User;

@Repository
public class UserDAO extends JdbcDaoSupport {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private CriteriaBuilder criteriaBuilder;

	@Autowired
	public UserDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	@Transactional
	public Optional<User> findUser(String username) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		Predicate byUsername = criteriaBuilder.equal(root.get("username"), username);
		criteriaQuery.select(root);
		criteriaQuery.where(byUsername);

		TypedQuery<User> userQuery = entityManager.createQuery(criteriaQuery);
		User user = userQuery.getSingleResult();
		if (user != null) {
			return Optional.of(user);
		}
		return Optional.empty();
	}

	public List<String> getUserRoles(String username) {

		List<String> rolesList = new ArrayList<String>();
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
		Root<Role> root = criteriaQuery.from(Role.class);
		
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
		
		for(Role role : entityManager.createQuery(criteriaQuery).getResultList()) {
			rolesList.add(role.getUserRole());
		}
		System.out.println("Roles: " + rolesList);
		
		return rolesList;
	}

}
