package fr.excilys.computer_database.daoTest;

import static org.junit.Assert.*;

import org.junit.*;

import fr.excilys.computer_database.dao.*;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;

public class ComputerDAOTest {

	public static DAO daoTest;
	public static ComputerDAO computerDAO;
	
	@Before
	public void setUp() throws Exception 
	{
		daoTest = DAO.getInstanceH2();
		computerDAO=new ComputerDAO();
	}
	
	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void testAjouter() throws DAOConfigurationException {
		
		Computer computer=new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
				   .setIntroduced(null)
				   .setDiscontinued(null)
				   .setCompany(new Company.CompanyBuilder()
						   			  	  .setId(1)
						   			  	  .setName("Apple Inc.").build())
				   .build();
		
		int i = computerDAO.ajouter(computer);
		assertEquals(i, 1);
			
		
		
		
		
	}

	@Test
	public void testModifier() throws DAOConfigurationException {
		
		Company company = new Company.CompanyBuilder().setId(1).build();
		
		Computer computer=new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
				   .setIntroduced(null)
				   .setDiscontinued(null)
				   .setCompany(company)
				   .build();
		computer.setId(20);
		
		int i = computerDAO.modifier(computer);
		assertEquals(i, 1);
			
		
	}

	@Test
	public void testSupprimer() throws DAOConfigurationException {
		
		int i = computerDAO.supprimer(20);
		assertEquals(i, 1);
			
		
	}

	@Test
	public void testLister() throws DAOConfigurationException {
		
		assertTrue(computerDAO.lister().size()==20);
		
	}

	@Test
	public void testListerInt() throws DAOConfigurationException {
		
		Paginer p = new Paginer.PaginerBuilder().setOffset(0).setStep(20).build();
		
		assertTrue(computerDAO.lister(p).size()==20);
		
	}

	@Test
	public void testGetComputerById() throws DAOConfigurationException {

		int i = 1;
		
		Computer computer1=new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
									   .setIntroduced(null)
									   .setDiscontinued(null)
									   .setCompany(
											   new Company.CompanyBuilder()
											   			  .setId(1)
									   					  .setName("Apple Inc.").build())
									   .build();
		computer1.setId(i);
		
		
		Computer computer2=computerDAO.getComputerById(i);
		
		assertNotNull(computer2);
		
		assertEquals(computer1.toString(), computer2.toString());
		
	}

}
