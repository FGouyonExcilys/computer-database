package fr.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.excilys.computer_database.dao.UserDAO;
import fr.excilys.computer_database.model.User;
 
@Service
public class DBAuthenticationService implements UserDetailsService {
 
    @Autowired
    private UserDAO userDAO;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        User user = userDAO.findUser(username).get();
        System.out.println("User= " + user);
 
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
         
        // [USER,ADMIN,..]
        List<String> roles= userDAO.getUserRoles(username);
         
        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(roles!= null)  {
            for(String role: roles)  {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                grantList.add(authority);
            }
        }        
         
        UserDetails userDetails = 
        		(UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(), 
                user.getPassword(),grantList);
 
        return userDetails;
    }
     
}
