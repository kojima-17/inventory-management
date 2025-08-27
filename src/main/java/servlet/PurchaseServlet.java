package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.dao.DAOException;
import model.dao.ProductsDAO;
import model.dao.PurchaseDAO;
import model.dao.PurchaseLinesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.dao.WarehouseDAO;
import model.service.PurchaseService;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet({ "/purchases/new", "/purchases/receive" })
public class PurchaseServlet extends HttpServlet {
	private PurchaseService service;

	public void init() throws ServletException {
		service = new PurchaseService(new StockDAO(), new StockMovementDAO(), new SupplierDAO(), new WarehouseDAO(),
				new ProductsDAO(), new PurchaseDAO(), new PurchaseLinesDAO());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		request.setAttribute("suppliers", service.findAllSuppliers());
		request.setAttribute("warehouses", service.findAllWarehouses());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/purchases/purchase.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int supplierId = Integer.parseInt(request.getParameter("supplierId"));
			int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
			String jan = request.getParameter("jan");
			int qty = Integer.parseInt(request.getParameter("qty"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.setAttribute("message", "入力に不備があります");
		}
		
		

	}

}
