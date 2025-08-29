package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.been.StockBeen;
import model.been.StockViewBeen;

public class StockDAO {

	private String SQL_SELECT_STOCK_VIEW = """
			SELECT p.name, p.jan, w.name, s.qty
			FROM stocks s
			INNER JOIN products p ON s.product_id = p.id
			INNER JOIN warehouses w ON s.warehouse_id = w.id
			WHERE 1 = 1
			""";
	private String SQL_SELECT_STOCK_TEMPLATE = """
			SELECT product_id, warehouse_id, qty
			FROM stocks
			WHERE 1 = 1
			""";
	
	public Optional<StockBeen> findByProductIdAndWarehouseId(int productId, int warehouseId, Connection con) throws DAOException {
		String sql = SQL_SELECT_STOCK_TEMPLATE + " AND product_id = ? AND warehouse_id = ?;";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, productId);
			ps.setInt(2, warehouseId);
			return execSelectOptional(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("在庫の取得に失敗しました");
		}
	}
	
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

	public List<StockViewBeen> findAllView() {
		String sql = SQL_SELECT_STOCK_VIEW + ";";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {
			return execSelectView(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("在庫の取得に失敗しました");
		}
	}

	public List<StockViewBeen> findViewByWarehouseIdAndName(int warehouseId, String serchWord) {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT_STOCK_VIEW);
		List<Object> words = new ArrayList<>();
		if (warehouseId != 0) {
			sql.append(" AND w.id = ?");
			words.add(warehouseId);
		}

		if (serchWord != null) {
			if (serchWord.length() != 0) {
				sql.append(" AND (p.name LIKE ? OR p.jan = ?)");
				words.add("%" + serchWord + "%");
				words.add(serchWord);
			}
		}
		sql.append(";");

		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());) {
			for (int i = 0; i < words.size(); i++) {
				ps.setObject(i + 1, words.get(i));
			}
			return execSelectView(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(serchWord);
		}
	}
	
	//Insertの場合true updateの場合false
	public boolean InsertOrUpdate(int productId, int warehouseId, int qty, Connection con) throws DAOException {
		String sql = """
				INSERT INTO stocks (product_id, warehouse_id, qty)
				VALUES (?, ?, ?)
				ON CONFLICT (product_id, warehouse_id)
				DO UPDATE SET qty = stocks.qty + ?
				RETURNING (xmax = 0) AS inserted;
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, productId);
			ps.setInt(2, warehouseId);
			ps.setInt(3, qty);
			ps.setInt(4, qty);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					boolean isInserted = rs.getBoolean("inserted");
					return isInserted;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new DAOException("在庫の更新に失敗しました。");	
	}
	
	public int update(int productId, int warehouseId, int qty, Connection con) throws DAOException {
		String sql = """
				UPDATE stocks 
				SET qty = stocks.qty + ?
				WHERE product_id = ? AND warehouse_id = ?;
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, qty);
			ps.setInt(2, productId);
			ps.setInt(3, warehouseId);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("在庫の更新に失敗しました");
		}
	}

	private List<StockViewBeen> execSelectView(PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.executeQuery();) {
			List<StockViewBeen> list = new ArrayList<>();
			while (rs.next()) {
				StockViewBeen newStockView = new StockViewBeen(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4));
				list.add(newStockView);
			}
			return list;
		}
	}
	
	private Optional<StockBeen> execSelectOptional(PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.executeQuery()){
			if(rs.next()) {
				return Optional.of(new StockBeen(rs.getInt("product_id"), (rs.getInt("warehouse_id")), (rs.getInt("qty"))));	
			} else {
				return Optional.empty();
			}
		}
	}

}
