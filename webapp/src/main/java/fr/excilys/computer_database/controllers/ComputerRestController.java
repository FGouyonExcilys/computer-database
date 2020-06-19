package fr.excilys.computer_database.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.service.CompanyService;
import fr.excilys.computer_database.service.ComputerService;

@RestController
@RequestMapping(value="/computers")
public class ComputerRestController {

	ComputerService computerServ;
	CompanyService companyServ;

	List<Computer> computerList = null;

	public ComputerRestController(ComputerService computerServ, CompanyService companyServ) {
		this.computerServ = computerServ;
		this.companyServ = companyServ;
	}

	@GetMapping("/")
	public List<Computer> getAllComputers() throws DAOConfigurationException {
		
		Paginer paginer = new Paginer.PaginerBuilder().setSearch("").setOrderBy("").build();
		computerList = computerServ.getComputerListInfo(paginer);
		return computerList;
	}

	@GetMapping("/computer/{id}")
	public Computer getOneComputer(@PathVariable Integer id) throws DAOConfigurationException {
		return computerServ.getComputerById(id);
		
	}


}
