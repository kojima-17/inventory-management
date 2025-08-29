package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import model.been.AuditLogBeen;
import model.been.ProductBeen;
import model.been.StockBeen;
import model.been.WarehouseBeen;
import model.dao.AuditLogDAO;
import model.dao.DAOException;
import model.dao.InventoryManagementConnction;
import model.dao.ProductsDAO;
import model.dao.SaleLinesDAO;
import model.dao.SalesDAO;
import model.dao.StockDAO;
import model.dao.StockMovementDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;

public class SalesService {
	private StockDAO stockDAO;
	private StockMovementDAO stockMovementDAO;
	private WarehouseDAO warehouseDAO;
	private ProductsDAO productsDAO;
	private SalesDAO salesDAO;
	private SaleLinesDAO saleLinesDAO;
	private AuditLogDAO auditLogDAO;
	
	public SalesService(StockDAO stockDAO, StockMovementDAO stockMovementDAO, WarehouseDAO warehouseDAO, 
			ProductsDAO productsDAO, SalesDAO salesDAO, SaleLinesDAO saleLinesDAO ,AuditLogDAO auditLogDAO) {
		this.stockDAO = stockDAO;
		this.stockMovementDAO = stockMovementDAO;
		this.warehouseDAO = warehouseDAO;
		this.productsDAO = productsDAO;
		this.salesDAO = salesDAO;
		this.saleLinesDAO = saleLinesDAO;
		this.auditLogDAO = auditLogDAO;
	}
	
	public List<WarehouseBeen> findAllWarehouses() throws DAOException {
		return warehouseDAO.findAll();
	}
	
	public boolean quickSale(int warehouseId, String jan, int qty, String text) throws NotFoundException, DAOException, IllegalArgumentException{
		if(qty < 0) {
			throw new IllegalArgumentException("数量がマイナスです。");
		}
		if(jan == null || jan.length() == 0) {
			throw new IllegalArgumentException("JANが入力されてません");
		}
		try(Connection con = new InventoryManagementConnction().getConnection();){
			try {
				con.setAutoCommit(false);
				Optional<ProductBeen> optProduct = productsDAO.findByJan(jan, con);
				if(optProduct.isEmpty()) {
					con.rollback();
					throw new NotFoundException("JANが見つかりませんでした");
				} 
				int productId = optProduct.get().getId();
				double productPrice = optProduct.get().getStdPrice();
				Optional<StockBeen> optStock = stockDAO.findByProductIdAndWarehouseId(productId, warehouseId, con);
				if(optStock.isEmpty()) {
					con.rollback();
					throw new NotFoundException("在庫が見つかりませんでした");
				}
				int stockQty = optStock.get().getQty();
				if(stockQty < qty) {
					con.rollback();
					throw new IllegalArgumentException("在庫数量:" + stockQty + "を超えています");
				}
				int saleId = salesDAO.quickInsert(text, con);
				saleLinesDAO.quickInsert(saleId, productId, qty, productPrice, con);
				stockDAO.update(productId, warehouseId, -qty, con);
				stockMovementDAO.insert(productId, warehouseId, -qty, "SALE", "SALE", saleId, con);
				auditLogDAO.logging(new AuditLogBeen("管理者", "SALE", "sales", saleId), con);
				con.commit();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new DAOException(e.getMessage());
			} catch (DAOException e) {
				e.printStackTrace();
				con.rollback();
				throw new DAOException(e.getMessage());
			} finally {
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
}
