package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.services.CompanyService;
import fr.excilys.computer_database.services.ComputerService;

/**
 * Servlet implementation class AddComputer
 */

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerDAO computerDao = ComputerDAO.getInstance();
	CompanyDAO companyDao = CompanyDAO.getInstance();

	ComputerService computerServ = ComputerService.getInstance(computerDao);
	CompanyService companyServ = CompanyService.getInstance(companyDao);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			ArrayList<Company> companyList = companyServ.getCompanyList();

			request.setAttribute("listeCompany", companyList);

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

		String error = "";
		request.setAttribute("error", error);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int companyId = 0;
		
		try {

			ComputerService computerServ = ComputerService.getInstance(computerDao);
			CompanyService companyServ = CompanyService.getInstance(companyDao);

			String computerName = request.getParameter("computerName");
			LocalDate introduced = request.getParameter("introduced").isEmpty() ? null
					: LocalDate.parse(request.getParameter("introduced"));
			LocalDate discontinued = request.getParameter("discontinued").isEmpty() ? null
					: LocalDate.parse(request.getParameter("discontinued"));

			String error = "";
			String cheatingError = "";

			if (introduced != null) {
				if (discontinued != null) {
					if (introduced.isAfter(discontinued)) {

						error = "date";
						request.setAttribute("error", error);

						doGet(request, response);

					} else {

						request.setAttribute("error", error);
						
						Company company = null;
						
						if (isInteger(request.getParameter("companyId"))) {
							
							companyId = Integer.parseInt(request.getParameter("companyId"));
							
							if(companyId < 0){
								cheatingError = "cheat";
								request.setAttribute("cheatingError", cheatingError);
								doGet(request,response);
							}
							
							boolean notSettingCompanyId = (companyId == 0);
							
							if (!notSettingCompanyId) {

								company = companyServ.getCompanyById(companyId);
							}
							
							Computer computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
									.setDiscontinued(discontinued).setCompany(company).build();

							computerServ.addComputer(computer);

							request.setAttribute("addSuccess", 1);
							
						} else {
							cheatingError = "cheat";
							request.setAttribute("cheatingError", cheatingError);
							doGet(request,response);
						}
					}
				}
			}

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());

		}

		response.sendRedirect("dashboard?addSuccess=1");

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
