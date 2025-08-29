package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.been.OrderBeen;
import model.dao.AuditLogDAO;
import model.dao.DAOException;
import model.dao.ProductsDAO;
import model.dao.PurchaseDAO;
import model.dao.PurchaseLinesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.dao.WarehouseDAO;
import model.exception.NonValueException;
import model.exception.NotFoundException;
import model.exception.UnderLotQtyException;
import model.service.PurchaseService;

/**
 * Servlet implementation class PurchaseOrdersServlet
 */
@WebServlet({ "/purchase-orders/new", "/purchase-orders/create"})
public class PurchaseOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		if(session.getAttribute("completed") != null) {
			if(Boolean.FALSE.equals(session.getAttribute("completed"))) {
				request.setAttribute("crtSupplierId", session.getAttribute("crtSupplierId"));
				request.setAttribute("orders", session.getAttribute("orders"));
				request.setAttribute("message", session.getAttribute("message"));
			} else {
				request.setAttribute("message", session.getAttribute("message"));
			}
			session.removeAttribute("crtSupplierId");
			session.removeAttribute("orders");
			session.removeAttribute("completed");
			session.removeAttribute("message");
		}
		try {
			request.setAttribute("suppliers", service.findAllSuppliers());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/purchases/purchase_orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<OrderBeen> orderList = new ArrayList<>();
		session.setAttribute("completed", false);
		int supplierId = Integer.parseInt(request.getParameter("supplierId"));
		session.setAttribute("crtSupplierId", supplierId);
		try {
			for(int i = 1; i <= 10; i++) {
				String jan = request.getParameter("jan" + i); 
				String qty = request.getParameter("qty" + i);
				
				if((jan != null && jan.length() != 0) && (qty != null && qty.length() != 0)) {
					orderList.add(new OrderBeen(i, jan, Integer.parseInt(qty)));
				} else if((jan != null && jan.length() != 0) || (qty != null && qty.length() != 0)) {
					throw new NonValueException(i + "のJANまたは数量が入力されていません");
				} else {
					orderList.add(null);
				}
			}
			session.setAttribute("orders", orderList);
			service.parchaseOrdersAdd(supplierId, orderList);
			session.setAttribute("message", "発注完了");
			session.setAttribute("completed", true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		} catch(NotFoundException e){
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		} catch(DAOException e){
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		} catch (NonValueException e) {
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		} catch (UnderLotQtyException e) {
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		}
		response.sendRedirect("/inventory-management/purchase-orders/new");
		
	}

}
