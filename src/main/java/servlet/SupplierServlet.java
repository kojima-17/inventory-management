package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.been.SupplierBeen;
import model.dao.AuditLogDAO;
import model.dao.SupplierDAO;
import model.exception.NotFoundException;
import model.exception.UniqueKeyException;
import model.service.SupplierService;

/**
 * Servlet implementation class SupplierServlet
 */
@WebServlet({ "/supplier", "/supplier/*" })
public class SupplierServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		HttpSession session = request.getSession();
		System.out.println(pathInfo);
		SupplierService service = new SupplierService(new AuditLogDAO(), new SupplierDAO());
		if (pathInfo == null) {
			request.setAttribute("suppliers", service.findAll());
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			request.getRequestDispatcher("/WEB-INF/supplier/supplier_master.jsp").forward(request, response);
			return;
		} else if (pathInfo.equals("/new")) {
			request.getRequestDispatcher("/WEB-INF/supplier/supplier_input.jsp").forward(request, response);
			return;
		} else if (pathInfo.matches("/edit.*")) {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			try {
				SupplierBeen supplier = service.findByID(id);
				request.setAttribute("supplier", supplier);
			} catch (NotFoundException e) {
				e.printStackTrace();
				response.sendError(404, "対象が見つかりません");
				return;
			}
			request.getRequestDispatcher("/WEB-INF/supplier/supplier_update.jsp").forward(request, response);
			return;
		} else {
			response.sendRedirect("/inventory-management/home");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		HttpSession session = request.getSession();
		SupplierService service = new SupplierService(new AuditLogDAO(), new SupplierDAO());
		if (pathInfo.equals("/create")) {
			SupplierBeen newSupplierBeen = new SupplierBeen();
			try {
				newSupplierBeen.setName((String) request.getParameter("name"));
				newSupplierBeen.setLeadTimeDays(Integer.parseInt((String) request.getParameter("leadTimeDays")));
				newSupplierBeen.setPhone((String) request.getParameter("phone"));
				newSupplierBeen.setEmail((String) request.getParameter("email"));
				if (service.addSupplier(newSupplierBeen)) {
					session.setAttribute("message", "登録完了");
				} else {
					session.setAttribute("message", "登録失敗！");
				}
			} catch (UniqueKeyException e) {
				e.printStackTrace();
				response.sendError(409, "同一JANの商品が既に存在します");
				return;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				response.sendError(400, "入力値が不正です");
				return;
			}
			response.sendRedirect("/inventory-management/supplier");
			return;
		} else if (pathInfo.equals("/update")) {
			try {
				SupplierBeen updateSupplierBeen = new SupplierBeen();
				updateSupplierBeen.setId(Integer.parseInt((String) request.getParameter("id")));
				updateSupplierBeen.setName((String) request.getParameter("name"));
				updateSupplierBeen.setLeadTimeDays(Integer.parseInt((String) request.getParameter("leadTimeDays")));
				updateSupplierBeen.setPhone((String) request.getParameter("phone"));
				updateSupplierBeen.setEmail((String) request.getParameter("email"));
				if (service.update(updateSupplierBeen)) {
					session.setAttribute("message", "更新完了！");
				} else {
					session.setAttribute("message", "更新失敗！");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				response.sendError(400, "入力値が不正です");
				return;
			}
			response.sendRedirect("/inventory-management/supplier");
			return;
		} else {
			response.sendRedirect("/inventory-management/home");
			return;
		}
	}
}
