package fr.excilys.computer_database.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.excilys.computer_database.service.DBAuthenticationService;

@Controller
public class LoginController {
	
	DBAuthenticationService dbAuthServ;
	
	public LoginController(DBAuthenticationService dbAuthServ) {
		this.dbAuthServ = dbAuthServ;
	}
	
	@GetMapping(value = "/login")
	public String getLogin(ModelMap modelMap) {
		return "login";

	}

	@GetMapping(value = "/user")
	public String user(ModelMap modelMap, Principal principal) {

		// After user login successfully.
		String username = principal.getName();

		System.out.println("Username: " + username);

		return "user";
	}
	
	@GetMapping(value = "/logoutSuccessful")
	   public String logoutSuccessful(ModelMap modelMap) {
	       modelMap.addAttribute("title", "Logout");
	       return "logoutSuccessful";
	}
	
	@GetMapping(value = "/403")
	   public String accessDenied(ModelMap modelMap, Principal principal) {
	        
	       if (principal != null) {
	           modelMap.put("message", "Hi " + principal.getName()
	                   + "<br> You do not have permission to access this page!");
	       } else {
	           modelMap.put("msg",
	                   "You do not have permission to access this page!");
	       }
	       return "403";
	   }

}
