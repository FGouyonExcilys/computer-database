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
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int idComputer = 0;

	ComputerDAO computerDao;
	CompanyDAO companyDao;

	ComputerService computerServ;
	CompanyService companyServ;

	@Override
	public void init() throws ServletException {

		computerDao = ComputerDAO.getInstance();
		companyDao = CompanyDAO.getInstance();

		computerServ = ComputerService.getInstance(computerDao);
		companyServ = CompanyService.getInstance(companyDao);

		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			ArrayList<Company> companyList = companyServ.getCompanyList();

			testIdNotNull(request, response);

			request.setAttribute("listeCompany", companyList);

			Computer computer = computerServ.getComputerById(idComputer);

			setAttributesComputer(request, computer);

		} catch (DAOConfigurationException e1) {
			Loggers.afficherMessageError("Problème DAOConfig \n" + e1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			testIdNotNull(request, response);

			String computerName = request.getParameter("computerName");
			LocalDate introduced = request.getParameter("introduced").isEmpty() ? null
					: LocalDate.parse(request.getParameter("introduced"));
			LocalDate discontinued = request.getParameter("discontinued").isEmpty() ? null
					: LocalDate.parse(request.getParameter("discontinued"));
			int company_id = Integer.parseInt(request.getParameter("companyId"));

			String error = "";

			if (introduced != null) {
				if (discontinued != null) {
					if (introduced.isAfter(discontinued)) {

						error = "date";
						request.setAttribute("error", error);

						doGet(request, response);

					} else {
						request.setAttribute("error", error);

						Company company = companyServ.getCompanyById(company_id);

						Computer computer = new Computer.ComputerBuilder(computerName).setIntroduced(introduced)
								.setDiscontinued(discontinued).setCompany(company).build();
						computer.setId(idComputer);

						computerServ.editComputer(computer);

						request.setAttribute("editSuccess", 1);
					}
				}
			}

		} catch (ClassNotFoundException | DAOConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("editSuccess", 1);

		response.sendRedirect("dashboard?editSuccess=1");
	}

	private void testIdNotNull(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("id") != null) {
			try {
				idComputer = Integer.parseInt(request.getParameter("id"));
			} catch (NumberFormatException e) {
				try {
					response.sendRedirect("dashboard?pageIterator=1");
				} catch (IOException e1) {
					e1.printStackTrace();
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
}
