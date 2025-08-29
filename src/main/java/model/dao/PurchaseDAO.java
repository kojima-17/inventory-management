package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseDAO {
	public int quickInsert(int supplierId, Connection con) throws DAOException {
		String sql = """
				INSERT INTO purchases (supplier_id, received_at, status)
				VALUES (?, now(), 'RECEIVED');
				""";
		try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setInt(1, supplierId);
			if(ps.executeUpdate() != 1) {
				throw new DAOException("入荷ヘッダーの追加に失敗しました");
			} else {
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						throw new DAOException("入荷ヘッダーの追加に失敗しました");
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			new DAOException("入荷ヘッダーの追加に失敗しました");
		}
		return 0;
	}
	
	public int insert(int supplierId, Connection con) throws DAOException {
		String sql = """
				INSERT INTO purchases (supplier_id, status)
				VALUES (?,'ORDERED');
				""";
		try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setInt(1, supplierId);
			if(ps.executeUpdate() != 1) {
				throw new DAOException("入荷ヘッダーの追加に失敗しました");
			} else {
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						throw new DAOException("入荷ヘッダーの追加に失敗しました");
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			new DAOException("入荷ヘッダーの追加に失敗しました");
		}
		return 0;
	}
}
