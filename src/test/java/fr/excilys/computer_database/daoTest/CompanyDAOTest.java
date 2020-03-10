package fr.excilys.computer_database.daoTest;

import static org.junit.Assert.*;

import org.junit.*;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.DAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;

public class CompanyDAOTest {

	public static DAO daoTest;
	public static CompanyDAO companyDAO;
	
	@Before
	public void setUp() throws Exception 
	{
		daoTest = DAO.getInstanceH2();
		companyDAO=new CompanyDAO();
	}
	
	@After
	public void tearDown() throws Exception 
	{
		System.setProperty("test","false");
	}
	
	@Test
	public void testLister() throws DAOConfigurationException {
		System.out.println(companyDAO.lister().size());
		
		assertTrue(companyDAO.lister().size()==20);
	}

	@Test
	public void testListerIntInt() throws DAOConfigurationException {
		assertTrue(companyDAO.lister(0,20).size()==20);
	}

}
