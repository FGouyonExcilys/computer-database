package fr.excilys.computer_database.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computer_database.dao.ComputerDAO;
import fr.excilys.computer_database.dao.DAO;
import fr.excilys.computer_database.exceptions.DAOConfigurationException;
import fr.excilys.computer_database.model.Computer;
import fr.excilys.computer_database.services.ComputerService;


@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int pageIterator = 1;
    private int step = 10;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        ComputerDAO computerDao;
        
        ArrayList<Computer> computerListPaginerDefault = null;
		try {
			computerDao = ComputerDAO.getInstance(DAO.getInstance());
			
			ArrayList<Computer> computerList = ComputerService.getInstance(computerDao).getComputerList();
			
			if (request.getParameter("pageIterator") != null) {
				String s = request.getParameter("pageIterator");
				pageIterator = Integer.parseInt(s);
			}
			
			if (request.getParameter("step") != null) {
				String s = request.getParameter("step");
				step = Integer.parseInt(s);
			}
			
			computerListPaginerDefault = ComputerService.getInstance(computerDao).getComputerListPaginer((pageIterator-1)*step,step);
			
			int lastPageIndex = (int) Math.ceil((double)computerList.size()/step);
			
			request.setAttribute("pageIterator", pageIterator);
			request.setAttribute("step", step);
			request.setAttribute("lastPageIndex", lastPageIndex);
			request.setAttribute("listeOrdi", computerList);
			request.setAttribute("listeOrdiPaginer", computerListPaginerDefault);
			
		} catch (DAOConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	doGet(request, response);
    }

}