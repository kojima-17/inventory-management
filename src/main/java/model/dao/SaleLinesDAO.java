package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleLinesDAO {
	public int quickInsert(int saleId, int productId, int qty, double price, Connection con) throws DAOException {
		String sql = """
				INSERT INTO sale_lines (sale_id, product_id, qty, unit_price)
				VALUES (?, ?, ?, ?);
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, saleId);
			ps.setInt(2, productId);
			ps.setInt(3, qty);
			ps.setDouble(4, price);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("販売明細の追加に失敗しました");
		}
	}
}
