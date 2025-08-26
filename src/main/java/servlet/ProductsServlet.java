package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.been.ProductBeen;
import model.dao.AuditLogDAO;
import model.dao.ProductsDAO;
import model.exception.NotFoundException;
import model.exception.UniqueKeyException;
import model.service.ProductsService;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet({ "/products", "/products/*" })
public class ProductsServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		HttpSession session = request.getSession();
		System.out.println(pathInfo);
		ProductsService service = new ProductsService(new AuditLogDAO(), new ProductsDAO());
		if (pathInfo == null) {
			request.setAttribute("products", service.findAll());
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			request.getRequestDispatcher("/WEB-INF/products/products_master.jsp").forward(request, response);
			return;
		} else if (pathInfo.equals("/new")) {
			request.getRequestDispatcher("/WEB-INF/products/products_input.jsp").forward(request, response);
			return;
		} else if (pathInfo.matches("/edit.*")) {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			try {
				ProductBeen product = service.findByID(id);
				request.setAttribute("product", product);
			} catch (NotFoundException e) {
				e.printStackTrace();
				response.sendError(404, "対象が見つかりません");
				return;
			}
			request.getRequestDispatcher("/WEB-INF/products/products_update.jsp").forward(request, response);
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
		ProductsService service = new ProductsService(new AuditLogDAO(), new ProductsDAO());
		if (pathInfo.equals("/create")) {
			ProductBeen newProductBeen = new ProductBeen();
			try {
				newProductBeen.setJan((String) request.getParameter("jan"));
				newProductBeen.setName((String) request.getParameter("name"));
				newProductBeen.setStdCost(Double.parseDouble((String) request.getParameter("stdCost")));
				newProductBeen.setStdPrice(Double.parseDouble((String) request.getParameter("stdPrice")));
				newProductBeen.setReorderPoint(Integer.parseInt((String) request.getParameter("orderPoint")));
				newProductBeen.setOrderLot(Integer.parseInt((String) request.getParameter("orderLot")));
				if (service.addProduct(newProductBeen)) {
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
			response.sendRedirect("/inventory-management/products");
			return;
		} else if (pathInfo.equals("/update")) {
			try {
				ProductBeen updateProductBeen = new ProductBeen();
				updateProductBeen.setId(Integer.parseInt((String) request.getParameter("id")));
				updateProductBeen.setJan((String) request.getParameter("jan"));
				updateProductBeen.setName((String) request.getParameter("name"));
				updateProductBeen.setStdCost(Double.parseDouble((String) request.getParameter("stdCost")));
				updateProductBeen.setStdPrice(Double.parseDouble((String) request.getParameter("stdPrice")));
				updateProductBeen.setReorderPoint(Integer.parseInt((String) request.getParameter("orderPoint")));
				updateProductBeen.setOrderLot(Integer.parseInt((String) request.getParameter("orderLot")));
				if (service.update(updateProductBeen)) {
					session.setAttribute("message", "更新完了！");
				} else {
					session.setAttribute("message", "更新失敗！");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				response.sendError(400, "入力値が不正です");
				return;
			}
			response.sendRedirect("/inventory-management/products");
			return;
		} else if (pathInfo.equals("/toggle")) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				service.toggle(id);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				response.sendError(400, "入力値が不正です");
				return;
			}
			response.sendRedirect("/inventory-management/products");
			return;
		}
	}
}
