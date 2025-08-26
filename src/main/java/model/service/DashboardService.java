package model.service;

import java.util.List;
import java.util.Map;

import model.been.StockMovementViewBeen;
import model.dao.ProductsDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;

public class DashboardService {
	private ProductsDAO productsDAO;
	private SupplierDAO supplierDAO;
	private StockDAO stockDAO;
	private StockMovementDAO stockMovementDAO;
	
	public DashboardService(ProductsDAO productDAO, SupplierDAO supplierDAO, StockDAO stockDAO, StockMovementDAO stockMovementDAO) {
		this.productsDAO = productDAO;
		this.supplierDAO = supplierDAO;
		this.stockDAO = stockDAO;
		this.stockMovementDAO = stockMovementDAO;
	}
	
	public Map<String, Integer> getProductsCount(){
		return productsDAO.getProductsCount();
	}
	
	public int getSupplierCount() {
		return supplierDAO.getRows();
	}
	
	public int getStockCount() {
		return stockDAO.getRows();
	}
	
	public int getStockAllCount() {
		return stockDAO.getAllStockCount();
	}
	
	public List<StockMovementViewBeen> getTopTenMovement() {
		return stockMovementDAO.findTopTenViewData();
	}
}
