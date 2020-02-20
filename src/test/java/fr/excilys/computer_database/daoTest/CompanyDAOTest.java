package fr.excilys.computer_database.daoTest;

import static org.junit.Assert.*;

import org.junit.*;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.DAO;

public class CompanyDAOTest {

	public static DAO daoTest;
	public static CompanyDAO companyDAO;
	
	@Before
	public void setUp() throws Exception 
	{
		daoTest = DAO.getInstanceH2();
		companyDAO=CompanyDAO.getInstance(daoTest);
	}
	
	@After
	public void tearDown() throws Exception 
	{
		System.setProperty("test","false");
	}
	
	@Test
	public void testLister() {
		fail("Not yet implemented");
	}

	@Test
	public void testListerIntInt() {
		fail("Not yet implemented");
	}

}
