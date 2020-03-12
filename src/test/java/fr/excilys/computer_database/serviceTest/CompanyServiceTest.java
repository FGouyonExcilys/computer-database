/**
 * 
 */
package fr.excilys.computer_database.serviceTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.service.CompanyService;

/**
 * @author excilys
 *
 */

public class CompanyServiceTest {

	@Mock
	CompanyDAO companyDao = Mockito.mock(CompanyDAO.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCompanyList() throws ClassNotFoundException, DAOConfigurationException  {
		ArrayList<Company> companies = new ArrayList<>();
		Mockito.when(companyDao.lister()).thenReturn(companies);
		
		CompanyService compServ = new CompanyService(companyDao);
		
		assertEquals(compServ.getCompanyList(), companies);
	}
	
	@Test
	public void testGetCompanyListPaginer() throws ClassNotFoundException, DAOConfigurationException  {
		ArrayList<Company> companies = new ArrayList<>();
		Mockito.when(companyDao.lister(0,20)).thenReturn(companies);
		
		CompanyService compServ = new CompanyService(companyDao);
		
		assertEquals(compServ.getCompanyListPaginer(0,20), companies);
	}
	

}
