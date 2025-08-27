package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.been.StockMovementViewBeen;
import model.dao.DAOException;
import model.dao.StockMovementDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;
import model.service.StockMovementService;

/**
 * Servlet implementation class StockMovementServlet
 */
@WebServlet("/stock-movements")
public class StockMovementServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockMovementService service = new StockMovementService(new StockMovementDAO(), new WarehouseDAO());

		try {
			request.setAttribute("warehouses", service.findAllWarehouse());
			request.setAttribute("stockMovements", service.findAllStockMoveView());
		} catch (DAOException e) {
			request.setAttribute("message", e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/stock_movement/stock_movement.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
		String type = request.getParameter("type");
		String strStartDateTime =  request.getParameter("startDateTime");
		String strEndDateTime =  request.getParameter("endDateTime");
		String serchWord = request.getParameter("serchWord");
		StockMovementService service = new StockMovementService(new StockMovementDAO(), new WarehouseDAO());
		try {
			request.setAttribute("warehouses", service.findAllWarehouse());
			List<StockMovementViewBeen> stockMovements = service.serchStockMovement(warehouseId, type, strStartDateTime, strEndDateTime, serchWord);
			request.setAttribute("stockMovements", stockMovements);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.setAttribute("message", "不適切な値です");
		} catch (NotFoundException e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		} catch (DAOException e) { 
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		}
		
		request.setAttribute("crtWarehouseId", warehouseId);
		request.setAttribute("crtType", type);
		if(strStartDateTime != null && strStartDateTime.length() != 0) {
			request.setAttribute("crtStartDateTime", LocalDateTime.parse(strStartDateTime));
		}
		if(strEndDateTime != null && strEndDateTime.length() != 0) {
			request.setAttribute("crtEndDateTime", LocalDateTime.parse(strEndDateTime));
		}
		request.setAttribute("crtSerchWord", serchWord);
		
		request.getRequestDispatcher("/WEB-INF/stock_movement/stock_movement.jsp").forward(request, response);
	}
}
