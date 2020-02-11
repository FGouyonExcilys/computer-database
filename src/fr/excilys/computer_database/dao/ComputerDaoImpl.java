package fr.excilys.computer_database.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.dto.Computer;

public class ComputerDaoImpl implements ComputerDao {
    
	private static ComputerDaoImpl INSTANCE = null;
    
    /** Point d'accès pour l'instance unique du singleton */
    public static ComputerDaoImpl getInstance(Dao dao)
    {
        if (INSTANCE == null)
        {   INSTANCE = new ComputerDaoImpl(dao); 
        }
        return INSTANCE;
    }
	
	private Dao dao;

    private ComputerDaoImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void ajouter(Computer computer) {
    	
        try {
        	Connection connexion = dao.getConnection();
        	
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) "
            															   + "VALUES(?, ?, ?, ?);");
            preparedStatement.setString(1, computer.getName());
            preparedStatement.setDate(2, computer.getIntroduced());
            preparedStatement.setDate(3, computer.getDiscontinued());
            preparedStatement.setInt(4, computer.getCompany_id());

            preparedStatement.executeUpdate();
            
            connexion.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * @param Computer computer
     * 
     */
    @Override
    public void modifier(Computer computer) {
    	
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = dao.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE computer "
            											 + "SET name= ?, introduced= ?, discontinued= ?, company_id= ? "
            											 + "WHERE id = ?;");
            
            preparedStatement.setString(1, computer.getName());
            preparedStatement.setObject(2, computer.getIntroduced());
            preparedStatement.setObject(3, computer.getDiscontinued());
            preparedStatement.setInt(4, computer.getCompany_id());
            preparedStatement.setInt(5, computer.getId());

            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
	public void supprimer(int id) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = dao.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM computer WHERE id = ?;");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
    
    @Override
    public List<Computer> lister() {
        List<Computer> computers = new ArrayList<Computer>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
        	
            connexion = dao.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM computer;");

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String name = resultat.getString("name");
                Date introduced = resultat.getDate("introduced");
            	Date discontinued = resultat.getDate("discontinued");
            	int company_id = resultat.getInt("company_id");

                Computer computer = new Computer();
                computer.setId(id);
                computer.setName(name);
                computer.setIntroduced(introduced);
                computer.setDiscontinued(discontinued);
                computer.setCompany_id(company_id);

                computers.add(computer);
                
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return computers;
    }
    
    @Override
    public String afficherInfoComputer(int id) {
    	
    	Computer computer = new Computer();

        try {
        	
        	Connection connexion = dao.getConnection();
        	Statement statement = connexion.createStatement();
        	ResultSet resultat = statement.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = " + id + ";");

            while (resultat.next()) {
                String name = resultat.getString("name");
                Date introduced = resultat.getDate("introduced");
                Date discontinued = resultat.getDate("discontinued");
            	int company_id = resultat.getInt("company_id");

                computer.setId(id);
                computer.setName(name);
                computer.setIntroduced(introduced);
                computer.setDiscontinued(discontinued);
                computer.setCompany_id(company_id);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return computer.toString();
    }

}
