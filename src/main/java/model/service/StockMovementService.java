package model.service;

import java.time.LocalDateTime;
import java.util.List;

import model.been.StockMovementViewBeen;
import model.been.WarehouseBeen;
import model.dao.DAOException;
import model.dao.StockMovementDAO;
import model.dao.WarehouseDAO;
import model.exception.NotFoundException;

public class StockMovementService {

	private StockMovementDAO stockMovementDAO;
	private WarehouseDAO warehouseDAO;
	public StockMovementService(StockMovementDAO stockMovementDAO,  WarehouseDAO warehouseDAO) {
		this.stockMovementDAO = stockMovementDAO;
		this.warehouseDAO = warehouseDAO;
	}
	
	public List<StockMovementViewBeen> findAllStockMoveView() throws DAOException {
		return stockMovementDAO.findAllViewData();
	}
	
	public List<WarehouseBeen> findAllWarehouse() throws DAOException {
		return warehouseDAO.findAll();
	}
	
	public List<StockMovementViewBeen> serchStockMovement(int warehouseId, 
			String type, String strStartDateTime, String strEndDateTime, String serchWord) throws DAOException, IllegalArgumentException, NotFoundException{
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		if(strStartDateTime != null && strStartDateTime.length() != 0) {
			startDateTime = LocalDateTime.parse(strStartDateTime);
		}
		if(strEndDateTime != null && strEndDateTime.length() != 0) {
			endDateTime = LocalDateTime.parse(strEndDateTime);
		}
		var list = stockMovementDAO.serchStockMovementView(warehouseId, type, startDateTime, endDateTime, serchWord);
		if(list.size() == 0) {
			throw new NotFoundException("条件に当てはまる入出庫履歴はありません");
		} else {
			return list;
		}
				
	}
	
}
