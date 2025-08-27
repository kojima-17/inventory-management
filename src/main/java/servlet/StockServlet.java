package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.DAOException;
import model.dao.StockDAO;
import model.dao.WarehouseDAO;
import model.service.StockService;

/**
 * Servlet implementation class StockServlet
 */
@WebServlet("/stock")
public class StockServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockService service = new StockService(new StockDAO(), new WarehouseDAO());
		HttpSession session = request.getSession();
		try {
			request.setAttribute("stocks", service.findAllStockView());
			request.setAttribute("warehouses", service.findAllWarehouse());
		} catch(DAOException e) {
			request.setAttribute("message", e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/stock/stock_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String warehouseId = request.getParameter("warenhouseId");
		String serchWord = request.getParameter("serchWord");
		
		
	}

}
