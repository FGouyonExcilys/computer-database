package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.logging.Loggers;
import fr.excilys.computer_database.model.Company;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.service.CompanyService;
import fr.excilys.computer_database.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */

@WebServlet("/addComputer")
@Controller
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ComputerService computerServ;
	@Autowired
	CompanyService companyServ;
	
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	public AddComputer() {
		super();
	}

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
			
			Company company = null;
			Computer computer = null;

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
					} 
				}
			}
			
			request.setAttribute("error", error);
			if (isInteger(request.getParameter("companyId"))) {
				companyId = Integer.parseInt(request.getParameter("companyId"));
				
				if(companyId < 0){
					cheatingError = "cheat";
					request.setAttribute("cheatingError", cheatingError);
					doGet(request,response);
				}
				
				boolean settingCompanyId = (companyId != 0);
				computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
						.setDiscontinued(discontinued).build();
				
				if (settingCompanyId) {
					company = companyServ.getCompanyById(companyId);
					computer.setCompany(company);
				}
				computerServ.addComputer(computer);

				request.setAttribute("addSuccess", 1);
			} else {
				cheatingError = "cheat";
				request.setAttribute("cheatingError", cheatingError);
				doGet(request,response);
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
