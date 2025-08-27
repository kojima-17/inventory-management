package model.service;

import java.util.List;

import model.been.StockViewBeen;
import model.been.WarehouseBeen;
import model.dao.DAOException;
import model.dao.StockDAO;
import model.dao.WarehouseDAO;

public class StockService {
	private StockDAO stockDAO;
	private WarehouseDAO warehouseDAO;
	
	public StockService(StockDAO stockDAO, WarehouseDAO warehouseDAO) {
		this.stockDAO = stockDAO;
		this.warehouseDAO = warehouseDAO;
	}
	
	public List<StockViewBeen> findAllStockView() throws DAOException {
		return stockDAO.findAllView();
	}
	
	public List<WarehouseBeen> findAllWarehouse() throws DAOException {
		return warehouseDAO.findAll();
	}
}
