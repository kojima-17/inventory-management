package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import model.been.AuditLogBeen;
import model.been.SupplierBeen;
import model.dao.AuditLogDAO;
import model.dao.InventoryManagementConnction;
import model.dao.SupplierDAO;
import model.exception.NotFoundException;

public class SupplierService {
	private AuditLogDAO logDAO;
	private SupplierDAO supplierDAO;

	public SupplierService(AuditLogDAO logDAO, SupplierDAO supplierDAO) {
		this.logDAO = logDAO;
		this.supplierDAO = supplierDAO;
	}

	public List<SupplierBeen> findAll() {
		return supplierDAO.findAll();
	}
	
	public SupplierBeen findByID(int id) throws NotFoundException {
	    Optional<SupplierBeen> optSupplier = supplierDAO.findById(id);
	    if(optSupplier.isEmpty()) {
	    	throw new NotFoundException("商品が見つかりません");
	    } else {
	    	return optSupplier.get();
	    }
	}

	public boolean addSupplier(SupplierBeen supplier) {
		try (Connection con = new InventoryManagementConnction().getConnection()) {
			try {
				con.setAutoCommit(false);
				int id = supplierDAO.insert(supplier, con);
				AuditLogBeen log = new AuditLogBeen("管理者", "SUPPLIER_CREATE", "suppliers", id);
				logDAO.logging(log, con);
				con.commit();
				return true;
			}catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(SupplierBeen supplier){
		try(Connection con = new InventoryManagementConnction().getConnection()){
			try {
				con.setAutoCommit(false);
				if(supplierDAO.update(supplier, con) == 1) {
					int id = supplier.getId();
					AuditLogBeen log = new AuditLogBeen("管理者", "PRODUCT_UPDATE", "supplier", id);
					logDAO.logging(log, con);
					con.commit();
					return true;
				}else {
					con.rollback();
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
