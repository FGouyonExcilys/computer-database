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
		
		String error = "";
    	request.setAttribute("error", error);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ComputerDAO computerDao = ComputerDAO.getInstance(DAO.getInstance());
			CompanyDAO companyDao = CompanyDAO.getInstance(DAO.getInstance());
			
	        String computerName = request.getParameter("computerName");
	        LocalDate introduced = request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced"));
	        LocalDate discontinued = request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")) ;
	        int company_id = Integer.parseInt(request.getParameter("companyId"));
	        
	        String error = "";
	        
	        if (introduced != null) {
	        	if(discontinued != null) {
	        		if(introduced.isAfter(discontinued)){
	        			
	        			error = "date";
			        	request.setAttribute("error", error);
			        	
			        	doGet(request, response);
			        	
	        		} else {
	        			
	        			request.setAttribute("error", error);
	        			
	        			Company company = CompanyService.getInstance(companyDao).getCompanyById(company_id);
	        			Computer computer = new Computer.ComputerBuilder(computerName)
	        					.setIntroduced(introduced)
	        					.setDiscontinued(discontinued)
	        					.setCompany(company).build();
	        			
	        			computerDao.ajouter(computer);
	        			
	        			request.setAttribute("addSuccess", 1);
	        		}
	        	}
	        }
	        
		} catch (ClassNotFoundException | DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("addSuccess", 1);
		
		response.sendRedirect("dashboard?addSuccess=1");
		
	}

}
