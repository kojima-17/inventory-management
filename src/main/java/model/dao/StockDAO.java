package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.been.StockViewBeen;

public class StockDAO {
	
	private String SQL_SELECT_STOCK_VIEW = """
			SELECT p.name, p.jan, w.name, s.qty,
			FROM stock s
			INNER JOIN products p ON sm.product_id = p.id
			INNER JOIN warehouses w ON sm.warehouse_id = w.id
			WHERE 1 = 1
			""" ;

	public int getRows() {
		String sql = "SELECT COUNT(*) FROM stocks WHERE qty > 0;";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getAllStockCount() {
		String sql = "SELECT SUM(qty) FROM stocks";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<StockViewBeen>

}
