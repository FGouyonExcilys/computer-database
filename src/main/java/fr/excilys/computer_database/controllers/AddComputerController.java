package fr.excilys.computer_database.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computer_database.dto.CompanyDTO;
import fr.excilys.computer_database.dto.ComputerDTO;
import fr.excilys.computer_database.exceptions.ComputerValidator;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.exceptions.DateException;
import fr.excilys.computer_database.exceptions.NameException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.mapper.ComputerMapper;
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
	public String getAddComputer(@RequestParam(value="error", defaultValue="", required = false) String error, 
								 ModelMap modelMap){
		
		List<Company> companyList;
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
			
			CompanyDTO companydto = new CompanyDTO(Integer.parseInt(companyIdStr));
			ComputerDTO computerdto = new ComputerDTO(computerName, introducedStr, discontinuedStr, companydto);
			
			Computer computer = ComputerMapper.convertComputerDTOtoComputer(computerdto);
			
			ComputerValidator.validateComputer(computer);
				
			computerServ.addComputer(computer);
			modelMap.put("addSuccess", 1);

			return "redirect:/dashboard?addSuccess=1";
			
		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in AddComputer Servlet" + e.getMessage());
			return "redirect:/addComputer";
		} catch (DateException e) {
			modelMap.put("error", "incorrectDate");
			return "redirect:/addComputer";
		} catch (NameException e) {
			modelMap.put("error", "incorrectName");
			return "redirect:/addComputer";
		} catch (NumberFormatException e) {
			modelMap.put("error", "cheat");
			return "redirect:/addComputer";
		}

	}
}
