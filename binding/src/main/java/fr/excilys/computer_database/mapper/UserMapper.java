package fr.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.excilys.computer_database.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        String username = rs.getString("username");
        String password = rs.getString("password");
 
        return new User(username, password);
    }
	
}
