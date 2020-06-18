package fr.excilys.computer_database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	 
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	    authProvider.setUserDetailsService(userDetailsService);
//	    authProvider.setPasswordEncoder(encoder());
//	    return authProvider;
//	}
}
