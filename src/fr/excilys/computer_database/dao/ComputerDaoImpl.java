package fr.excilys.computer_database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computer_database.dto.Computer;

public class ComputerDaoImpl implements ComputerDao {
    
	private Dao dao;

    public ComputerDaoImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void ajouter(Computer computer) {
    	
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = dao.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO computer(id, name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, computer.getId());
            preparedStatement.setString(2, computer.getName());
            preparedStatement.setDate(3, computer.getIntroduced());
            preparedStatement.setDate(4, computer.getDiscontinued());
            preparedStatement.setInt(5, computer.getCompany_id());

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
            resultat = statement.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer;");

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String name = resultat.getString("name");
                Date introduced = resultat.getDate("introduced");;
            	Date discontinued = resultat.getDate("discontinued");;
            	int company_id = resultat.getInt("company_id");;

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

}
