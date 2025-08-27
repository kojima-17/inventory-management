package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.been.StockViewBeen;

public class StockDAO {
	
	private String SQL_SELECT_STOCK_VIEW = """
			SELECT p.name, p.jan, w.name, s.qty
			FROM stocks s
			INNER JOIN products p ON s.product_id = p.id
			INNER JOIN warehouses w ON s.warehouse_id = w.id
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
	
	public List<StockViewBeen> findAllView(){
		String sql = SQL_SELECT_STOCK_VIEW + ";";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			return execSelectView(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("在庫の取得に失敗しました");
		}
	}
	
	private List<StockViewBeen> execSelectView(PreparedStatement ps) throws SQLException {
		try(ResultSet rs = ps.executeQuery();){
			List<StockViewBeen> list = new ArrayList<>();
			while(rs.next()) {
				StockViewBeen newStockView = new StockViewBeen(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				list.add(newStockView);
			}
			return list;
		}
	}

}
