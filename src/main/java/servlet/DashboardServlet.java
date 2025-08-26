package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.been.StockMovementViewBeen;
import model.dao.ProductsDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.service.DashboardService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home/*")
public class DashboardServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DashboardService service = new DashboardService(new ProductsDAO(), new SupplierDAO(), new StockDAO(), new StockMovementDAO());
		request.getSession();
		Map<String, Integer> productCount = service.getProductsCount();
		int productRows = productCount.get("rows");
		int productAllCount = productCount.get("continuedRows");
		int supplierCount = service.getSupplierCount();
		int stockCount = service.getStockCount();
		int stockAllCount = service.getStockAllCount();
		List<StockMovementViewBeen> stockMovements = service.getTopTenMovement();
		request.setAttribute("productRows", productRows);
		request.setAttribute("productAllCount", productAllCount);
		request.setAttribute("supplierCount", supplierCount);
		request.setAttribute("stockCount", stockCount);
		request.setAttribute("stockAllCount", stockAllCount);
		request.setAttribute("stockMovements", stockMovements);
		request.getRequestDispatcher("/WEB-INF/home/dashboard.jsp").forward(request, response);
	}


}
