package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.been.AuditLogBeen;
import model.been.OrderBeen;
import model.been.OrderedPurchaseBeen;
import model.been.ProductBeen;
import model.been.PurchaseLineBeen;
import model.been.SupplierBeen;
import model.been.WarehouseBeen;
import model.dao.AuditLogDAO;
import model.dao.DAOException;
import model.dao.InventoryManagementConnction;
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

public class PurchaseService {
	private StockDAO stockDAO;
	private StockMovementDAO stockMovementDAO;
	private SupplierDAO supplierDAO;
	private WarehouseDAO warehouseDAO;
	private ProductsDAO productsDAO;
	private PurchaseDAO purchaseDAO;
	private PurchaseLinesDAO purchaseLinesDAO;
	private AuditLogDAO auditLogDAO;
	
	public PurchaseService(StockDAO stockDAO, StockMovementDAO stockMovementDAO, SupplierDAO supplierDAO
			, WarehouseDAO warehouseDAO, ProductsDAO productsDAO, PurchaseDAO purchaseDAO, PurchaseLinesDAO purchaseLinesDAO, AuditLogDAO auditLogDAO) {
		this.stockDAO = stockDAO;
		this.stockMovementDAO = stockMovementDAO;
		this.supplierDAO = supplierDAO;
		this.warehouseDAO = warehouseDAO;
		this.productsDAO = productsDAO;
		this.purchaseDAO = purchaseDAO;
		this.purchaseLinesDAO = purchaseLinesDAO;
		this.auditLogDAO = auditLogDAO;
	}
	
	public List <SupplierBeen> findAllSuppliers() throws DAOException {
		return supplierDAO.findAll();
	}
	
	public List<WarehouseBeen> findAllWarehouses() throws DAOException {
		return warehouseDAO.findAll();
	}

	public List<OrderedPurchaseBeen> findOrderedPruchases() throws DAOException {
		return purchaseDAO.findViewByOredered();
	}
	
	public List<PurchaseLineBeen> findPurchaseLinesBypurchaseId(int purchaseId) throws DAOException, NotFoundException{
		List<PurchaseLineBeen> list = purchaseLinesDAO.findByPurchaseId(purchaseId);
		if(list.size() == 0) {
			throw new NotFoundException("未検収の商品が見つかりませんでした");
		} else {
			return list;
		}
	}
	
	public int receive(List<PurchaseLineBeen> purchaseLines, int purchaseId) throws DAOException {
		try(Connection con = new InventoryManagementConnction().getConnection()){
			try {
				con.setAutoCommit(false);
			}
		}
	}
	

	public boolean parchaseAdd(int supplierId, int warehouseId, String jan, int qty) throws DAOException, NotFoundException, IllegalArgumentException {
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
				int purchaseId = purchaseDAO.quickInsert(supplierId, con);
				purchaseLinesDAO.quickInsert(purchaseId, productId, qty, con);
				stockDAO.InsertOrUpdate(productId, warehouseId, qty, con);
				stockMovementDAO.insert(productId, warehouseId, qty, "PURCHASE", "PURCHASE", purchaseId, con);
				auditLogDAO.logging(new AuditLogBeen("管理者", "RECEIVE", "purchases", purchaseId), con);
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
	
	public int parchaseOrdersAdd(int supplierId, List<OrderBeen> orderList) throws NonValueException, DAOException, NotFoundException , UnderLotQtyException{
		List<OrderBeen> targetOrderList = new ArrayList<OrderBeen>();
		for(OrderBeen order : orderList) {
			if(order != null) {
				targetOrderList.add(order);
			}
		}
		if(targetOrderList.size() == 0) {
			throw new NonValueException("値が入力されていません");
		}
		try (Connection con = new InventoryManagementConnction().getConnection()){
			try {
				con.setAutoCommit(false);
				for(OrderBeen order : targetOrderList) {
					Optional<ProductBeen> optProduct =  productsDAO.findByJan(order.getJan(), con);
					if(optProduct.isEmpty()) {
						con.rollback();
						throw new NotFoundException(order.getId() + "のJANが見つかりません");
					}
					ProductBeen product = optProduct.get();
					order.setProduct(product);
					if(order.getQty() < product.getOrderLot() || order.getQty() % product.getOrderLot() != 0) {
						con.rollback();
						throw new UnderLotQtyException(order.getId(), product.getOrderLot());
					}
				}
				int purchaseId = purchaseDAO.insert(supplierId, con);
				List<PurchaseLineBeen> purchaseLineList = new ArrayList<>();
				for(OrderBeen order : targetOrderList) {
					purchaseLineList.add(new PurchaseLineBeen(purchaseId, order.getProduct().getId(), order.getQty()));
				}
				int rows = purchaseLinesDAO.insertMultipleLines(purchaseLineList, con);
				if(rows != targetOrderList.size()) {
					con.rollback();
					throw new DAOException("発注テーブルの詳細の追加に失敗しました");
				}
				auditLogDAO.logging(new AuditLogBeen("管理者", "ORDERD", "purchases", purchaseId), con);
				con.commit();
				return rows;
			} catch (DAOException e) {
				e.printStackTrace();
				con.rollback();
				throw e;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new DAOException("発注処理に失敗しました");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注処理に失敗しました");
		}
		
		
	}
}
