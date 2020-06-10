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

			List<Company> companyList = companyServ.getCompanyList();

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

		try {

			testIdComputerToEditNotNull(id);

			CompanyDTO companyDTO = new CompanyDTO(Integer.parseInt(companyIdStr));
			ComputerDTO computerDTO = new ComputerDTO(computerName, introducedStr, discontinuedStr, companyDTO);
			
			Computer computer = ComputerMapper.convertComputerDTOtoComputer(computerDTO);
			
			ComputerValidator.validateComputer(computer);

			computerServ.editComputer(computer);

			modelMap.put("editSuccess", 1);

			return "redirect:/dashboard?editSuccess=1";
			

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in EditComputer Servlet" + e.getMessage());
			return "redirect:/editComputer";
		} catch (DateException e) {
			modelMap.put("error", "incorrectDate");
			return "redirect:/editComputer";
		} catch (NameException e) {
			modelMap.put("error", "incorrectName");
			return "redirect:/editComputer";
		} catch (NumberFormatException e) {
			modelMap.put("error", "cheat");
			return "redirect:/editComputer";
		}
		
	}
	
	
	private String testIdComputerToEditNotNull(String id) {
		try {
			idComputer = Integer.parseInt(id);
			return null;
		} catch (NumberFormatException eNumFormat) {
			Loggers.afficherMessageError("NumberFormatException in EditComputer Controller" + eNumFormat.getMessage());
			return "redirect:/dashboard?pageIterator=1";
		}
		
	}

	private void setAttributesComputer(ModelMap modelMap, Computer computer) {

		modelMap.put("id", computer.getId());
		modelMap.put("name", computer.getName());
		modelMap.put("introduced", computer.getIntroduced());
		modelMap.put("discontinued", computer.getDiscontinued());
		modelMap.put("currentCompany", computer.getCompany().getId());
	}
}
