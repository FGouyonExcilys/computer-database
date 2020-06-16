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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.mapper.CompanyMapper;
import fr.excilys.computer_database.model.Company;

@Repository
public class CompanyDAO {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	private CriteriaBuilder criteriaBuilder;
	
	ComputerDAO computerDao;
	CompanyMapper companyMapper;
	
	public void init() {
		this.criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
	}

	@Transactional
	public void deleteCompany(int companyId) throws DAOConfigurationException {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.joinTransaction();
		criteriaBuilder= entityManager.getCriteriaBuilder();
		CriteriaDelete< Company>criteriaDelete=criteriaBuilder.createCriteriaDelete(Company.class);
		
		Root<Company>root=criteriaDelete.from(Company.class);
		Predicate byId=criteriaBuilder.equal(root.get("id"), companyId);
		
		criteriaDelete.where(byId);
		entityManager.createQuery(criteriaDelete).executeUpdate();

	}

	public List<Company> lister() throws DAOConfigurationException {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
		
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		
		TypedQuery<Company> companyList = entityManager.createQuery(criteriaQuery);
		return companyList.getResultList();
	}

	public Optional<Company> getCompanyById(int id) throws DAOConfigurationException {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery=criteriaBuilder.createQuery(Company.class);
		
		Root<Company>root=criteriaQuery.from(Company.class);
		Predicate byId = criteriaBuilder.equal(root.get("id"),id);
		
		criteriaQuery.where(byId);
		 
		TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
	    Company company = typedQuery.getSingleResult();
		if(company !=null) {
			return Optional.of(company);
		}
		return Optional.empty();
		
	}

}
