package fr.excilys.computer_database.daoTest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;

import fr.excilys.computer_database.dao.*;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Computer;

public class ComputerDAOTest {

	public static DAO daoTest;
	public static ComputerDAO computerDAO;
	
	@Before
	public void setUp() throws Exception 
	{
		daoTest = DAO.getInstanceH2();
		computerDAO=ComputerDAO.getInstance(daoTest);
	}
	
	@After
	public void tearDown() throws Exception 
	{
		System.setProperty("test","false");
	}

	@Test
	public void testAjouter() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifier() {
		fail("Not yet implemented");
	}

	@Test
	public void testSupprimer() {
		fail("Not yet implemented");
	}

	@Test
	public void testLister() {
		try {
			assertTrue(computerDAO.lister().size()==20);
		} catch (SQLException e) {
			Loggers.afficherMessageWarn("Erreur Test testLister()");
		}
	}

	@Test
	public void testListerInt() {
		try {
			assertTrue(computerDAO.lister(0,20).size()==20);
		} catch (SQLException e) {
			Loggers.afficherMessageWarn("Erreur Test testListerInt()");
		}
	}

	@Test
	public void testAfficherInfoComputer() {

		Computer computer1=new Computer.ComputerBuilder("MacBook Pro 15.4 inch").build();
		computer1.setId(1);
		String stringComputer1=computer1.toString();
		
		String stringComputer2=computerDAO.afficherInfoComputer(1);
		
		assertNotNull(stringComputer2);
		
		assertEquals(stringComputer1, stringComputer2);
		
	}

}
