package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import model.been.ProductBeen;
import model.been.SupplierBeen;
import model.been.WarehouseBeen;
import model.dao.DAOException;
import model.dao.InventoryManagementConnction;
import model.dao.ProductsDAO;
import model.dao.PurchaseDAO;
import model.dao.PurchaseLinesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.SupplierDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;

public class PurchaseService {
	private StockDAO stockDAO;
	private StockMovementDAO stockMovementDAO;
	private SupplierDAO supplierDAO;
	private WarehouseDAO warehouseDAO;
	private ProductsDAO productsDAO;
	private PurchaseDAO purchaseDAO;
	private PurchaseLinesDAO purchaseLinesDAO;
	
	public PurchaseService(StockDAO stockDAO, StockMovementDAO stockMovementDAO, SupplierDAO supplierDAO
			, WarehouseDAO warehouseDAO, ProductsDAO productsDAO, PurchaseDAO purchaseDAO, PurchaseLinesDAO purchaseLinesDAO) {
		this.stockDAO = stockDAO;
		this.stockMovementDAO = stockMovementDAO;
		this.supplierDAO = supplierDAO;
		this.warehouseDAO = warehouseDAO;
		this.productsDAO = productsDAO;
		this.purchaseDAO = purchaseDAO;
		this.purchaseLinesDAO = purchaseLinesDAO;
	}
	
	public List <SupplierBeen> findAllSuppliers() throws DAOException {
		return supplierDAO.findAll();
	}
	
	public List<WarehouseBeen> findAllWarehouses() throws DAOException {
		return warehouseDAO.findAll();
	}
	
	public parchaseAdd(int supplierId, int warehouseId, String jan, int qty) throws DAOException, NotFoundException, IllegalArgumentException {
		if(jan == null || jan.length() == 0) {
			throw new IllegalArgumentException();
		}
		try(Connection con = new InventoryManagementConnction().getConnection();){
			try {
				con.setAutoCommit(false);
				Optional<ProductBeen> optProduct = productsDAO.findByJan(jan, con);
				if(optProduct.isEmpty()) {
					throw new NotFoundException("JANが見つかりませんでした");
					con.rollback();
				} 
				int productId = optProduct.get().getId();
				int purchaseId = purchaseDAO.quickInsert(supplierId, con);
				purchaseLinesDAO.quickInsert(purchaseId, productId, qty, con);
				
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			} catch (DAOException e) {
				e.printStackTrace();
				throw new DAOException(e.getMessage());
				con.rollback();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
