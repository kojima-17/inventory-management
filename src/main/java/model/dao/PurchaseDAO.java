package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.been.OrderedPurchaseBeen;
import model.been.PurchaseBeen;

public class PurchaseDAO {
	private final String SQL_SELECT_TEMPLATE = """
			SELECT id, supplier_id, ordered_at, received_at, status
			FROM purchases
			WHERE 1 = 1
			""";
	
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
	
	public List<PurchaseBeen> findByOredered() throws DAOException{
		String sql = SQL_SELECT_TEMPLATE + " AND status = 'ORDERED';";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			return execSelectList(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注の取得に失敗しました");
		}
	}
	
	public List<OrderedPurchaseBeen> findViewByOredered() throws DAOException{
		String sql = """
				SELECT p.id, s.name, p.ordered_at
				FROM purchases p
				INNER JOIN suppliers s ON p.supplier_id = s.id
				WHERE p.status = 'ORDERED';
				""";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
						ResultSet rs = ps.executeQuery()){
			List<OrderedPurchaseBeen> list = new ArrayList<OrderedPurchaseBeen>();
			while(rs.next()) {
				list.add(new OrderedPurchaseBeen(rs.getInt(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime()));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注の取得に失敗しました");
		}
	}
	
	
	private List<PurchaseBeen> execSelectList(PreparedStatement ps) throws SQLException{
		List<PurchaseBeen> list = new ArrayList<>();
		try(ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id");
				int supplierId = rs.getInt("supplier_id");
				LocalDateTime orderedAt = LocalDateTime.parse(rs.getString("ordered_at"));
				LocalDateTime receivedAt = LocalDateTime.parse(rs.getString("received_at"));
				String status = rs.getString("status");
				list.add(new PurchaseBeen(id, supplierId, orderedAt, receivedAt, status)); 
			}
			return list;
		}
	}
}
