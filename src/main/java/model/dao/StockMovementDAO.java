package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.been.StockMovementViewBeen;

public class StockMovementDAO {
	
	private final String SQL_SELECT_VIEW_TEMPLATE= """
			SELECT p.name, p.jan, w.name, sm.type, sm.qty, sm.moved_at
			FROM stock_movements sm
			INNER JOIN products p ON sm.product_id = p.id
			INNER JOIN warehouses w ON sm.warehouse_id = w.id
			WHERE 1 = 1
			""";
	
	public List<StockMovementViewBeen> findTopTenViewData(){
		String sql = SQL_SELECT_VIEW_TEMPLATE + "ORDER BY sm.moved_at DESC LIMIT 10;";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();){
			List<StockMovementViewBeen> list = new ArrayList<>();
			while(rs.next()) {
				String productName = rs.getString(1);
				String jan = rs.getString(2);
				String warehouseName = rs.getString(3);
				String type = rs.getString(4);
				int qty = rs.getInt(5);
				LocalDateTime movedAt = rs.getObject(6, LocalDateTime.class);
				list.add(new StockMovementViewBeen(productName, jan, warehouseName, type, qty, movedAt));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
