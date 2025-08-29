package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.been.PurchaseLineBeen;
import model.dao.AuditLogDAO;
import model.dao.DAOException;
import model.dao.ProductsDAO;
import model.dao.PurchaseDAO;
import model.dao.PurchaseLinesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;
import model.service.PurchaseService;

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
		HttpSession session = request.getSession();
		if(request.getParameter("id") == null) {
			request.setAttribute("purchases", service.findOrderedPruchases());
			request.getRequestDispatcher("/WEB-INF/purchases/ordered_list.jsp").forward(request, response);
		} else {
			int purchaseId =Integer.parseInt(request.getParameter("id"));
			try {
				session.setAttribute("purchaseLines", service.findPurchaseLinesBypurchaseId(purchaseId));
				request.setAttribute("warehouses", service.findAllWarehouses());
				request.setAttribute("purchaseId", purchaseId);
				request.getRequestDispatcher("/WEB-INF/purchases/purchase_receive.jsp").forward(request, response);
			} catch (DAOException | NotFoundException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/purchases/ordered_list.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("/inventory-management/purchase-orders/receive");
			return;
		} else {
			try {
				List<PurchaseLineBeen> lines = (List<PurchaseLineBeen>) session.getAttribute("purchaseLines");
				for(PurchaseLineBeen line : lines) {
					int receiveQty =  Integer.parseInt(request.getParameter(line.getId() + ""));
					if(receiveQty > line.getNotReceivedQty()) {
						throw new IllegalArgumentException("受入数量が発注残数量を超えています");
					}
					line.setReceivedQty(line.getReceivedQty() + receiveQty);
				}
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				request.setAttribute("message", e.getMessage());
			}
			
		}
	}

}
