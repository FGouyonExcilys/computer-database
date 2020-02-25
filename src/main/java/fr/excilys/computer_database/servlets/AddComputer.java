package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.dao.DAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.services.CompanyService;

/**
 * Servlet implementation class AddComputer
 */

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CompanyDAO companyDao;
        
        try {
        	
			companyDao = CompanyDAO.getInstance(DAO.getInstance());
			
			ArrayList<Company> companyList = CompanyService.getInstance(companyDao).getCompanyList();
			
			request.setAttribute("listeCompany", companyList);
			
			
		} catch (DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ComputerDAO computerDao = ComputerDAO.getInstance(DAO.getInstance());
			CompanyDAO companyDao = CompanyDAO.getInstance(DAO.getInstance());
			
	        String computerName = request.getParameter("computerName");
	        String introduced = request.getParameter("introduced");
	        String discontinued = request.getParameter("discontinued");
	        int company_id = Integer.parseInt(request.getParameter("companyId"));
	        
	        Company company = CompanyService.getInstance(companyDao).getCompanyById(company_id);
	        
			Computer computer = new Computer.ComputerBuilder(computerName)
					.setIntroduced(LocalDate.parse(introduced))
					.setDiscontinued(LocalDate.parse(discontinued))
					.setCompany(company).build();
			
			computerDao.ajouter(computer);
			
		} catch (ClassNotFoundException | DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		response.sendRedirect("dashboard?pageIterator=1");
	}

}
