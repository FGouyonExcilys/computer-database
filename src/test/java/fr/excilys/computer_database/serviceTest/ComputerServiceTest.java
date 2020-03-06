/**
 * 
 */
package fr.excilys.computer_database.serviceTest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.services.ComputerService;

/**
 * @author excilys
 *
 */

public class ComputerServiceTest {

	@Mock
	ComputerDAO computerDao = Mockito.mock(ComputerDAO.class);

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

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#getComputerList()}.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testGetComputerList() throws SQLException, ClassNotFoundException, DAOConfigurationException {
		ArrayList<Computer> computers = new ArrayList<>();
		Mockito.when(computerDao.lister()).thenReturn(computers);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.getComputerList(), computers);
	}

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#getComputerListPaginer(int, int)}.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testGetComputerListPaginer() throws ClassNotFoundException, SQLException, DAOConfigurationException {
		
		ArrayList<Computer> computers = new ArrayList<>();

		Paginer p = new Paginer.PaginerBuilder().setOffset(0).setStep(20).build();
		
		Mockito.when(computerDao.lister(p)).thenReturn(computers);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.getComputerListPaginer(p), computers);
	}

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#deleteComputer(int)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testAddComputer() throws SQLException, ClassNotFoundException, DAOConfigurationException {
		
		int ajouter = 0;

		Company company = new Company.CompanyBuilder().setId(1).build();

		Computer computer = new Computer.ComputerBuilder("MacBook Pro 15.4 inch").setIntroduced(null)
				.setDiscontinued(null).setCompany(company).build();
		computer.setId(20);

		Mockito.when(computerDao.ajouter(computer)).thenReturn(ajouter);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.addComputer(computer), ajouter);
	}

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#editComputer(fr.excilys.computer_database.model.Computer)}.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testEditComputer() throws ClassNotFoundException, SQLException, DAOConfigurationException {

		int modifier = 0;

		Company company = new Company.CompanyBuilder().setId(1).build();

		Computer computer = new Computer.ComputerBuilder("MacBook Pro 15.4 inch").setIntroduced(null)
				.setDiscontinued(null).setCompany(company).build();
		computer.setId(20);

		Mockito.when(computerDao.modifier(computer)).thenReturn(modifier);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.editComputer(computer), modifier);
	}

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#deleteComputer(int)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testDeleteComputer() throws ClassNotFoundException, SQLException, DAOConfigurationException {
		
		int supprimer = 0;

		Mockito.when(computerDao.supprimer(20)).thenReturn(supprimer);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.deleteComputer(20), supprimer);
	}

	/**
	 * Test method for
	 * {@link fr.excilys.computer_database.services.ComputerService#getComputerById(int)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws DAOConfigurationException 
	 */
	@Test
	public void testGetComputerById() throws ClassNotFoundException, SQLException, DAOConfigurationException {
		
		
		Company company = new Company.CompanyBuilder().setId(1).build();

		Computer computer = new Computer.ComputerBuilder("MacBook Pro 15.4 inch").setIntroduced(null)
				.setDiscontinued(null).setCompany(company).build();
		computer.setId(1);

		Mockito.when(computerDao.getComputerById(1)).thenReturn(computer);

		ComputerService compServ = new ComputerService(computerDao);

		assertEquals(compServ.getComputerById(1), computer);
	}

}
