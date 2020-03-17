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
public class EditComputerController {

	ComputerService computerServ;
	CompanyService companyServ;
	
	int idComputer = 0;
	
	public EditComputerController(ComputerService computerServ, CompanyService companyServ) {
		this.computerServ = computerServ;
		this.companyServ = companyServ;
	}
	
	@GetMapping("/editComputer")
	public String getEditComputer(@RequestParam(value="id", required = false) String id,
								  ModelMap modelMap) {
		
		try {

			ArrayList<Company> companyList = companyServ.getCompanyList();

			testIdComputerToEditNotNull(id);

			modelMap.put("listeCompany", companyList);

			Computer computer = computerServ.getComputerById(idComputer);

			setAttributesComputer(modelMap, computer);

		} catch (DAOConfigurationException eDAOConfig) {
			Loggers.afficherMessageError("DAOConfigurationException in EditComputer Controller" + eDAOConfig.getMessage());
		}
		
		return "editComputer";
		
	}
	
	@PostMapping("/editComputer")
	public String postEditComputer(@RequestParam(value="id", required = false) String id,
								   @RequestParam(value="computerName", required = false) String computerName,
								   @RequestParam(value="introduced", required = false) String introducedStr,
								   @RequestParam(value="discontinued", required = false) String discontinuedStr,
								   @RequestParam(value="companyId", required = false) String companyIdStr,
								   ModelMap modelMap) {
		int companyId = 0;
		String error = "";
		String cheatingError = "";

		try {

			testIdComputerToEditNotNull(id);

			LocalDate introduced = introducedStr.isEmpty() ? null
														   : LocalDate.parse(introducedStr);
			LocalDate discontinued = discontinuedStr.isEmpty() ? null
														   : LocalDate.parse(discontinuedStr);

			if (introduced != null) {
				if (discontinued != null) {
					if (introduced.isAfter(discontinued)) {
						modelMap.put("error", "date");
						return "redirect:/editComputer";
					} 
				}
			}
			
			modelMap.put("error", error);

			Company company = null;
			
			if (isInteger(companyIdStr)) {

				companyId = Integer.parseInt(companyIdStr);

				if (companyId < 0) {
					cheatingError = "cheat";
					modelMap.put("cheatingError", cheatingError);
					return "redirect:/editComputer";
				}
				
				boolean removeCompanyId = (companyId != 0);

				if (!removeCompanyId) {

					company = companyServ.getCompanyById(companyId);
				}

				Computer computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
						.setDiscontinued(discontinued).setCompany(company).build();
				computer.setId(idComputer);

				computerServ.editComputer(computer);

				modelMap.put("editSuccess", 1);
				
			} else {
				cheatingError = "cheat";
				modelMap.put("cheatingError", cheatingError);
				return "redirect:/editComputer";
			}

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in EditComputer Servlet" + e.getMessage());
		}

		modelMap.put("editSuccess", 1);

		return "redirect:/dashboard?editSuccess=1";
		
	}
	
	
	private String testIdComputerToEditNotNull(String id) {
		if (id != null) {
			try {
				idComputer = Integer.parseInt(id);
			} catch (NumberFormatException eNumFormat) {
				Loggers.afficherMessageError("NumberFormatException in EditComputer Controller" + eNumFormat.getMessage());
				return "redirect:/dashboard?pageIterator=1";
			}
		}
		return null;
	}

	private void setAttributesComputer(ModelMap modelMap, Computer computer) {

		modelMap.put("id", computer.getId());
		modelMap.put("name", computer.getName());
		modelMap.put("introduced", computer.getIntroduced());
		modelMap.put("discontinued", computer.getDiscontinued());
		modelMap.put("currentCompany", computer.getCompany().getId());
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
