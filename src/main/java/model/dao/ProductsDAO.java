package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.been.ProductBeen;

public class ProductsDAO {
	private final String SQL_SELECT_TEMPLATE = """
			SELECT id, jan, name, std_cost, std_price, std_price, reorder_point, order_lot, discontinued
			FROM products
			WHERE 1 = 1
			""";

	public List<ProductBeen> findAll() {
		String sql = SQL_SELECT_TEMPLATE + ";";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			return execSelectAndCreateList(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Optional<ProductBeen> findById(int id) {
		String sql = SQL_SELECT_TEMPLATE + "AND id = ?;";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			return execSelectAndCreateOptional(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Map<String, Integer> getProductsCount() {
		String sql = """
				SELECT
				COUNT(*) AS rows
				,SUM(CASE WHEN discontinued = false THEN 1 ELSE 0 END) AS continued_rows
				FROM products;
				""";
		try (Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			rs.next();
			var result = new HashMap<String, Integer>();
			result.put("rows", rs.getInt("rows"));
			result.put("continuedRows", rs.getInt("continued_rows"));
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insert(ProductBeen product, Connection con) throws SQLException {
		String sql = """
				INSERT INTO
				products (jan, name, std_cost, std_price, reorder_point, order_lot)
				VALUES (?, ?, ?, ?, ?, ?)
				""";
		try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, product.getJan());
			ps.setString(2, product.getName());
			ps.setDouble(3, product.getStdCost());
			ps.setDouble(4, product.getStdPrice());
			ps.setInt(5, product.getReorderPoint());
			ps.setInt(6, product.getOrderLot());
			int rows = ps.executeUpdate();
			if (rows == 1) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					rs.next();
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public int update(ProductBeen product, Connection con) throws SQLException {
		String sql = """
				UPDATE products SET name = ?, std_cost = ?
						, std_price = ?, reorder_point = ?, order_lot = ? , updated_at = now()
				WHERE id = ?;
				""";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, product.getName());
			ps.setDouble(2, product.getStdCost());
			ps.setDouble(3, product.getStdPrice());
			ps.setInt(4, product.getReorderPoint());
			ps.setInt(5, product.getOrderLot());
			ps.setInt(6, product.getId());
			return ps.executeUpdate();
		}
	}

	public int toggle(int id, Connection con) {
		String sql = """
				UPDATE products SET discontinued = CASE
				WHEN discontinued = false THEN true
				ELSE false END, updated_at = now()
				WHERE id = ?
				""";
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Optional<ProductBeen> findByJan(String jan, Connection con) throws DAOException {
		String sql = SQL_SELECT_TEMPLATE + " AND jan = ?;";
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, jan);
			return execSelectAndCreateOptional(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("値の取得に失敗しました");
		}
	}

	private List<ProductBeen> execSelectAndCreateList(PreparedStatement ps) throws SQLException {
		List<ProductBeen> list = new ArrayList<ProductBeen>();
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String jan = rs.getString("jan");
				String name = rs.getString("name");
				double stdCost = rs.getDouble("std_cost");
				double stdPrice = rs.getDouble("std_price");
				int reorderPoint = rs.getInt("reorder_point");
				int orderLot = rs.getInt("order_lot");
				boolean discontinued = rs.getBoolean("discontinued");
				list.add(new ProductBeen(id, jan, name, stdCost, stdPrice, reorderPoint, orderLot, discontinued));
			}
			return list;
		}
	}

	private Optional<ProductBeen> execSelectAndCreateOptional(PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				int id = rs.getInt("id");
				String jan = rs.getString("jan");
				String name = rs.getString("name");
				double stdCost = rs.getDouble("std_cost");
				double stdPrice = rs.getDouble("std_price");
				int reorderPoint = rs.getInt("reorder_point");
				int orderLot = rs.getInt("order_lot");
				boolean discontinued = rs.getBoolean("discontinued");
				return Optional
						.of(new ProductBeen(id, jan, name, stdCost, stdPrice, reorderPoint, orderLot, discontinued));
			}
		}
		return Optional.empty();
	}
}
