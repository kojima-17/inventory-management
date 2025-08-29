package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.been.PurchaseLineBeen;

public class PurchaseLinesDAO {
	public int quickInsert(int purchaseId, int productId, int qty, Connection con) throws DAOException {
		String sql = """
				INSERT INTO purchase_lines (purchase_id, product_id, ordered_qty, received_qty)
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
	
	public int insert(int purchaseId, int productId, int qty, Connection con) throws DAOException {
		String sql = """
				INSERT INTO purchase_lines (purchase_id, product_id, ordered_qty)
				VALUES 
				""";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, purchaseId);
			ps.setInt(2, productId);
			ps.setInt(3, qty);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("入荷明細の追加に失敗しました");
		}
	}
	
	
	public int insertMultipleLines(List<PurchaseLineBeen> purchaseLines, Connection con) {
		StringBuilder sql = new StringBuilder();
		sql.append("""
				INSERT INTO purchase_lines (purchase_id, product_id, ordered_qty)
				VALUES 
				""");
		for(int i = 0; i < purchaseLines.size(); i++) {
			sql.append("(?, ?, ?)");
			if(i < purchaseLines.size() - 1) {
				sql.append(", ");
			} else {
				sql.append(";");
			}
		}
		try(PreparedStatement ps = con.prepareStatement(sql.toString())){
			for(int i = 0; i < purchaseLines.size(); i++) {
				ps.setInt(i * 3 + 1, purchaseLines.get(i).getPurchaseId());
				ps.setInt(i * 3 + 2, purchaseLines.get(i).getProductId());
				ps.setInt(i * 3 + 3, purchaseLines.get(i).getOrderedQty());
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注明細の追加に失敗しました");
		}
	}
	
	public int update(PurchaseLineBeen purchaseLine, Connection con) {
		String sql = """
				UPDATE purchase_lines SET received = ?
				WHERE id = ?;
				""";
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, purchaseLine.getReceivedQty());
			ps.setInt(2, purchaseLine.getId());
			return ps.executeUpdate();
		}  catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注明細の更新に失敗しました");
		} 
	}
	
	public List<PurchaseLineBeen> findByPurchaseId(int purchaseId){
		String sql = """
				SELECT id, product_id, ordered_qty, received_qty
				FROM purchase_lines
				WHERE purchase_id = ? AND ordered_qty = received_qty;
				""";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, purchaseId);
			try(ResultSet rs = ps.executeQuery()){
				List<PurchaseLineBeen> list = new ArrayList<PurchaseLineBeen>();
				while(rs.next()) {
					list.add(new PurchaseLineBeen(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("発注明細の取得に失敗しました");
		}
	}
}
	
