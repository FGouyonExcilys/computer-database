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
import fr.excilys.computer_database.services.CompanyService;
import fr.excilys.computer_database.services.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
@Controller
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ComputerService computerServ;
	@Autowired
	CompanyService companyServ;
	
	private int idComputer = 0;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			ArrayList<Company> companyList = companyServ.getCompanyList();

			testIdComputerToEditNotNull(request, response);

			request.setAttribute("listeCompany", companyList);

			Computer computer = computerServ.getComputerById(idComputer);

			setAttributesComputer(request, computer);

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in EditComputer Servlet" + e.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int companyId = 0;
		String error = "";
		String cheatingError = "";

		try {

			testIdComputerToEditNotNull(request, response);

			String computerName = request.getParameter("computerName");
			LocalDate introduced = request.getParameter("introduced").isEmpty() ? null
					: LocalDate.parse(request.getParameter("introduced"));
			LocalDate discontinued = request.getParameter("discontinued").isEmpty() ? null
					: LocalDate.parse(request.getParameter("discontinued"));

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

							if (companyId < 0) {
								cheatingError = "cheat";
								request.setAttribute("cheatingError", cheatingError);
								doGet(request, response);
							}
							
							boolean removeCompanyId = (request.getParameter("companyId").equals("none"));

							if (!removeCompanyId) {

								company = companyServ.getCompanyById(companyId);
							}

							Computer computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
									.setDiscontinued(discontinued).setCompany(company).build();
							computer.setId(idComputer);

							computerServ.editComputer(computer);

							request.setAttribute("editSuccess", 1);
							
						} else {
							cheatingError = "cheat";
							request.setAttribute("cheatingError", cheatingError);
							doGet(request,response);
						}
					}
				}
			}

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in EditComputer Servlet" + e.getMessage());
		}

		request.setAttribute("editSuccess", 1);

		response.sendRedirect("dashboard?editSuccess=1");
	}

	
	
	private void testIdComputerToEditNotNull(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("id") != null) {
			try {
				idComputer = Integer.parseInt(request.getParameter("id"));
			} catch (NumberFormatException e) {
				Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
				try {
					response.sendRedirect("dashboard?pageIterator=1");
				} catch (IOException e1) {

					Loggers.afficherMessageError("IOException in Dashboard Servlet" + e1.getMessage());
				}
			}
		}
	}

	private void setAttributesComputer(HttpServletRequest request, Computer computer) {

		request.setAttribute("id", computer.getId());
		request.setAttribute("name", computer.getName());
		request.setAttribute("introduced", computer.getIntroduced());
		request.setAttribute("discontinued", computer.getDiscontinued());
		request.setAttribute("currentCompany", computer.getCompany().getId());
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
