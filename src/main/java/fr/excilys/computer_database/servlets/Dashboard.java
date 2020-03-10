package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.services.CompanyService;
import fr.excilys.computer_database.services.ComputerService;

@WebServlet("/dashboard")
@Controller
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	ComputerService computerServ;
	@Autowired
	CompanyService companyServ;

	private int pageIterator = 1;
	private int step = 10;
	private String search = null;
	private int lastPageIndex = 1;
	private String orderBy = null;
	private String columnName = null;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Computer> computerList = null;
		ArrayList<Computer> computerListPaginer = null;

		ArrayList<Computer> computerListSearch = null;
		ArrayList<Computer> computerListSearchPaginer = null;

		try {
			if (request.getParameter("pageIterator") != null) {
				try {
					pageIterator = Integer.parseInt(request.getParameter("pageIterator"));
				} catch (NumberFormatException e) {
					Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
					this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
				}
			}

			if (request.getParameter("step") != null) {
				try {
					step = Integer.parseInt(request.getParameter("step"));
				} catch (NumberFormatException e) {
					Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
					this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
				}
			}
			
			orderBy = request.getParameter("orderBy");
			columnName = request.getParameter("columnName");

			if (request.getParameter("search") != null) {

				search = request.getParameter("search");

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
				
				request.setAttribute("listeOrdiSearched", computerListSearch);
				request.setAttribute("listeOrdiSearchedPaginer", computerListSearchPaginer);

			} else {

				search = null;
				
				Paginer paginer = new Paginer.PaginerBuilder().setOrderBy(orderBy)
						  									  .setColumnName(columnName)
															  .setOffset((pageIterator - 1) * step)
															  .setStep(step).build();
				
				computerList = computerServ.getComputerList();
				computerListPaginer = computerServ.getComputerListPaginer(paginer);

				lastPageIndex = (int) Math.ceil((double) computerList.size() / step);

				request.setAttribute("listeOrdi", computerList);
				request.setAttribute("listeOrdiPaginer", computerListPaginer);
			}

			request.setAttribute("addSuccess", request.getParameter("addSuccess"));
			request.setAttribute("editSuccess", request.getParameter("editSuccess"));

			request.setAttribute("search", search);
			request.setAttribute("orderBy", orderBy);
			request.setAttribute("pageIterator", pageIterator);
			request.setAttribute("step", step);
			request.setAttribute("lastPageIndex", lastPageIndex);
			request.setAttribute("columnName", columnName);

			this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

		} catch (DAOConfigurationException e) {
			Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// -- Section Suppression d'ordinateurs --

		String listDeletions = request.getParameter("selection");

		List<String> deleteSelectionArray = Arrays.asList(listDeletions.split("\\s,\\s"));

		for (String s : deleteSelectionArray) {
			try {
				computerServ.deleteComputer(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				Loggers.afficherMessageError("NumberFormatException in Dashboard Servlet" + e.getMessage());
			} catch (DAOConfigurationException e) {
				Loggers.afficherMessageError("DAOConfigurationException in Dashboard Servlet" + e.getMessage());
			}
		}

		doGet(request, response);
	}

}