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
	List<Computer> computerListPaginer = null;

	List<Computer> computerListSearch = null;
	List<Computer> computerListSearchPaginer = null;
	
	public DashboardController(ComputerService computerServ, CompanyService companyServ) {
		this.computerServ = computerServ;
		this.companyServ = companyServ;
	}
	
	
	@GetMapping("/dashboard")
	public String getDashbord(@RequestParam(value="search", required = false) String search,
								//@Valid
							  @RequestParam(value="orderBy", required = false) String orderBy,
							  @RequestParam(value="columnName", required = false) String columnName,
							  @RequestParam(value="pageIterator", defaultValue="1", required = false) int pageIterator,
							  @RequestParam(value="step", defaultValue="10", required = false) int step,
							  @RequestParam(value="addSuccess", defaultValue="0", required = false) int addSuccess,
							  @RequestParam(value="editSuccess", defaultValue="0", required = false) int editSuccess,
							  ModelMap modelMap)
									  throws ServletException, IOException, DAOConfigurationException {
		
		if (search != null) {

			computerListSearch = computerServ.getComputerListSearched(search);

			lastPageIndex = (int) Math.ceil((double) computerListSearch.size() / step);
			
			if(pageIterator > lastPageIndex) {
				pageIterator = 1;
			}
			
			Paginer paginer = new Paginer.PaginerBuilder().setOrderBy(orderBy)
														  .setColumnName(columnName)
														  .setSearch(search)
														  .setOffset((pageIterator - 1) * step)
														  .setStep(step).build();
			
			computerListSearchPaginer = computerServ.getComputerListSearchedPaginer(paginer);
			
			modelMap.put("listeOrdiSearched", computerListSearch);
			modelMap.put("listeOrdiSearchedPaginer", computerListSearchPaginer);

		} else {
			
			Paginer paginer = new Paginer.PaginerBuilder().setOrderBy(orderBy)
					  									  .setColumnName(columnName)
														  .setOffset((pageIterator - 1) * step)
														  .setStep(step).build();
			
			computerList = computerServ.getComputerList();
			computerListPaginer = computerServ.getComputerListPaginer(paginer);

			lastPageIndex = (int) Math.ceil((double) computerList.size() / step);

			modelMap.put("listeOrdi", computerList);
			modelMap.put("listeOrdiPaginer", computerListPaginer);
		}

		modelMap.put("addSuccess", addSuccess);
		modelMap.put("editSuccess", editSuccess);

		modelMap.put("search", search);
		modelMap.put("orderBy", orderBy);
		modelMap.put("pageIterator", pageIterator);
		modelMap.put("step", step);
		modelMap.put("lastPageIndex", lastPageIndex);
		modelMap.put("columnName", columnName);
		
		return "dashboard";
	}

	@PostMapping("/dashboard")
	public String postDashboard(@RequestParam(value="search", required = false) String selection){

		List<String> deleteSelectionArray = Arrays.asList(selection.split("\\s,\\s"));

		for (String s : deleteSelectionArray) {
			try {
				computerServ.deleteComputer(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
			} catch (DAOConfigurationException e) {
				Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
			}
		}

		return "redirect:/dashboard";
		
	}

}
