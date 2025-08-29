package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.AuditLogDAO;
import model.dao.DAOException;
import model.dao.ProductsDAO;
import model.dao.SaleLinesDAO;
import model.dao.SalesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;
import model.service.SalesService;

/**
 * Servlet implementation class SalesServlet
 */
@WebServlet("/sales/new")
public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SalesService service;

	public void init() throws ServletException {
		service = new SalesService(new StockDAO(), new StockMovementDAO(), new WarehouseDAO(),
				new ProductsDAO(), new SalesDAO(), new SaleLinesDAO(), new AuditLogDAO());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("completed") != null) {
			if(Boolean.FALSE.equals(session.getAttribute("completed"))) {
				request.setAttribute("crtWarehouseId", session.getAttribute("crtWarehouseId"));
				request.setAttribute("crtJan", session.getAttribute("crtJan"));
				request.setAttribute("crtQty", session.getAttribute("crtQty"));
				request.setAttribute("crtText", session.getAttribute("crtText"));
				request.setAttribute("message", session.getAttribute("message"));
			} else {
				request.setAttribute("message", session.getAttribute("message"));
			}
			session.removeAttribute("crtWarehouseId");
			session.removeAttribute("crtJan");
			session.removeAttribute("crtQty");
			session.removeAttribute("crtText");
			session.removeAttribute("completed");
			session.removeAttribute("message");
		}
		
		try {
			request.setAttribute("warehouses", service.findAllWarehouses());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/sales/sales_quick.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("completed", false);
		try {
			int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
			String jan = request.getParameter("jan");
			int qty = Integer.parseInt(request.getParameter("qty"));
			String text = request.getParameter("text");
			session.setAttribute("crtWarehouseId", warehouseId);
			session.setAttribute("crtJan", jan);
			session.setAttribute("crtQty", qty);
			session.setAttribute("crtText", text);
			service.quickSale(warehouseId, jan, qty, text);
			session.setAttribute("completed", true);
			session.setAttribute("message", "販売完了");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.setAttribute("message", "入力に不備があります");
		} catch (DAOException e) {
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		} catch (NotFoundException e) {
			e.printStackTrace();
			session.setAttribute("message", e.getMessage());
		}
		response.sendRedirect("/inventory-management/sales/new");
	}

}
