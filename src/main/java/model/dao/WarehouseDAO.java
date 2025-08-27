package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.been.WarehouseBeen;

public class WarehouseDAO {
	String SQL_SELECT_TEMPLATE = """
			SELECT id, name, location
			FROM warehouses
			WHERE 1=1;
			""";
	
	public List<WarehouseBeen> findAll(){
		String sql = SQL_SELECT_TEMPLATE + ";";
		try(Connection con = new InventoryManagementConnction().getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			return execSelect(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("倉庫の取得に失敗しました");
		}
		
		
	}
	
	private List<WarehouseBeen> execSelect(PreparedStatement ps) throws SQLException {
		try(ResultSet rs = ps.executeQuery();){
			List<WarehouseBeen> list = new ArrayList<>();
			while(rs.next()) {
				WarehouseBeen newWareHouse = new WarehouseBeen(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(newWareHouse);
			}
			return list;
		}
	}
}
