package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseLinesDAO {
	public int quickInsert(int purchaseId, int productId, int qty, Connection con) throws DAOException {
		String sql = """
				INSERT INTO purchases (purchases_id, products_id, orderd_qty, received_qty)
				VALUES (?, ?, ?, ?);
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, purchaseId);
			ps.setInt(2, productId);
			ps.setInt(3, qty);
			ps.setInt(4, qty);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("入荷明細の追加に失敗しました");
		}
	}
}
	
