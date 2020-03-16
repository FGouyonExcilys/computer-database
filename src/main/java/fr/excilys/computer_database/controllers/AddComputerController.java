package fr.excilys.computer_database.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.service.CompanyService;
import fr.excilys.computer_database.service.ComputerService;

@Controller
public class AddComputerController {
	
	ComputerService computerServ;
	CompanyService companyServ;
	
	public AddComputerController(ComputerService computerServ, CompanyService companyServ) {
		this.computerServ = computerServ;
		this.companyServ = companyServ;
	}
	
	@GetMapping("/addComputer")
	public String getAddComputer(@RequestParam(value="error", defaultValue="", required = false) int error, 
								 ModelMap modelMap){
		
		ArrayList<Company> companyList;
		try {
			companyList = companyServ.getCompanyList();
			modelMap.put("listeCompany", companyList);
			modelMap.put("error", error);
		} catch (DAOConfigurationException eDAOConfig) {
			Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + eDAOConfig.getMessage());
		}

		return "addComputer";
		
	}
	

	@PostMapping("/addComputer")
	public String postDashboard(@RequestParam(value="computerName", required = false) String computerName,
			  @RequestParam(value="introduced", required = false) String introducedStr,
			  @RequestParam(value="discontinued", required = false) String discontinuedStr,
			  @RequestParam(value="companyId", required = false) String companyIdStr,
			  @RequestParam(value="addSuccess", defaultValue="0", required = false) int addSuccess,
			  @RequestParam(value="editSuccess", defaultValue="0", required = false) int editSuccess,
			  ModelMap modelMap) {
		
		try {
			
			Company company = null;
			Computer computer = null;
			int companyId = 0;
			
			LocalDate introduced = introducedStr.isEmpty() ? null
					: LocalDate.parse(introducedStr);
			LocalDate discontinued = discontinuedStr.isEmpty() ? null
					: LocalDate.parse(discontinuedStr);

			String error = "";

			if (introduced != null) {
				if (discontinued != null) {
					if (introduced.isAfter(discontinued)) {
						modelMap.put("error", "date");
						return "redirect:/addComputer";
					} 
				}
			}
			
			modelMap.put("error", error);
			
			if (isInteger(companyIdStr)) {
				companyId = Integer.parseInt(companyIdStr);
				if(companyId < 0){
					modelMap.put("cheatingError", "cheat");
					return "redirect:/addComputer";
				}
				
				boolean settingCompanyId = (companyId != 0);
				computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
						.setDiscontinued(discontinued).build();
				
				if (settingCompanyId) {
					company = companyServ.getCompanyById(companyId);
					computer.setCompany(company);
				}
				computerServ.addComputer(computer);
				modelMap.put("addSuccess", 1);
				
			} else {
				modelMap.put("cheatingError", "cheat");
				return "redirect:/addComputer";
			}
		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
		}

		return "redirect:/dashboard?addSuccess=1";

	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
}
