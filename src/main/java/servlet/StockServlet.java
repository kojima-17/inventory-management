package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.dao.DAOException;
import model.dao.StockDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;
import model.service.StockService;

/**
 * Servlet implementation class StockServlet
 */
@WebServlet("/stock")
public class StockServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StockService service = new StockService(new StockDAO(), new WarehouseDAO());

		try {
			request.setAttribute("warehouses", service.findAllWarehouse());
			request.setAttribute("stocks", service.findAllStockView());
		} catch (DAOException e) {
			request.setAttribute("message", e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/stock/stock_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
		String serchWord = request.getParameter("serchWord");
		StockService service = new StockService(new StockDAO(), new WarehouseDAO());
		try {
			request.setAttribute("warehouses", service.findAllWarehouse());
			request.setAttribute("stocks", service.findBySerchword(warehouseId, serchWord));
		} catch (NotFoundException e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.setAttribute("stocks", null);
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		}
		request.setAttribute("crtWarehouseId", warehouseId);
		request.setAttribute("crtSerchWord", serchWord);
		request.getRequestDispatcher("/WEB-INF/stock/stock_list.jsp").forward(request, response);
	}

}
