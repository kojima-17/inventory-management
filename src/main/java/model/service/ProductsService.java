package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import model.been.AuditLogBeen;
import model.been.ProductBeen;
import model.dao.AuditLogDAO;
import model.dao.InventoryManagementConnction;
import model.dao.ProductsDAO;
import model.exception.NotFoundException;
import model.exception.UniqueKeyException;

public class ProductsService {
	private AuditLogDAO logDAO;
	private ProductsDAO productsDAO;

	public ProductsService(AuditLogDAO logDAO, ProductsDAO productsDAO) {
		this.logDAO = logDAO;
		this.productsDAO = productsDAO;
	}

	public List<ProductBeen> findAll() {
		return productsDAO.findAll();
	}
	
	public ProductBeen findByID(int id) throws NotFoundException {
	    Optional<ProductBeen> optProduct = productsDAO.findById(id);
	    if(optProduct.isEmpty()) {
	    	throw new NotFoundException("商品が見つかりません");
	    } else {
	    	return optProduct.get();
	    }
	}

	public boolean addProduct(ProductBeen product) throws UniqueKeyException {
		try (Connection con = new InventoryManagementConnction().getConnection()) {
			try {
				con.setAutoCommit(false);
				int id = productsDAO.insert(product, con);
				AuditLogBeen log = new AuditLogBeen("管理者", "PRODUCT_CREATE", "products", id);
				logDAO.logging(log, con);
				con.commit();
				return true;
			}catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				if("23505".equals(e.getSQLState())) {
					throw new UniqueKeyException("JANの一意制約違反");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(ProductBeen product){
		try(Connection con = new InventoryManagementConnction().getConnection()){
			try {
				con.setAutoCommit(false);
				if(productsDAO.update(product, con) == 1) {
					int id = product.getId();
					AuditLogBeen log = new AuditLogBeen("管理者", "PRODUCT_UPDATE", "products", id);
					logDAO.logging(log, con);
					con.commit();
					return true;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean toggle(int id) {
		try(Connection con = new InventoryManagementConnction().getConnection()){
			try {
				con.setAutoCommit(false);
				if(productsDAO.toggle(id, con) == 1) {
					AuditLogBeen log = new AuditLogBeen("管理者", "PRODUCT_TOGGLE", "products", id);
					logDAO.logging(log, con);
					con.commit();
					return true;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
