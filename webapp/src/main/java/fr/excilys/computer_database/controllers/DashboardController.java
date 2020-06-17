package fr.excilys.computer_database.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.service.CompanyService;
import fr.excilys.computer_database.service.ComputerService;

@Controller
public class DashboardController {

	ComputerService computerServ;
	CompanyService companyServ;

	int lastPageIndex = 1;
	List<Computer> computerList = null;
	List<Computer> computerListDisplayed = null;

	public DashboardController(ComputerService computerServ, CompanyService companyServ) {
		this.computerServ = computerServ;
		this.companyServ = companyServ;
	}

	@GetMapping("/dashboard")
	public String getDashbord(@RequestParam(value = "search", defaultValue="", required = false) String search,
			// @Valid
			@RequestParam(value = "orderBy", defaultValue="", required = false) String orderBy,
			@RequestParam(value = "pageIterator", defaultValue = "1", required = false) int pageIterator,
			@RequestParam(value = "step", defaultValue = "10", required = false) int step,
			@RequestParam(value = "addSuccess", defaultValue = "0", required = false) int addSuccess,
			@RequestParam(value = "editSuccess", defaultValue = "0", required = false) int editSuccess,
			@RequestParam(value = "deleteSuccess", defaultValue = "0", required = false) int deleteSuccess,
			ModelMap modelMap) throws ServletException, IOException, DAOConfigurationException {

		Paginer paginer = new Paginer.PaginerBuilder().setOrderBy(orderBy).setSearch(search)
				.setOffset((pageIterator - 1) * step).setStep(step).build();
		
		computerList = computerServ.getComputerListInfo(paginer);
		computerListDisplayed = computerServ.getComputerList(paginer);
		
		lastPageIndex = (int) Math.ceil((double) computerList.size() / step);

		if (pageIterator > lastPageIndex) {
			pageIterator = 1;
		}
		
		modelMap.put("computerList", computerList);
		modelMap.put("computerListDisplayed", computerListDisplayed);

		modelMap.put("addSuccess", addSuccess);
		modelMap.put("editSuccess", editSuccess);
		modelMap.put("deleteSuccess", deleteSuccess);

		modelMap.put("search", search);
		modelMap.put("orderBy", orderBy);
		modelMap.put("pageIterator", pageIterator);
		modelMap.put("step", step);
		modelMap.put("lastPageIndex", lastPageIndex);

		return "dashboard";
	}

	@PostMapping("/dashboard")
	public String postDashboard(@RequestParam(value = "selection", required = false) String selection) {

		List<String> deleteSelectionArray = Arrays.asList(selection.split(","));

		for (String s : deleteSelectionArray) {
			try {
				computerServ.deleteComputer(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
			} catch (DAOConfigurationException e) {
				Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
			}
		}

		return "redirect:/dashboard?deleteSuccess=1";

	}

}
