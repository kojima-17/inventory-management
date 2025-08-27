package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.been.StockMovementViewBeen;

public class StockMovementDAO {

	private final String SQL_SELECT_VIEW_TEMPLATE = """
			SELECT p.name, p.jan, w.name, sm.type, sm.qty, sm.moved_at
			FROM stock_movements sm
			INNER JOIN products p ON sm.product_id = p.id
			INNER JOIN warehouses w ON sm.warehouse_id = w.id
			WHERE 1 = 1
			""";

	public List<StockMovementViewBeen> findTopTenViewData() {
		String sql = SQL_SELECT_VIEW_TEMPLATE + "ORDER BY sm.moved_at DESC LIMIT 10;";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {
			return execSelectView(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<StockMovementViewBeen> findAllViewData() throws DAOException{
		String sql = SQL_SELECT_VIEW_TEMPLATE + "ORDER BY sm.moved_at;";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {
			return execSelectView(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("入出庫履歴の取得に失敗しました");
		}
	}
	
	public List<StockMovementViewBeen> serchStockMovementView(int warehouseId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String serchWord){
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT_VIEW_TEMPLATE);
		List<Object> words = new ArrayList<>();
		if(warehouseId != 0) {
			sql.append(" AND w.id = ?");
			words.add(warehouseId);
		}
		
		if(type != null) {
			if(type.length() != 0) {
				sql.append(" AND sm.type = ?");
				words.add(type);
			}
		}
		
		if(startDateTime != null) {
			sql.append(" AND sm.moved_at >= ?");
			words.add(startDateTime);
		}
		
		if(endDateTime != null) {
			sql.append(" AND sm.moved_at <= ?");
			words.add(endDateTime);
		}
		
		
		if(serchWord != null) {
			if(serchWord.length() != 0) {
				sql.append(" AND (p.name LIKE ? OR p.jan = ?)");
				words.add("%" + serchWord + "%");
				words.add(serchWord);
			}
		}
		sql.append(";");
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());){
			for (int i = 0; i < words.size(); i++) {
			    Object param = words.get(i);
			    if (param instanceof LocalDateTime ldt) {
			        ps.setTimestamp(i + 1, Timestamp.valueOf(ldt));
			    } else {
			        ps.setObject(i + 1, param);
			    }
			}
			return execSelectView(ps);
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DAOException("入出庫履歴の取得に失敗しました");
		}
	}
	
	private List<StockMovementViewBeen> execSelectView(PreparedStatement ps) throws SQLException{
		List<StockMovementViewBeen> list = new ArrayList<>();
		try(ResultSet rs = ps.executeQuery();){
			while (rs.next()) {
				String productName = rs.getString(1);
				String jan = rs.getString(2);
				String warehouseName = rs.getString(3);
				String type = rs.getString(4);
				int qty = rs.getInt(5);
				LocalDateTime movedAt = rs.getObject(6, LocalDateTime.class);
				list.add(new StockMovementViewBeen(productName, jan, warehouseName, type, qty, movedAt));
			}
			return list;
		}
	}
}
