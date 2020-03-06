package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computer_database.dao.CompanyDAO;
import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.model.Paginer;
import fr.excilys.computer_database.services.CompanyService;
import fr.excilys.computer_database.services.ComputerService;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerDAO computerDao = ComputerDAO.getInstance();
	CompanyDAO companyDao = CompanyDAO.getInstance();

	ComputerService computerServ = ComputerService.getInstance(computerDao);
	CompanyService companyServ = CompanyService.getInstance(companyDao);

	private int pageIterator = 1;
	private int step = 10;
	private String search = null;
	private int lastPageIndex = 1;
	private String orderBy = null;
	private String columnName = null;

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
					this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
				}
			}

			if (request.getParameter("step") != null) {
				try {
					step = Integer.parseInt(request.getParameter("step"));
				} catch (NumberFormatException e) {
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

		} catch (DAOConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

}