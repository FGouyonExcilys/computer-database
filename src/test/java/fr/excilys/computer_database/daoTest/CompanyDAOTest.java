package fr.excilys.computer_database.daoTest;

import static org.junit.Assert.*;

import org.junit.*;

public class CompanyDAOTest {

	@Before
	public void setUp() throws Exception 
	{
		System.setProperty("test","true");
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
