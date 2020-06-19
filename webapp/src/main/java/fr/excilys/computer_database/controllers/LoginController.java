package fr.excilys.computer_database.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping(value = "/logoutSuccessful")
	   public String logoutSuccessful(ModelMap modelMap) {
	       modelMap.addAttribute("title", "Logout");
	       return "logoutSuccessful";
	}
	
	@GetMapping(value = "/403")
	   public String accessDenied(ModelMap modelMap) {
	        
	       return "403";
	   }

}
