package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.dao.AuditLogDAO;
import model.dao.ProductsDAO;
import model.dao.PurchaseDAO;
import model.dao.PurchaseLinesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.dao.WarehouseDAO;
import model.service.PurchaseService;

import java.io.IOException;

/**
 * Servlet implementation class PurchaseReceiveServlet
 */
@WebServlet({"/purchase-orders/receive", "/purchase-orders/receive?id=?"})
public class PurchaseReceiveServlet extends HttpServlet {
    
	private PurchaseService service;

	public void init() throws ServletException {
		service = new PurchaseService(new StockDAO(), new StockMovementDAO(), new SupplierDAO(), new WarehouseDAO(),
				new ProductsDAO(), new PurchaseDAO(), new PurchaseLinesDAO(), new AuditLogDAO());
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			service.findAllWarehouses();
			service.
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
